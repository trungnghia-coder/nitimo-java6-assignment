# Nitimo E-Commerce Platform

Full-stack e-commerce application built with Spring Boot and Vue.js

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Vue.js](https://img.shields.io/badge/Vue.js-3.5.24-4FC08D.svg)](https://vuejs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)

## Tổng Quan

Website thương mại điện tử bán quần áo trực tuyến với đầy đủ tính năng quản lý sản phẩm, giỏ hàng, đơn hàng và phân quyền người dùng. Dự án được phát triển như một bài tập lớn môn Java 6 tại FPT Polytechnic.

### Mục Tiêu

- Xây dựng hệ thống e-commerce hoàn chỉnh với Spring Boot REST API
- Triển khai JWT authentication và role-based authorization
- Responsive UI với Vue.js và Bootstrap
- Tối ưu hiệu suất với pagination và lazy loading
- Clean architecture với feature-based structure

## Chức Năng Chính

### User Features

**Authentication**
- Đăng ký/Đăng nhập với JWT token (HttpOnly cookie)
- Quản lý profile cá nhân
- Đổi mật khẩu
- Session persistence

**Shopping**
- Browse danh sách sản phẩm với pagination (10 items/page)
- Filter theo category
- Xem chi tiết sản phẩm với multiple images
- Product variants (màu sắc, kích thước)
- Infinite scroll

**Cart & Checkout**
- Add/remove/update items trong giỏ hàng
- Real-time cart counter
- Cart slide-out panel
- Validation logic trước khi checkout
- Location picker (Tỉnh/Thành - Quận/Huyện - Phường/Xã)
- Order history

### Admin Features

**Product Management**
- CRUD operations cho products
- Quản lý product variants (color, size, stock)
- Multiple image upload
- Set primary image
- Category management

**Order Management**
- View tất cả orders
- Update order status (PENDING → CONFIRMED → SHIPPING → COMPLETED)
- Order details với full information
- Customer order tracking

**User Management**
- Customer management
- User account management
- View user profiles và statistics

## Tech Stack

### Backend

- **Java 21** - Programming language
- **Spring Boot 3.5.7** - Application framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Database ORM
- **MySQL 8.0** - Relational database
- **JWT 0.11.5** - Token-based authentication
- **Maven** - Build tool
- **Lombok** - Reduce boilerplate code

### Frontend

- **Vue.js 3.5.24** - Progressive JavaScript framework
- **Vite 7.2.2** - Build tool
- **Vue Router 4.6.3** - Client-side routing
- **Axios** - HTTP client
- **Bootstrap 5.3.8** - CSS framework
- **Pinia** - State management

## Cấu trúc project

Backend sử dụng feature-based architecture:
```
backend/src/main/java/poly/edu/java6/
├── feature/
│   ├── auth/          # Authentication, user management
│   ├── product/       # Product, category
│   ├── cart/          # Shopping cart
│   └── order/         # Orders
├── infrastructure/    # Security config, filters
├── model/            # JPA entities
└── service/          # Shared services
```

Frontend:
```
frontend/src/
├── views/            # Pages
├── components/       # Reusable components
├── composables/      # Business logic
├── router/           # Routing
└── stores/           # State management
```

## Setup

**Prerequisites:**
- Java 21+
- Node.js 18+
- MySQL 8.0+
- Maven

**1. Database**
```sql
CREATE DATABASE assignent_nitimo_ecommerce;
```

**2. Backend**
```bash
cd backend
# Sửa application.properties với MySQL credentials
mvn spring-boot:run
```
Server chạy ở `http://localhost:8080`

**3. Frontend**
```bash
cd frontend
npm install
npm run dev
```
App chạy ở `http://localhost:5173`

## Authentication

JWT-based authentication với HttpOnly cookies:
1. User login → Backend verify → Generate JWT
2. JWT được lưu trong HttpOnly cookie
3. Frontend gửi request với cookie
4. Backend validate JWT qua filter
5. Authorization dựa trên role (USER/ADMIN)

## API

**Public endpoints:**
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/logup` - Đăng ký
- `GET /api/product/all` - Danh sách sản phẩm (paginated)
- `GET /api/product/{code}` - Chi tiết sản phẩm
- `GET /api/category` - Danh mục

**Authenticated:**
- `GET /api/cart-item/slide-bar` - Lấy giỏ hàng
- `POST /api/cart-item/add` - Thêm vào giỏ
- `POST /api/order/create-order` - Tạo đơn hàng
- `GET /api/order/fetch-order-history` - Lịch sử đơn

**Admin only:**
- `/api/admin/**` - Quản lý sản phẩm, đơn hàng, users

## Database

**Main tables:**
- `users` - User accounts
- `products` - Sản phẩm
- `product_variants` - Variants (color, size)
- `product_images` - Ảnh sản phẩm
- `categories` - Danh mục
- `carts`, `cart_items` - Giỏ hàng
- `orders`, `order_details` - Đơn hàng

Config trong `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3307/assignent_nitimo_ecommerce
spring.jpa.hibernate.ddl-auto=none
```

## Một số implementation notes

**Backend:**
- Feature-based package structure thay vì layered
- JWT filter để validate token từ cookie
- Named Entity Graph để tránh N+1 queries
- DTO pattern để tách request/response khỏi entities
- BCrypt cho password hashing

**Frontend:**
- Composition API (Vue 3)
- Pinia cho state management
- Singleton pattern cho cart state (shared across components)
- Route guards để protect authenticated routes
- Axios interceptor với credentials

## Build

**Backend:**
```bash
mvn clean package
java -jar target/java6-0.0.1-SNAPSHOT.jar
```

**Frontend:**
```bash
npm run build
```

---

Assignment project cho môn Java 6 - FPT Polytechnic
