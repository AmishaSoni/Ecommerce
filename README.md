# 🛒 Ecommerce Application

A modern, scalable e-commerce backend API built with Spring Boot 3.3.5 and PostgreSQL. Manage products, users, shopping carts, and orders with a clean REST API.

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-green?logo=spring-boot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9-darkred?logo=apachemaven)](https://maven.apache.org/)

---

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Database Models](#database-models)
- [Configuration](#configuration)
- [Development](#development)
- [Docker](#docker)
- [Contributing](#contributing)
- [License](#license)

---

## ✨ Features

### User Management
- ✅ User registration and profile management
- ✅ Role-based access control (User/Admin roles)
- ✅ Address management for users
- ✅ User authentication & authorization ready

### Product Management
- ✅ Create, read, update, delete products
- ✅ Product inventory management
- ✅ Product pricing and categorization
- ✅ Search and filter capabilities

### Shopping Cart
- ✅ Add/remove items from cart
- ✅ Update cart item quantities
- ✅ Real-time cart total calculations
- ✅ Cart persistence

### Order Management
- ✅ Place orders from cart items
- ✅ Order status tracking (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- ✅ Order history for users
- ✅ Order item details and tracking

### API Features
- ✅ RESTful API design
- ✅ Request/Response DTOs
- ✅ Proper HTTP status codes
- ✅ Spring Boot Actuator for monitoring

---

## 🛠️ Tech Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 17 | Programming Language |
| **Spring Boot** | 3.3.5 | Framework |
| **Spring Data JPA** | - | ORM & Database Access |
| **PostgreSQL** | 16 | Relational Database |
| **Maven** | 3.9+ | Build Tool |
| **Lombok** | Latest | Reduce Boilerplate Code |
| **Actuator** | - | Monitoring & Health Checks |

---

## 📁 Project Structure

```
ecommerce/
├── src/
│   ├── main/
│   │   └── java/com/app/ecom/
│   │       ├── EcomApplication.java           # Main Application Entry Point
│   │       ├── controller/                    # REST Controllers
│   │       │   ├── UserController.java
│   │       │   ├── ProductController.java
│   │       │   ├── CartController.java
│   │       │   └── OrderController.java
│   │       ├── service/                       # Business Logic
│   │       │   ├── UserService.java
│   │       │   ├── ProductService.java
│   │       │   ├── CartService.java
│   │       │   └── OrderService.java
│   │       ├── repository/                    # Data Access Layer
│   │       │   ├── UserRepository.java
│   │       │   ├── ProductRepository.java
│   │       │   ├── CartItemRepository.java
│   │       │   └── OrderRepository.java
│   │       ├── model/                         # JPA Entities
│   │       │   ├── User.java
│   │       │   ├── Product.java
│   │       │   ├── CartItem.java
│   │       │   ├── Order.java
│   │       │   ├── OrderItem.java
│   │       │   ├── Address.java
│   │       │   ├── UserRole.java
│   │       │   └── OrderStatus.java
│   │       └── dto/                           # Data Transfer Objects
│   │           ├── UserRequest.java
│   │           ├── UserResponse.java
│   │           ├── ProductRequest.java
│   │           ├── ProductResponse.java
│   │           ├── CartItemRequest.java
│   │           ├── OrderResponse.java
│   │           ├── OrderItemDTO.java
│   │           └── AddressDTO.java
│   └── test/
│       └── java/com/app/ecom/
│           └── EcomApplicationTests.java
├── target/                                    # Compiled Output
├── .mvn/                                      # Maven Wrapper
├── pom.xml                                    # Maven Dependencies
├── Dockerfile                                 # Container Configuration
├── docker-compose.yml                         # Docker Orchestration
├── DOCKER_README.md                           # Docker Documentation
└── README.md                                  # This File

```

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17+** ([Download](https://www.oracle.com/java/technologies/downloads/#java17))
- **Maven 3.9+** ([Download](https://maven.apache.org/download.cgi))
- **PostgreSQL 14+** ([Download](https://www.postgresql.org/download/))
- **Git** ([Download](https://git-scm.com/))

**Optional (for Docker):**
- **Docker** ([Download](https://www.docker.com/products/docker-desktop))
- **Docker Compose** (Included with Docker Desktop)

**Verify Installation:**
```bash
java -version
mvn --version
psql --version
```

---

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-org/ecommerce.git
cd ecommerce
```

### 2. Configure Database Connection

#### Option A: Using PostgreSQL Locally

Create a PostgreSQL database:
```sql
CREATE USER ecom_user WITH PASSWORD 'ecom_password';
CREATE DATABASE ecommerce OWNER ecom_user;
```

Create `src/main/resources/application.properties` (if not exists):
```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=ecom_user
spring.datasource.password=ecom_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Actuator Configuration
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=when-authorized
```

#### Option B: Using Docker Compose
See [DOCKER_README.md](./DOCKER_README.md) for Docker setup instructions.

### 3. Install Dependencies
```bash
mvn clean install
```

---

## ▶️ Running the Application

### Using Maven
```bash
# Run the application
mvn spring-boot:run

# Run tests
mvn test

# Build production JAR
mvn clean package
```

### Using Java
```bash
java -jar target/ecom-application-0.0.1-SNAPSHOT.jar
```

### Using Docker
```bash
docker-compose up --build
```

The application will start on **http://localhost:8080**

### Health Check
```bash
curl http://localhost:8080/actuator/health
# Response: {"status":"UP"}
```

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:8080/api
```

### User Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/users` | Get all users |
| `GET` | `/users/{id}` | Get user by ID |
| `POST` | `/users` | Create new user |
| `PUT` | `/users/{id}` | Update user |
| `DELETE` | `/users/{id}` | Delete user |
| `GET` | `/users/{id}/addresses` | Get user addresses |
| `POST` | `/users/{id}/addresses` | Add address to user |

**Create User Request:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "role": "USER"
}
```

---

### Product Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/products` | Get all products |
| `GET` | `/products/{id}` | Get product by ID |
| `POST` | `/products` | Create new product |
| `PUT` | `/products/{id}` | Update product |
| `DELETE` | `/products/{id}` | Delete product |
| `GET` | `/products/search` | Search products |

**Create Product Request:**
```json
{
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "stock": 50,
  "category": "Electronics"
}
```

---

### Cart Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/cart/{userId}` | Get user's cart |
| `POST` | `/cart/{userId}/items` | Add item to cart |
| `PUT` | `/cart/items/{cartItemId}` | Update cart item quantity |
| `DELETE` | `/cart/items/{cartItemId}` | Remove item from cart |
| `DELETE` | `/cart/{userId}/clear` | Clear entire cart |

**Add to Cart Request:**
```json
{
  "productId": 1,
  "quantity": 2
}
```

---

### Order Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/orders` | Get all orders |
| `GET` | `/orders/{id}` | Get order by ID |
| `POST` | `/orders` | Create new order |
| `GET` | `/orders/user/{userId}` | Get user's orders |
| `PUT` | `/orders/{id}/status` | Update order status |

**Create Order Request:**
```json
{
  "userId": 1,
  "cartId": 1,
  "shippingAddress": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "country": "USA"
  }
}
```

**Update Order Status Request:**
```json
{
  "status": "SHIPPED"
}
```

---

## 📊 Database Models

### User Entity
```
User
├── id (Long) - Primary Key
├── name (String) - User's full name
├── email (String) - Email address (Unique)
├── phone (String) - Phone number
├── role (UserRole) - USER or ADMIN
├── addresses (List<Address>) - One-to-Many relationship
├── cartItems (List<CartItem>) - One-to-Many relationship
└── orders (List<Order>) - One-to-Many relationship
```

### Product Entity
```
Product
├── id (Long) - Primary Key
├── name (String) - Product name
├── description (String) - Product description
├── price (BigDecimal) - Product price
├── stock (Integer) - Available quantity
├── category (String) - Product category
├── cartItems (List<CartItem>) - One-to-Many relationship
└── orderItems (List<OrderItem>) - One-to-Many relationship
```

### CartItem Entity
```
CartItem
├── id (Long) - Primary Key
├── user (User) - Many-to-One relationship
├── product (Product) - Many-to-One relationship
├── quantity (Integer) - Item quantity
└── addedAt (LocalDateTime) - Timestamp
```

### Order Entity
```
Order
├── id (Long) - Primary Key
├── user (User) - Many-to-One relationship
├── orderItems (List<OrderItem>) - One-to-Many relationship
├── shippingAddress (Address) - Embedded
├── status (OrderStatus) - PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
├── totalAmount (BigDecimal) - Total order amount
├── createdAt (LocalDateTime) - Order creation timestamp
└── updatedAt (LocalDateTime) - Last update timestamp
```

### OrderItem Entity
```
OrderItem
├── id (Long) - Primary Key
├── order (Order) - Many-to-One relationship
├── product (Product) - Many-to-One relationship
├── quantity (Integer) - Ordered quantity
└── price (BigDecimal) - Price at time of order
```

### Address Entity
```
Address
├── id (Long) - Primary Key
├── user (User) - Many-to-One relationship
├── street (String) - Street address
├── city (String) - City name
├── state (String) - State/Province
├── zipCode (String) - Postal code
└── country (String) - Country name
```

---

## ⚙️ Configuration

### Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=ecom_user
spring.datasource.password=ecom_password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Actuator
management.endpoints.web.exposure.include=health,info
```

### Environment Variables

```bash
export DB_URL=jdbc:postgresql://localhost:5432/ecommerce
export DB_USERNAME=ecom_user
export DB_PASSWORD=ecom_password
export SERVER_PORT=8080
```

---

## 💻 Development

### IDE Setup

**IntelliJ IDEA:**
1. Open project → Select pom.xml
2. Enable Lombok annotation processing: Settings → Build → Compiler → Annotation Processors
3. Check "Enable annotation processing"

**Visual Studio Code:**
1. Install "Extension Pack for Java"
2. Install "Spring Boot Extension Pack"
3. Open project folder

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EcomApplicationTests

# Run with coverage
mvn test jacoco:report
```

### Code Style

- Follow Google Java Style Guide
- Use meaningful variable names
- Add Javadoc for public methods
- Keep methods focused and small

---

## 🐳 Docker

For complete Docker setup instructions, see [DOCKER_README.md](./DOCKER_README.md)

### Quick Start with Docker

```bash
# Build and start all services
docker-compose up --build

# View logs
docker-compose logs -f app

# Stop services
docker-compose down
```

---

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Make** your changes
4. **Commit** with clear messages (`git commit -m 'Add amazing feature'`)
5. **Push** to the branch (`git push origin feature/amazing-feature`)
6. **Open** a Pull Request

### Development Guidelines

- Write clean, readable code
- Add tests for new features
- Update documentation
- Follow existing code patterns
- Test thoroughly before submitting PR

---

## 🐛 Troubleshooting

### Database Connection Issues
```bash
# Check PostgreSQL is running
psql -U ecom_user -d ecommerce -c "SELECT version();"

# Check connection in app logs
mvn spring-boot:run | grep -i database
```

### Maven Build Issues
```bash
# Clean and rebuild
mvn clean install -U

# Skip tests
mvn clean package -DskipTests
```

### Port Already in Use
```bash
# Change port in application.properties
server.port=8081

# Or kill existing process
lsof -i :8080 | grep LISTEN
kill -9 <PID>
```

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [REST API Best Practices](https://restfulapi.net/)

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💼 Author

**Ankit Kumar**
- Email: ankit.rkumar@ascendion.com
- GitHub: [@ankitkumar](https://github.com/ankitkumar)

---

## ⭐ Show Your Support

If you found this project helpful, please give it a star! ⭐

---

**Happy Coding! 🚀**

*Last Updated: June 2026*
