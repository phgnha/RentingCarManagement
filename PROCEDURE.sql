-- Active: 1731858376857@@127.0.0.1@3306@quanlychothuexe

DELIMITER //


-- 1. Thủ tục tạo hợp đồng thuê xe mới
CREATE PROCEDURE CreateRentalContract(
    IN p_staffID INT,
    IN p_customerID INT,
    IN p_carID INT,
    IN p_beginDate DATE,
    IN p_endDate DATE,
    IN p_rent BIGINT
)
BEGIN
    DECLARE v_contractID INT;
    DECLARE v_carStatus VARCHAR(20);
    
    -- Kiểm tra trạng thái xe
    SELECT status INTO v_carStatus FROM car WHERE id = p_carID;
    
    IF v_carStatus != 'Sẵn sàng' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Xe không khả dụng cho thuê';
    ELSE
        START TRANSACTION;
        -- Tạo hợp đồng mới
        INSERT INTO contract (staffID, customerID, beginDate, endDate, totalCost, status)
        VALUES (p_staffID, p_customerID, p_beginDate, p_endDate, p_rent, 'Đang thực hiện');
        
        SET v_contractID = LAST_INSERT_ID();
        
        -- Tạo chi tiết hợp đồng
        INSERT INTO contractDetail (contractID, carID, deliveryDate, returnDate, rent)
        VALUES (v_contractID, p_carID, p_beginDate, p_endDate, p_rent);
        
        -- Cập nhật trạng thái xe
        UPDATE car SET status = 'Đang cho thuê' WHERE id = p_carID;
        
        COMMIT;
    END IF;
END //


-- 2. Thủ tục ghi nhận trả xe và vi phạm
CREATE PROCEDURE ReturnCarWithViolation(
    IN p_contractID INT,
    IN p_actualReturnDate DATE,
    IN p_violationDesc VARCHAR(200),
    IN p_penaltyAmount BIGINT
)
BEGIN
    DECLARE v_carID INT;
    DECLARE v_customerID INT;
    DECLARE v_staffID INT;
    DECLARE v_invoiceID INT;
    
    START TRANSACTION;
    
    -- Lấy thông tin cần thiết
    SELECT carID, c.customerID, c.staffID 
    INTO v_carID, v_customerID, v_staffID
    FROM contractDetail cd
    JOIN contract c ON cd.contractID = c.id
    WHERE c.id = p_contractID;
    
    -- Tạo hóa đơn phạt
    INSERT INTO invoice (contractID, totalCost, releaseDate, status, type)
    VALUES (p_contractID, p_penaltyAmount, CURRENT_DATE, 'Chờ thanh toán', 'Phí phạt');
    
    SET v_invoiceID = LAST_INSERT_ID();
    
    -- Ghi nhận vi phạm
    INSERT INTO breachOfContract (
        contractID, invoiceID, staffID, customerID, 
        amount, violationDate, description
    )
    VALUES (
        p_contractID, v_invoiceID, v_staffID, v_customerID,
        p_penaltyAmount, p_actualReturnDate, p_violationDesc
    );
    
    -- Cập nhật trạng thái xe và hợp đồng
    UPDATE car SET status = 'Sẵn sàng' WHERE id = v_carID;
    UPDATE contract SET status = 'Vi phạm' WHERE id = p_contractID;
    
    COMMIT;
END //


-- 3. Thủ tục tính lương nhân viên
CREATE PROCEDURE CalculateStaffSalary(
    IN p_staffID INT,
    IN p_month INT,
    IN p_year INT
)
BEGIN
    DECLARE v_basicSalary INT;
    DECLARE v_bonus INT;
    DECLARE v_allowance INT;
    DECLARE v_totalAmount INT;
    
    -- Tính bonus dựa trên số hợp đồng hoàn thành
    SELECT COUNT(*) * 200000 INTO v_bonus
    FROM contract 
    WHERE staffID = p_staffID 
    AND MONTH(endDate) = p_month 
    AND YEAR(endDate) = p_year
    AND status = 'Hoàn thành';
    
    -- Lấy lương cơ bản theo vị trí
    SELECT 
        CASE 
            WHEN position = 'Quản lý' THEN 15000000
            ELSE 10000000
        END,
        CASE 
            WHEN position = 'Quản lý' THEN 1000000
            ELSE 500000
        END
    INTO v_basicSalary, v_allowance
    FROM staff 
    WHERE id = p_staffID;
    
    SET v_totalAmount = v_basicSalary + v_bonus + v_allowance;
    
    -- Lưu vào lịch sử lương
    INSERT INTO salaryHistory (
        staffID, payDay, basicSalary, 
        bonus, allowance, amount
    )
    VALUES (
        p_staffID, LAST_DAY(CONCAT(p_year,'-',p_month,'-01')), 
        v_basicSalary, v_bonus, v_allowance, v_totalAmount
    );
