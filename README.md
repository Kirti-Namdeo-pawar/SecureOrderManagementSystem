---

# 🚀 **Secure Order Management System**

## 🏗 Microservices Architecture using Spring Boot, JWT, Eureka & API Gateway

---

# 📌 **Project Overview**

The **Secure Order Management System** is a complete **microservices-based backend application** built using **Spring Boot**.

This project demonstrates real-world implementation of:

- ✅ Microservices Architecture  
- ✅ Service Registry (Eureka)  
- ✅ API Gateway (Spring Cloud Gateway)  
- ✅ JWT Authentication  
- ✅ Role-Based Authorization  
- ✅ Inter-Service Communication (OpenFeign)  
- ✅ Centralized Security  
- ✅ Separate Database per Service  

---

# 🧱 **System Architecture**

## 🔹 Microservices Used

| Service Name        | Port  | Description |
|--------------------|-------|-------------|
| **Service Registry** | 8761  | Eureka Server |
| **API Gateway**      | 9090  | Entry point for all requests |
| **Auth Service**     | 8081  | Authentication & JWT generation |
| **Product Service**  | 8082  | Product management |
| **Order Service**    | 8083  | Cart & Order management |
| **security-common**  | —     | Shared JWT utility module |

Each service runs independently and registers itself with **Eureka Server**.

---

# 🔄 **Complete System Flow**

---

## 1️⃣ Service Startup Order

1. Start **Service Registry**
2. Start **Auth Service**
3. Start **Product Service**
4. Start **Order Service**
5. Start **API Gateway**

All services register themselves with Eureka.

---

# 🔐 **Authentication & Authorization Flow**

---

## 📝 Step 1: User Signup

- User registers via **Auth Service**
- Role assigned:
  - `USER`
  - `ADMIN`
- User saved in Auth database

---

## 🔑 Step 2: Login & JWT Generation

After login:

- Credentials validated
- JWT token generated
- Token returned to client

### JWT Contains:

- Username  
- Role  
- Issued Date  
- Expiration Date  
- Claims  

---

## 🌐 Step 3: Client Sends Request

All requests go through:

http://localhost:9090


Header:

Authorization: Bearer <JWT_TOKEN>


---

## 🚦 Step 4: API Gateway Validation

The **API Gateway** contains:

- Custom **JWT Authentication Filter**
- Overridden `apply()` method
- Runs for every request

### Gateway Process:

1. Extract token from:
HTTP Header → Authorization

2. Validate JWT
3. Extract:
- Username
- Role
- Expiration
4. Apply Role-Based Rules
5. Forward request to correct microservice

If token invalid → `401 Unauthorized`

---

# 🛡 **Security Design**

✔ API Gateway performs:

- JWT validation  
- Role extraction  
- Authorization control  

✔ Product & Order services:

- Trust API Gateway  
- Do not re-validate JWT  
- Contain security configuration but rely on Gateway filtering  

This ensures **centralized security management**.

---

# 🔁 **Inter-Service Communication**

Order Service communicates with Product Service using:

### ✅ OpenFeign (Thin Client)

Flow:

1. Order Service calls Product Service
2. Retrieves product details
3. Maps response using `ProductResponse` class
4. Creates Order & OrderItem

---

# 🗂 **Database Architecture**

Each microservice maintains its own database:

- Auth DB → Users  
- Product DB → Products  
- Order DB → Orders & Cart  

This follows true microservices design principles.

---

# 📡 **API Summary**

---

## 🔐 Auth Service (Port 8081)

- `POST /auth/signup`
- `POST /auth/login`

---

## 📦 Product Service (Port 8082)

- `POST /products/add`
- `GET /products/{id}`
- `GET /products/all`
- `PUT /products/update`
- `DELETE /products/delete/{id}`

(Admin Protected)

---

## 🛒 Order Service (Port 8083)

### 👤 User APIs

- `POST /orders/cart/add`
- `PUT /orders/cart/update`
- `DELETE /orders/cart/remove`
- `POST /orders/place`
- `GET /orders/my`

### 👑 Admin APIs

- `GET /orders/all`
- `PUT /orders/update-status`

---

# ⚙ **application.properties Skeleton**

---

## 🔐 Auth Service

```properties
server.port=8081
spring.application.name=AUTH-SERVICE

spring.datasource.url=jdbc:mysql://localhost:3306/auth_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=3600000

eureka.client.service-url.defaultZone=http://localhost:8761/eureka
🌐 API Gateway
server.port=9090
spring.application.name=API-GATEWAY

eureka.client.service-url.defaultZone=http://localhost:8761/eureka

spring.cloud.gateway.routes[0].id=auth-service
spring.cloud.gateway.routes[0].uri=lb://AUTH-SERVICE
spring.cloud.gateway.routes[0].predicates[0]=Path=/auth/**



---






##🧠 **Key Concepts Learned**




Microservices Architecture

Service Discovery (Eureka)

API Gateway Routing

JWT Authentication

Role-Based Authorization

Custom Gateway Filters

OpenFeign Communication

Centralized Security

Stateless Authentication

⚠ Challenges Faced
JWT validation flow

Role-based routing logic

Inter-service communication issues

Correct service startup sequence

Managing multiple ports

Eureka configuration

🚀 How To Run
Clone repository

Configure application.properties in each service

Start MySQL databases

Start services in order:

Service Registry

Auth Service

Product Service

Order Service

API Gateway

Test using Postman via Gateway port (9090)

🏁 Conclusion
This project demonstrates a fully functional, secure microservices architecture implementing:

Authentication

Authorization

API Gateway Security

Service Discovery

Inter-Service Communication
