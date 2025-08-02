CREATE TABLE person (
    id VARCHAR(20) PRIMARY KEY,
    lastName VARCHAR(10),
    firstName VARCHAR(10),
    bornDate DATE,
    email VARCHAR(30) UNIQUE,
    address VARCHAR(50)
);


CREATE TABLE phone (
    phoneNumber VARCHAR(12) PRIMARY KEY,
    personID VARCHAR(20),
    FOREIGN KEY (personID) REFERENCES person(id) ON DELETE CASCADE
);
CREATE TABLE driverLicense (
    licenseNumber VARCHAR(20) PRIMARY KEY,
    expireDate DATE
);
CREATE TABLE customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    personID VARCHAR(20),
    licenseNumber VARCHAR(20),
    FOREIGN KEY (personID) REFERENCES person(id) ON DELETE CASCADE,
    FOREIGN KEY (licenseNumber) REFERENCES driverLicense (licenseNumber) ON DELETE SET NULL
);
CREATE TABLE staff (
    id INT PRIMARY KEY AUTO_INCREMENT,
    personID VARCHAR(20),
    hireDate DATE,
    branchID INT,
    position VARCHAR(20),
    FOREIGN KEY (personID) REFERENCES person(id) ON DELETE CASCADE
);
CREATE TABLE branch (
    id INT PRIMARY KEY AUTO_INCREMENT,
    branchName VARCHAR(30),
    managerID INT,
    address VARCHAR(50),
    phoneNumber VARCHAR(12),
    FOREIGN KEY (managerID) REFERENCES staff(id) ON DELETE SET NULL
);
ALTER TABLE staff
ADD FOREIGN KEY (branchID) REFERENCES branch (id);
CREATE TABLE contract (
    id INT PRIMARY KEY AUTO_INCREMENT,
    staffID INT,
    customerID INT,
    beginDate DATE,
    endDate DATE,
    totalCost BIGINT,
    status ENUM('Chờ duyệt', 'Đang thực hiện', 'Hoàn thành', 'Đã hủy', 'Vi phạm'),
    FOREIGN KEY (staffID) REFERENCES staff(id),
    FOREIGN KEY (customerID) REFERENCES customer(id) 
);
CREATE TABLE car (
    id INT PRIMARY KEY AUTO_INCREMENT,
    licensePlateNumber VARCHAR(20),
    model VARCHAR(20),
    manufactureYear YEAR,
    category ENUM(
        'Xe 4 chỗ',
        'Xe 7 chỗ',
        'Xe 16 chỗ',
        'Xe 29 chỗ',
        'Xe bán tải',
        'Xe sang',
        'Xe thể thao',
        'Xe du lịch'
    ) NOT NULL,
    brand VARCHAR(20),
    color ENUM(
        'Đỏ',
        'Xanh',
        'Đen',
        'Trắng',
        'Bạc',
        'Xám',
        'Nâu',
        'Vàng cát',
        'Xanh đen',
        'Trắng ngọc trai'
    ) NOT NULL,
    gearboxType ENUM(
        'Số sàn',
        'Số tự động',
        'Số hỗn hợp'
    ) NOT NULL,
    distanceTravel INT,
    status ENUM(
        'Sẵn sàng',
        'Đang cho thuê',
        'Đang bảo trì',
        'Không khả dụng'
    ),
    branchID INT,
    FOREIGN KEY (branchID) REFERENCES branch(id) ON UPDATE CASCADE
);

CREATE TABLE contractDetail (
    contractID INT,
    carID INT,
    deliveryDate DATE,
    returnDate DATE,
    rent BIGINT,
    PRIMARY KEY (contractID, carID),
    FOREIGN KEY (contractID) REFERENCES contract(id) ON DELETE CASCADE,
    FOREIGN KEY (carID) REFERENCES car (id) 
);
CREATE TABLE carMaintenance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    carID INT,
    maintenanceDate DATE,
    expense BIGINT,
    description VARCHAR(200),
    staffInChargeID INT,
    FOREIGN KEY (carID) REFERENCES car (id),
    FOREIGN KEY (staffInChargeID) REFERENCES staff (id)
);
CREATE TABLE plugInService (
    id INT,
    contractID INT,
    name VARCHAR(20),
    price INT,
    PRIMARY KEY (id, contractID),
    FOREIGN KEY (contractID) REFERENCES contract(id) ON DELETE CASCADE
);
CREATE TABLE invoice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    contractID INT,
    totalCost BIGINT,
    releaseDate DATE,
    status ENUM(
        'Đã thanh toán',
        'Chờ thanh toán',
        'Thanh toán thất bại',
        'Hoàn tiền',
        'Đã hủy'
    ),
    type ENUM(
        'Phí thuê xe',
        'Tiền cọc',
        'Phí trễ hạn',
        'Phí dịch vụ',
        'Phí bảo hiểm',
        'Phí phạt'
    ),
    FOREIGN KEY (contractID) REFERENCES contract (id)
);
CREATE TABLE payment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    invoiceID INT,
    implementationDate DATE,
    amount BIGINT,
    method ENUM (
        'Tiền mặt',
        'Thẻ tín dụng',
        'Chuyển khoản',
        'Ví điện tử'
    ),
    FOREIGN KEY (invoiceID) REFERENCES invoice(id) ON DELETE CASCADE
);
CREATE TABLE breachOfContract (
    id INT PRIMARY KEY AUTO_INCREMENT,
    contractID INT,
    invoiceID INT,
    staffID INT,
    customerID INT,
    amount BIGINT,
    violationDate DATE,
    description VARCHAR(200),
    FOREIGN KEY (contractID) REFERENCES contract (id) ON DELETE CASCADE,
    FOREIGN KEY (invoiceID) REFERENCES invoice (id) ON DELETE CASCADE,
    FOREIGN KEY (staffID) REFERENCES staff (id) ON DELETE CASCADE,
    FOREIGN KEY (customerID) REFERENCES customer (id) ON DELETE CASCADE
);
CREATE TABLE manager (
    staffID INT,
    managerID INT,
    PRIMARY KEY (staffID, managerID),
    FOREIGN KEY (staffID) REFERENCES staff (id) ON DELETE CASCADE,
    FOREIGN KEY (managerID) REFERENCES staff (id) ON DELETE CASCADE
);
CREATE TABLE salaryHistory (
    id INT PRIMARY KEY AUTO_INCREMENT,
    staffID INT,
    payDay DATE,
    basicSalary INT,
    bonus INT,
    allowance INT,
    amount INT,
    FOREIGN KEY (staffID) REFERENCES staff(id) 
);