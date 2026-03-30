E-Commerce Microservices System

A scalable, production-style E-Commerce backend system built using Spring Boot Microservices Architecture.
This project demonstrates real-world backend design, including authentication, inter-service communication, caching, and distributed system principles.

Tech Stack
Java + Spring Boot
Spring Security (JWT Authentication)
Spring Cloud (Eureka Server)
API Gateway
Redis (Caching - Product Service)
REST Template (Inter-service communication)
PostgreSQL / MySQL (Service-wise DB)
Swagger UI (API Testing)
Maven

Microservices Overview

1. User Service
Handles User Registration & Login
Implements JWT Authentication & Authorization
Password encryption using BCrypt
Role-based access (ADMIN / USER)
CRUD operations for user profile

Core Features:
Secure login system
Token generation & validation
Protected endpoints 

2. Product Service
Manages Product Catalog
CRUD operations for products
Search & filtering support
Redis caching implemented for faster product retrieval

Core Features:
Product caching using Redis
Optimized performance for GET APIs
Reduced DB load

3. Order Service
Handles Order Creation & Management
Communicates with:
User Service (validation)
Product Service (product details & stock)
Deducts product quantity on order

Core Features:
UUID-based order tracking
Inter-service communication
Business workflow handling

4. Payment Service
Handles Payment Processing
Simulates payment success/failure
Updates order status after payment

Core Features:
Payment verification logic
Integration with Order Service
Mock payment gateway

System Architecture
Each service runs on different ports
All services are registered with Eureka Server
Communication via REST APIs
Optional API Gateway for centralized routing
JWT Token-based security across services

Security Implementation
JWT-based authentication
Role-based authorization:
a) USER → limited access
b) ADMIN → full access
Custom Filters:
OncePerRequestFilter
Token validation in each service
Secure endpoints using Spring Security
Redis Caching (Product Service)
Implemented caching for:
GET Product by ID
Annotations used:
@Cacheable
@CachePut
@CacheEvict

Benefit:
Faster response time
Reduced database load

Complete Flow
User registers & logs in
JWT token is generated
User fetches products (cached via Redis)
User places order
Order Service validates user & product
Stock is updated
Payment is processed
Order status is updated
API Testing
Swagger UI available for each service
Tested using Postman
Secured endpoints require JWT token (Bearer Token)

📁 Project Structure
E-Commerce Microservices
├── user-service
├── product-service
├── order-service
├── payment-service
├── api-gateway
└── eureka-server
Key Concepts Demonstrated
Microservices Architecture
Service Discovery (Eureka)
API Gateway Routing
JWT Authentication & Authorization
Redis Caching
Inter-service Communication
Clean Layered Architecture (Controller → Service → Repository)

Why This Project is Important?
This project demonstrates:
Real-world backend system design
Strong understanding of distributed systems
Practical implementation of Spring Boot + Microservices
Interview-ready concepts used in top tech companies