END //


-- 4. Thủ tục kiểm tra xe cần bảo trì
CREATE PROCEDURE CheckMaintenanceNeeded()
BEGIN
    SELECT 
        c.id,
        c.licensePlateNumber,
        c.brand,
        c.model,
        c.distanceTravel,
        MAX(cm.maintenanceDate) as LastMaintenance,
        DATEDIFF(CURRENT_DATE, MAX(cm.maintenanceDate)) as DaysSinceLastMaintenance
    FROM car c
    LEFT JOIN carMaintenance cm ON c.id = cm.carID
    GROUP BY c.id
    HAVING 
        DaysSinceLastMaintenance >= 180 -- 6 tháng
        OR c.distanceTravel >= 50000 -- 50,000 km
        OR LastMaintenance IS NULL;
END //


-- 5. Thủ tục cập nhật trạng thái xe
CREATE PROCEDURE UpdateCarStatus(
    IN p_carID INT,
    IN p_newStatus VARCHAR(20),
    IN p_maintenanceDesc VARCHAR(200),
    IN p_maintenanceExpense BIGINT,
    IN p_staffInChargeID INT
)
BEGIN
    START TRANSACTION;
    
    -- Cập nhật trạng thái xe
    UPDATE car 
    SET status = p_newStatus
    WHERE id = p_carID;
    
    -- Nếu là bảo trì, thêm bản ghi bảo trì
    IF p_newStatus = 'Đang bảo trì' THEN
        INSERT INTO carMaintenance (
            carID, maintenanceDate, expense, 
            description, staffInChargeID
        )
        VALUES (
            p_carID, CURRENT_DATE, p_maintenanceExpense,
            p_maintenanceDesc, p_staffInChargeID
        );
    END IF;
    
    COMMIT;
END //


-- 6. Thủ tục tạo hợp đồng rỗng
CREATE PROCEDURE CreateEmptyContract(
    IN p_staffID INT,
    IN p_customerID INT,
    IN p_beginDate DATE,
    IN p_endDate DATE,
    OUT p_contractID INT
)
BEGIN
    START TRANSACTION;
    
    -- Tạo hợp đồng mới với tổng tiền ban đầu = 0
    INSERT INTO contract (staffID, customerID, beginDate, endDate, totalCost, status)
    VALUES (p_staffID, p_customerID, p_beginDate, p_endDate, 0, 'Chờ duyệt');
    
    SET p_contractID = LAST_INSERT_ID();
    
    COMMIT;
END //


-- 7. Thủ tục thêm xe vào hợp đồng (cập nhật)
CREATE PROCEDURE AddCarToContract(
    IN p_contractID INT,
    IN p_carID INT,
    IN p_deliveryDate DATE,    -- Thêm ngày bắt đầu thuê xe
    IN p_returnDate DATE,      -- Thêm ngày trả xe
    IN p_rent BIGINT
)
BEGIN
    DECLARE v_carStatus VARCHAR(20);
    DECLARE v_contractStatus VARCHAR(20);
    DECLARE v_contractBeginDate DATE;
    DECLARE v_contractEndDate DATE;
    
    -- Kiểm tra trạng thái xe và hợp đồng
    SELECT c.status, con.beginDate, con.endDate, con.status
    INTO v_carStatus, v_contractBeginDate, v_contractEndDate, v_contractStatus
    FROM car c, contract con 
    WHERE c.id = p_carID AND con.id = p_contractID;
    
    -- Kiểm tra các điều kiện
    IF v_carStatus NOT IN ('Sẵn sàng', 'Đang cho thuê') THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Xe không khả dụng cho thuê';
    ELSEIF v_contractStatus != 'Chờ duyệt' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Hợp đồng không ở trạng thái chờ duyệt';
    ELSEIF p_deliveryDate < v_contractBeginDate OR p_returnDate > v_contractEndDate THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Thời gian thuê xe nằm ngoài thời hạn hợp đồng';
    ELSEIF NOT IsCarAvailable(p_carID, p_deliveryDate, p_returnDate) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Xe đã được đặt trong khoảng thời gian này';
    ELSE
        START TRANSACTION;
        
        -- Thêm chi tiết hợp đồng với ngày thuê cụ thể
        INSERT INTO contractDetail (
            contractID, 
            carID, 
            deliveryDate, 
            returnDate, 
            rent
        )
        VALUES (
            p_contractID, 
            p_carID, 
            p_deliveryDate, 
            p_returnDate, 
            p_rent
        );
        
        -- Cập nhật tổng tiền hợp đồng
        UPDATE contract 
        SET totalCost = totalCost + p_rent
        WHERE id = p_contractID;
        
        COMMIT;
    END IF;
END //


