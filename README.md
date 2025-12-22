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

---

## 🏗️ Kiến Trúc

### Backend Architecture (Feature-based)

## Kiến Trúc Dự Án             # Chia theo tính năng
│   ├── auth/               # Authentication & User Management
│   ├── product/            # Product Management
│   ├── cart/               # Shopping Cart
│   ├── order/              # Order Processing
│   └── size/               # Size Management
├── infrastructure/         # Cấu hình & Infrastructure
│   ├── config/            # Security, CORS, Web Config
│   ├── filter/            # JWT Filter
│   ├── interceptor/       # Request/Response Interceptors
│   └── aop/               # Aspect-Oriented Programming
├── model/                  # Domain Models (10 Entities)
├── service/                # Shared Services
└── utils/                  # Utilities
```

### Frontend Architecture (Component-based)

```
frontend/
├── views/                  # Page Components (7 pages)
├── components/             # Reusable Components (7 components)
├── composables/            # Composition API Logic
│   ├── management/         # Admin-specific composables
│   └── *.js               # Feature composables
├── service/                # API Service Layer
├── stores/                 # Pinia State Management
├── router/                 # Vue Router Configuration
└── utils/                  # Utilities & Helpers
```

---

## Cài Đặt

- **Java 21** or higher
- **Node.js 18+** and **npm/pnpm**
- **MySQL 8.0+**
- **Maven 3.8+**

### 1️⃣ Clone Repository

```bash
git clone https://github.com/yourusername/java6-assignment.git
cd java6-assignment
```

### 2️⃣ Database Setup

```bash
# Tạo database
mysql -u root -p
CREATE DATABASE assignent_nitimo_ecommerce;
exit;

# Import schema (nếu có)
mysql -u root -p assignent_nitimo_ecommerce < database/schema.sql
```

### 3️⃣ Backend Setup

```bash
cd backend

# Cấu hình database trong application.properties
# src/main/resources/application.properties
# Sửa username/password theo MySQL của bạn

# Build và chạy
mvn clean install
mvn spring-boot:run

# Hoặc
./mvnw spring-boot:run
```

Backend sẽ chạy tại: `http://localhost:8080`

### 4️⃣ Frontend Setup

```bash
cd frontend

# Install dependencies
npm install
# hoặc
pnpm install

# Run development server
npm run dev
# hoặc
pnpm dev
```

Frontend sẽ chạy tại: `http://localhost:5173`

---

##rmaid
sequenceDiagram
    participant User
    participant Frontend
    participant Backend
    participant Database

    User->>Frontend: Login (username, password)
    Frontend->>Backend: POST /api/auth/login
    Backend->>Database: Verify credentials
    Database-->>Backend: User data
    Backend-->>Frontend: JWT Token (HttpOnly Cookie)
    Frontend->>Frontend: Store role in sessionStorage
    User->>Frontend: Access protected route
    Frontend->>Backend: Request with Cookie
    Backend->>Backend: Validate JWT
    Backend-->>Frontend: Protected resource
```

---

## 📡 API Documentation

## API Endpointsescription | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/login` | Đăng nhập | ❌ |
| POST | `/api/auth/logup` | Đăng ký | ❌ |
| GET | `/api/auth/check-auth-status` | Kiểm tra trạng thái auth | ✅ |
| GET | `/api/auth/get_my_profile` | Lấy thông tin profile | ✅ |
| PUT | `/api/auth/update-profile` | Cập nhật profile | ✅ |
| POST | `/api/auth/change-password` | Đổi mật khẩu | ✅ |

### Products

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/product/all?page=0&size=10` | Danh sách sản phẩm | ❌ |
| GET | `/api/product/{code}` | Chi tiết sản phẩm | ❌ |
| GET | `/api/category` | Danh sách danh mục | ❌ |
| POST | `/api/admin/product` | Tạo sản phẩm | 👤 Admin |
| PUT | `/api/admin/product/{code}` | Cập nhật sản phẩm | 👤 Admin |
| DELETE | `/api/admin/product/{code}` | Xóa sản phẩm | 👤 Admin |

### Cart

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/cart-item/slide-bar` | Lấy giỏ hàng | ✅ |
| POST | `/api/cart-item/add` | Thêm vào giỏ | ✅ |
| PUT | `/api/cart-item/update` | Cập nhật số lượng | ✅ |
| DELETE | `/api/cart-item/remove` | Xóa item | ✅ |
| DELETE | `/api/cart-item/clean` | Xóa tất cả | ✅ |
| GET | `/api/cart-item/is-empty` | Kiểm tra giỏ rỗng | ✅ |

