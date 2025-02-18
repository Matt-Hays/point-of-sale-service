# Point of Sale (POS) Service Microservice

The POS Service is a Spring Boot-based microservice designed to manage transactions, registers, employees, products, and customers for a point-of-sale system. It provides a RESTful API to create, update, and retrieve transactions while ensuring consistency and integrity of business operations.

---

## Table of Contents

- [Features](#features)
- [Architecture Overview](#architecture-overview)
- [API Endpoints](#api-endpoints)
    - [Transaction Endpoints](#transaction-endpoints)
    - [Register Endpoints](#register-endpoints)
    - [Product Endpoints](#product-endpoints)
    - [Employee Endpoints](#employee-endpoints)
    - [Customer Endpoints](#customer-endpoints)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Service](#running-the-service)
- [Error Handling](#error-handling)
- [License](#license)

---

## Features

- **Transaction Management:** Create and modify transactions, including line items.
- **Register Management:** Manage cash registers and their associated locations.
- **Product Management:** CRUD operations for products.
- **Employee Management:** Handle employee records and their associated data.
- **Customer Management:** Create, update, retrieve, and delete customer records.
- **RESTful API:** Structured endpoints for seamless integration.
- **Optimistic Locking:** Prevents conflicts in concurrent transactions.

---

## Architecture Overview

The microservice follows a layered architecture:

- **Controllers:**
    - `TransactionController`, `RegisterController`, `ProductController`, `EmployeeController`, `CustomerController` expose REST endpoints.

- **Services:**
    - `TransactionService`, `RegisterService`, `ProductService`, `EmployeeService`, `CustomerService` handle business logic and data persistence.

- **Repositories:**
    - Spring Data JPA repositories for transaction, register, product, employee, and customer data.

- **Models:**
    - Entity classes for Transaction, Register, Product, Employee, Customer, and related line items.

The service uses **Jakarta Persistence (JPA)** for ORM and **Spring Boot** for rapid development.

---

## API Endpoints

### Transaction Endpoints

| Method | Endpoint                          | Description                                               |
| ------ | --------------------------------- | --------------------------------------------------------- |
| GET    | `/v1/transaction`                 | Retrieve all transactions.                                |
| GET    | `/v1/transaction/{id}`            | Retrieve details of a specific transaction.              |
| POST   | `/v1/transaction`                 | Create a new transaction.                                |
| PATCH  | `/v1/transaction/{id}`            | Update an existing transaction.                         |
| POST   | `/v1/transaction/{id}/item`       | Add a line item to a transaction.                        |
| DELETE | `/v1/transaction/{id}`            | Delete a transaction.                                    |

---

### Register Endpoints

| Method | Endpoint          | Description                                   |
| ------ | ----------------- | --------------------------------------------- |
| GET    | `/v1/register`    | Retrieve all registers.                       |
| GET    | `/v1/register/{id}` | Retrieve details of a specific register.    |
| POST   | `/v1/register`    | Create a new register.                        |
| PATCH  | `/v1/register/{id}` | Update an existing register.                |
| DELETE | `/v1/register/{id}` | Delete a register.                          |

---

### Product Endpoints

| Method | Endpoint          | Description                                   |
| ------ | ----------------- | --------------------------------------------- |
| GET    | `/v1/product`      | Retrieve all products.                        |
| GET    | `/v1/product/{id}` | Retrieve details of a specific product.      |
| POST   | `/v1/product`      | Create a new product.                         |
| PATCH  | `/v1/product/{id}` | Update an existing product.                  |
| DELETE | `/v1/product/{id}` | Delete a product.                            |

---

### Employee Endpoints

| Method | Endpoint          | Description                                   |
| ------ | ----------------- | --------------------------------------------- |
| GET    | `/v1/employee`     | Retrieve all employees.                       |
| GET    | `/v1/employee/{id}` | Retrieve details of a specific employee.    |
| POST   | `/v1/employee`     | Create a new employee.                        |
| PATCH  | `/v1/employee/{id}` | Update an existing employee.                |
| DELETE | `/v1/employee/{id}` | Delete an employee.                          |

---

### Customer Endpoints

| Method | Endpoint          | Description                                   |
| ------ | ----------------- | --------------------------------------------- |
| GET    | `/v1/customer`     | Retrieve all customers.                       |
| GET    | `/v1/customer/{id}` | Retrieve details of a specific customer.    |
| POST   | `/v1/customer`     | Create a new customer.                        |
| PATCH  | `/v1/customer/{id}` | Update an existing customer.                |
| DELETE | `/v1/customer/{id}` | Delete a customer.                          |

---

## Prerequisites

Before running the POS Service, ensure you have the following installed:

- **Java:** JDK 17 or higher
- **Build Tool:** Maven
- **Database:** PostgreSQL
- **IDE:** Optional (e.g., IntelliJ IDEA, Eclipse) for development

---

## Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/Matt-Hays/point-of-sale-service.git
   cd pos-service
   ```

2. **Build the Service:**

   ```bash
   mvn clean install
   ```

---

## Running the Service

1. **Run with Maven:**

   ```bash
   mvn spring-boot:run
   ```

2. **Run with Docker:**

   ```bash
   mvn clean spring-boot:build-image -DskipTests
   docker run -p 8083:8083 docker.io/library/point-of-sale-service:0.0.1-SNAPSHOT
   ```

---

## Error Handling
The microservice handles errors using HTTP response codes:
- **404 Not Found** - Resource does not exist
- **400 Bad Request** - Invalid input data
- **422 Unprocessable Entity** - Optimistic locking issues
- **500 Internal Server Error** - General server errors