-- 8. Thủ tục thêm dịch vụ vào hợp đồng
CREATE PROCEDURE AddServiceToContract(
    IN p_contractID INT,
    IN p_serviceName VARCHAR(20),
    IN p_servicePrice BIGINT
)
BEGIN
    DECLARE v_contractStatus VARCHAR(20);
    DECLARE v_serviceID INT;
    
    -- Kiểm tra trạng thái hợp đồng
    SELECT status INTO v_contractStatus 
    FROM contract 
    WHERE id = p_contractID;
    
    IF v_contractStatus != 'Chờ duyệt' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Hợp đồng không ở trạng thái chờ duyệt';
    ELSE
        START TRANSACTION;
        
        -- Tìm ID dịch vụ tiếp theo cho hợp đồng này
        SELECT COALESCE(MAX(id), 0) + 1 
        INTO v_serviceID
        FROM plugInService 
        WHERE contractID = p_contractID;
        
        -- Thêm dịch vụ
        INSERT INTO plugInService (id, contractID, name, price)
        VALUES (v_serviceID, p_contractID, p_serviceName, p_servicePrice);
        
        -- Cập nhật tổng tiền hợp đồng
        UPDATE contract 
        SET totalCost = totalCost + p_servicePrice
        WHERE id = p_contractID;
        
        COMMIT;
    END IF;
END //


-- 9. Thủ tục hoàn tất hợp đồng
CREATE PROCEDURE FinalizeContract(
    IN p_contractID INT,
    OUT p_invoiceID INT
)
BEGIN
    DECLARE v_totalCost BIGINT;
    DECLARE v_contractStatus VARCHAR(20);
    
    -- Kiểm tra trạng thái hợp đồng
    SELECT status, totalCost INTO v_contractStatus, v_totalCost
    FROM contract 
    WHERE id = p_contractID;
    
    IF v_contractStatus != 'Chờ duyệt' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Hợp đồng không ở trạng thái chờ duyệt';
    ELSE
        START TRANSACTION;
        
        -- Cập nhật trạng thái hợp đồng
        UPDATE contract 
        SET status = 'Đang thực hiện'
        WHERE id = p_contractID;
        
        -- Cập nhật trạng thái các xe
        UPDATE car 
        SET status = 'Đang cho thuê'
        WHERE id IN (
            SELECT carID 
            FROM contractDetail 
            WHERE contractID = p_contractID
        );
        
        -- Tạo hóa đơn
        INSERT INTO invoice (
            contractID, totalCost, releaseDate, 
            status, type
        )
        VALUES (
            p_contractID, v_totalCost, CURRENT_DATE,
            'Chờ thanh toán', 'Phí thuê xe'
        );
        SET p_invoiceID = LAST_INSERT_ID();
        
        COMMIT;
    END IF;
END //


-- 10. Thủ tục thanh toán
CREATE PROCEDURE MakePayment(
    IN p_invoiceID INT,
    IN p_amount BIGINT,
    IN p_method ENUM('Tiền mặt', 'Thẻ tín dụng', 'Chuyển khoản', 'Ví điện tử')
)
BEGIN
    DECLARE v_totalCost BIGINT;
    DECLARE v_paidAmount BIGINT;
    DECLARE v_remainingAmount BIGINT;
    DECLARE v_status ENUM('Đã thanh toán', 'Chờ thanh toán', 'Thanh toán thất bại', 'Hoàn tiền', 'Đã hủy');
    
    -- Kiểm tra tổng chi phí và trạng thái hóa đơn
    SELECT totalCost, status INTO v_totalCost, v_status
    FROM invoice
    WHERE id = p_invoiceID;
    
    -- Tính tổng số tiền đã thanh toán
    SELECT COALESCE(SUM(amount), 0) INTO v_paidAmount
    FROM payment
    WHERE invoiceID = p_invoiceID;
    
    SET v_remainingAmount = v_totalCost - v_paidAmount;
    
    IF v_status != 'Chờ thanh toán' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Hóa đơn không ở trạng thái chờ thanh toán';
    ELSEIF p_amount > v_remainingAmount THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Số tiền thanh toán vượt quá số tiền còn lại';
    ELSE
        START TRANSACTION;
        
        -- Thêm bản ghi thanh toán
        INSERT INTO payment (
            invoiceID, 
            implementationDate, 
            amount, 
            method
        )
        VALUES (
            p_invoiceID, 
            CURRENT_DATE, 
            p_amount, 
            p_method
        );
        
        -- Cập nhật trạng thái hóa đơn nếu đã thanh toán đủ
        IF p_amount = v_remainingAmount THEN
            UPDATE invoice
            SET status = 'Đã thanh toán'
            WHERE id = p_invoiceID;
        END IF;
        
        COMMIT;
    END IF;
END //

DELIMITER ;
