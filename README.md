ER diagram:
<img width="887" height="743" alt="image" src="https://github.com/user-attachments/assets/c03844ff-2ca1-4d04-9e74-79dfae12da25" />

Relational Schema(3NF Form):
<img width="945" height="853" alt="image" src="https://github.com/user-attachments/assets/06be1fdd-75ff-470a-809e-d74c2b002605" />


# Renting Car Management

Hệ thống quản lý cho thuê xe ô tô sử dụng Spring Boot, JPA, MySQL.

## Tính năng

- Quản lý chi nhánh, nhân viên, khách hàng, xe, hợp đồng, hóa đơn.
- Tìm kiếm, lọc, và thống kê doanh thu.
- Quản lý trạng thái xe, hợp đồng, hóa đơn.
- Kiểm tra tính hợp lệ của giấy phép lái xe khách hàng.
- Xử lý thanh toán, vi phạm hợp đồng.


## Công nghệ sử dụng

- Java 17
- Spring Boot 3
- Spring Data JPA
- MySQL
- Lombok

## Cài đặt & Chạy dự án

1. Cấu hình database trong `src/main/resources/application.properties`:

    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/rentingcar
    spring.datasource.username=YOUR_USERNAME
    spring.datasource.password=YOUR_PASSWORD
    ```

2. Build và chạy ứng dụng:

    ```sh
    ./mvnw spring-boot:run
    ```

3. API sẽ chạy tại: `http://localhost:8080`

## API chính

- `/api/branches` - Quản lý chi nhánh
- `/api/staff` - Quản lý nhân viên
- `/api/customers` - Quản lý khách hàng
- `/api/cars` - Quản lý xe
- `/api/contracts` - Quản lý hợp đồng
- `/api/contract-details` - Quản lý chi tiết hợp đồng
- `/api/invoices` - Quản lý hóa đơn
- `/api/persons` - Quản lý thông tin cá nhân

