# Secure Order Management System

![System Architecture Diagram](image1)

_Distributed Microservices-Based Backend Architecture using Spring Boot, JWT, Eureka, API Gateway, and OpenFeign_

---

## Table of Contents

- [Overview](#overview)
- [System Architecture](#system-architecture)
- [Core Technologies](#core-technologies)
- [Microservices Breakdown](#microservices-breakdown)
- [Security Architecture](#security-architecture)
- [Role-Based Authorization](#role-based-authorization)
- [Database Architecture](#database-architecture)
- [Inter-Service Communication](#inter-service-communication)
- [Gateway Routing](#gateway-routing)
- [Engineering Highlights](#engineering-highlights)
- [Testing Approach](#testing-approach)
- [Improvement Roadmap](#improvement-roadmap)
- [Conclusion](#conclusion)

---

## Overview

The **Secure Order Management System** is a modular, microservices-based backend application designed to emulate an enterprise-grade distributed system. Built entirely in Java with Spring Boot and Spring Cloud, this project brings together authentication, authorization, service discovery, centralized security, and inter-service communication in an educational showcase of scalable backend engineering practices.

**_Project Focus:_**
- Distributed system architecture & service isolation
- Centralized JWT-based authentication & role-based authorization
- Modular services (Auth, Product, Order) with dedicated databases
- Service discovery with Eureka
- Secure, centralized API gateway
- Feign-based inter-service HTTP communication

---

## System Architecture

The system follows a **microservices architecture** pattern, splitting responsibilities across independently deployable services. All external API traffic enters through a centralized API Gateway, which manages authentication, JWT validation, and secure routing. Each service maintains its own database, ensuring true service isolation.

![System Architecture](image1)
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/f9e58f3b-5271-4fc7-9f12-7e442760df63" />

**_Architecture Layers:_**
- **Client Layer**: Client apps interact via REST APIs.
- **API Gateway & Security Layer**: Entry point for all requests; enforces JWT authentication & role-based access.
- **Service Layer**: Independent microservices for auth, product, and order management.
- **Database Layer**: Database-per-service, ensuring modularity and autonomy.

> See [Architecture Diagram](#system-architecture) for a visual overview.

---

## Core Technologies

| Category                | Technologies                                                      |
|-------------------------|-------------------------------------------------------------------|
| Backend Framework       | Spring Boot                                                       |
| Security                | Spring Security, JWT (JJWT)                                       |
| Service Discovery       | Netflix Eureka Server                                             |
| API Gateway             | Spring Cloud Gateway                                              |
| Inter-Service Comm      | OpenFeign                                                         |
| Database                | MySQL                                                             |
| ORM                     | Spring Data JPA / Hibernate                                       |
| Build Tool              | Maven                                                             |
| Testing                 | Postman                                                           |
| Boilerplate Reduction   | Lombok                                                            |

---

## Microservices Breakdown

| Service           | Responsibility                      | Port  |
|-------------------|-------------------------------------|-------|
| Eureka Server     | Service Registry & Discovery         | 8761  |
| API Gateway       | Central Routing & Security           | 9090  |
| Auth Service      | User Authentication & JWT Provider   | 8081  |
| Product Service   | Product CRUD Management              | 8082  |
| Order Service     | Cart & Order Management              | 8083  |
| security-common   | Shared JWT Utility Module            | Shared|

- **Each service registers with Eureka** for discovery.
- All services are independently deployable and own exclusive MySQL databases.

---

## Security Architecture

**Centralized JWT Authentication & Authorization**

- **Sign-Up**: Users register via Auth Service, assigned roles (`USER`, `ADMIN`).
- **Login**: On successful login, JWT tokens are issued, containing username, role, issued/expiry timestamps, and claims.
- **Token Validation**:  
  - Centralized at the API Gateway using a **custom JWT Gateway Filter** (extends `AbstractGatewayFilterFactory`), handling:
    1. Authorization header detection
    2. Bearer token JWT parsing and validation
    3. Role extraction & route authorization
    4. Forwarding/denying the request (HTTP 401 for invalid tokens)
- **Trust Model**: Downstream services (Product/Order) trust requests forwarded by the gateway, reducing redundant authentication logic.

> **Tokens expire after ~1 hour. No refresh token flow is implemented in this version. Clients must re-authenticate post expiration.**

---

## Role-Based Authorization

- **Admin APIs** (restricted to `ADMIN` role):
  - Product addition, update, and deletion
  - Viewing all orders
- **User APIs**:
  - Managing own cart and placing/viewing orders

Authorization rules are centrally enforced in the API Gateway, streamlining policy updates and reducing duplication.

---

## Database Architecture

**_Database-per-Service_** pattern:

| Service         | Database Responsibility          |
|-----------------|----------------------------------|
| Auth Service    | User authentication data         |
| Product Service | Product inventory                |
| Order Service   | Order and cart data              |

- No cross-service direct DB access ensures true service independence.
- Enables modularity, scalability, and easier maintenance.

---

## Inter-Service Communication

- **Order Service ↔ Product Service**: Uses OpenFeign for clean HTTP-based service calls.
- **Data Handling**: DTOs standardize communication (product ID, name, category, quantity, price).
- **Typical Flow**: 
  1. User requests order
  2. Order Service fetches/validates product data via Feign client
  3. Order and order items are created accordingly

---

## Gateway Routing

- **Spring Cloud Gateway** manages routes & endpoint exposure.
- All routes and secured endpoints are configured in `application.properties`.
- Combines path-based routing with JWT/role validation for each protected endpoint.

---

## Engineering Highlights

**_Why Microservices & JWT?_**
- Practice real-world scale-out architectures
- Stateless, sessionless JWTs scale easily (no server-side session store)
- Centralized gateway-based security for maintainability
- API Gateway reduces code repetition and security drift

**_Reliability & Maintainability_**
- Service failures are isolated by design
- Eureka for service registration/discovery resilience
- DTO separation, layered architecture, shared JWT utilities, and global exception handling improve codebase maintainability

**_Performance Notes_**
- Gateway JWT validation adds negligible latency (<10ms)
- Internal service requests average 20–80ms locally (varies with DB calls)

**_Scalability_**
- Stateless components, database-per-service, and independent deployability make this architecture highly scalable
- Lacks distributed caching, container orchestration, Kubernetes, or message queues in the current stage

---

## Testing Approach

- Tested API endpoints and JWT flows using **Postman**
- Scenarios:
  - Auth & signup flows
  - Role-based endpoint access
  - Gateway JWT/error handling
  - CRUD on products and orders
  - Inter-service Feign client integration

---

## Improvement Roadmap

| Enhancement           | Purpose                        |
|-----------------------|--------------------------------|
| Dockerization         | Portable container deployment  |
| Kubernetes            | Orchestration, autoscaling     |
| Config Server         | Central configuration           |
| Refresh Tokens        | Enhanced authentication         |
| Resilience4j          | Circuit breaking, retries       |
| Distributed Tracing   | Improved observability          |
| Rate Limiting         | Prevent API abuse at gateway    |
| Security (HTTPS)      | Secure all network traffic      |
| Monitoring (ELK/SLEUTH)| Auditability, error tracking   |

---

## Conclusion

This project offers a robust educational blueprint for microservices-based backend system design—uniting modularity, centralized security, and scalable engineering patterns. While not production-hardened, it establishes a strong foundation across distributed architecture, JWT authentication/authorization, service discovery, and clean code modularity.

---
**_For architecture, code samples, or improvements, see the [diagram above](#system-architecture)._**

---

**Author:** [Kirti Namdeo Pawar](https://github.com/Kirti-Namdeo-pawar)