### Orders

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/order/create-order` | Tạo đơn hàng | ✅ |
| GET | `/api/order/fetch-order-history` | Lịch sử đơn hàng | ✅ |
| GET | `/api/order-detail/{orderCode}` | Chi tiết đơn hàng | ✅ |
| PUT | `/api/admin/order/{orderCode}/status` | Cập nhật trạng thái | 👤 Admin |

---

## 🗄️ Database Schema

##
-- Users (Người dùng)
users (username PK, password, email, fullName, phone, address, role, status)

-- Products (Sản phẩm)
products (productCode PK, name, description, price, discount, categoryCode FK)
product_variants (id PK, productCode FK, color, sizeCode FK, stock)
product_images (id PK, productCode FK, imageUrl, isPrimary)
categories (categoryCode PK, categoryName)
sizes (sizeCode PK, sizeName)

-- Shopping (Giỏ hàng & Đơn hàng)
carts (id PK, username FK)
cart_items (id PK, cartId FK, variantId FK, quantity)
orders (orderCode PK, username FK, orderDate, status, totalAmount, shippingAddress)
order_details (id PK, orderCode FK, productCode FK, variantId FK, quantity, price)
```

### Relationships

- User `1-N` Cart, Order
- Product `N-1` Category
- Product `1-N` ProductVariant, ProductImage
- Order `1-N` OrderDetail
- ProductVariant `N-1` Size

---

## 🎨 Screenshots

##lication.properties**
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3307/assignent_nitimo_ecommerce
spring.datasource.username=root
spring.datasource.password=your_password

# JPA
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

# File Upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=100MB
```

### Frontend Configuration

**vite.config.js**
```javascript
export default {
  server: {
    port: 5173,
    proxy: {
      '/api': 'http://localhost:8080'
    }
  }
}
```

---

## 🔒 Security Features

- ✅ **JWT Authentication** với HttpOnly Cookies
- ✅ **BCrypt Password Hashing**
- ✅ **Role-Based Access Control** (RBAC)
- ✅ **CORS Configuration** cho cross-origin requests
- ✅ **CSRF Protection** (disabled cho REST API)
- ✅ **SQL Injection Prevention** (JPA PreparedStatements)
- ✅ **XSS Protection** (HttpOnly cookies)
- ✅ **Route Guards** (Frontend protection)

---

## 🚀 Performance Optimizations

- ⚡ **Named Entity Graph** - Giải quyết N+1 query problem
- ⚡ **Lazy Loading** - Relationships được load on-demand
- ⚡ **Pagination** - Giảm tải dữ liệu (10 items/page)
- ⚡ **Debounce** - Optimize search input
- ⚡ **Singleton State** - Shared cart state across components
- ⚡ **Vite HMR** - Fast refresh trong development

---

## Security Implementation

- JWT Authentication với HttpOnly Cookies
- BCrypt password hashing
- Role-based access control (RBAC)
- CORS configuration
- CSRF protection (disabled cho REST API)
- SQL injection prevention (JPA PreparedStatements)
- XSS protection
- Frontend route guards

## Performance Optimizations

- Named Entity Graph để giải quyết N+1 query problem
- Lazy loading cho relationships
- Pagination (10 items per page)
- Debounce cho search input
- Singleton state pattern cho cart
- Vite HMR trong development

## Design Patterns & Best Practices

**Backend**
- Repository Pattern
- DTO Pattern
- Service Layer architecture
- Feature-based package structure
- Jakarta Bean Validation
- Centralized exception handling

**Frontend**
- Composition API (Vue 3)
- Singleton pattern cho shared state
- Composable functions
- Route guards
- API service layer

##mmit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

---

## 📄 License

Dự án này được phân phối dưới giấy phép MIT. Xem file `LICENSE` để biết thêm chi tiết.

---

## 👨‍💻 Author

**Poly Student**
- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- Vue.js Community
- Bootstrap Team
- FPT Polytechnic - Java 6 Course

---

<div align="center">

### ⭐ Nếu dự án hữu ích, hãy cho một ngôi sao nhé! ⭐

**Made with ❤️ by Poly Student**

</div>
## Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/NewFeature`)
3. Commit your changes (`git commit -m 'Add NewFeature'`)
4. Push to the branch (`git push origin feature/NewFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Contact

- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

## Acknowledgments

Dự án được phát triển như một phần của môn học Java 6 tại FPT Polytechnic.
