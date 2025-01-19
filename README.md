# Yummy API

## Overview

Yummy API Cake is a **Spring Boot** application built to manage various aspects of a cake shop or restaurant. This API follows **clean code** principles for easy maintainability and scalability. The application features secure authentication using **JWT** (JSON Web Tokens) and supports **role-based access control** (RBAC) to differentiate user access based on their roles.

## Features

- **Secure Authentication**: Implemented **JWT** for user authentication and authorization.
- **Role-Based Access Control**: Roles such as `USER` and `ADMIN` to manage permissions and restrict access to certain API endpoints.
- **Password Management**: Added support for password reset functionality, including the ability to send password reset emails and update user passwords securely.
- **Data Validation**: Used **DTOs (Data Transfer Objects)** with validation annotations to ensure proper data handling and prevent invalid data from being processed.
- **MySQL Database**: The application uses MySQL as its primary database for data storage and management.
- **Clean Code Architecture**: The codebase follows industry-standard practices for clean, readable, and maintainable code.
- **Email Sending Feature**: Integrated an email functionality to send emails via SMTP. The feature uses `EmailController`, `EmailService`, and `EmailDetails` to send emails such as notifications, password resets, and more.

## Technologies Used

- **Spring Boot**: The core framework for building the backend.
- **JWT**: For secure authentication and authorization.
- **MySQL**: Database used to store and manage data.
- **Spring Security**: For securing endpoints and managing authentication.
- **DTOs & Validation**: To validate input data and maintain consistency.
- **Maven**: For dependency management and building the application.
- **JavaMailSender**: For sending emails via SMTP.
