# Yummy API 

## Overview
Yummy API Cake is a **Spring Boot** application built to manage various aspects of a cake shop or restaurant. This API follows **clean code** principles for easy maintainability and scalability. The application features secure authentication using **JWT** (JSON Web Tokens) and supports **role-based access control** (RBAC) to differentiate user access based on their roles.

## Features
- **Secure Authentication**: Implemented **JWT** for user authentication and authorization.
- **Role-Based Access Control**: Roles such as `USER` and `ADMIN` to manage permissions and restrict access to certain API endpoints.
- **Data Validation**: Used **DTOs (Data Transfer Objects)** with validation annotations to ensure proper data handling and prevent invalid data from being processed.
- **MySQL Database**: The application uses MySQL as its primary database for data storage and management.
- **Clean Code Architecture**: The codebase follows industry-standard practices for clean, readable, and maintainable code.

## Technologies Used
- **Spring Boot**: The core framework for building the backend.
- **JWT**: For secure authentication and authorization.
- **MySQL**: Database used to store and manage data.
- **Spring Security**: For securing endpoints and managing authentication.
- **DTOs & Validation**: To validate input data and maintain consistency.
- **Maven**: For dependency management and building the application.

## Folder Structure

Here is the folder structure of the project:
yummy-api-cake/ ├── src/ │ ├── main/ │ │ ├── java/ │ │ │ ├── com/ │ │ │ │ └── bytepulse/ │ │ │ │ └── yummy/ │ │ │ │ ├── config/ # Security and JWT configuration files │ │ │ │ ├── controller/ # REST API controllers │ │ │ │ ├── dto/ # Data Transfer Objects (DTOs) for validation │ │ │ │ ├── model/ # Entities and enums (e.g., User, Role) │ │ │ │ ├── repository/ # Data access layer (repositories) │ │ │ │ ├── security/ # JWT authentication logic and filters │ │ │ │ ├── service/ # Business logic and services │ │ │ │ └── exception/ # Exception handling classes │ │ └── resources/ │ │ ├── application.properties # Database connection and other configuration │ │ ├── static/ # Static assets (e.g., images, CSS) │ │ └── templates/ # Template files for views (if any) ├── pom.xml # Project dependencies and build configuration └── README.md # Project overview and documentation

