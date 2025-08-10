<h1 align="center">📚 BookWorld</h1>
<h3 align="center">Full-stack Book Management & E-commerce App | Java + Angular + SQL Server</h3>

<p align="center">
  <img src="https://img.shields.io/badge/Java-SpringBoot-blue" alt="Java Spring Boot" />
  <img src="https://img.shields.io/badge/Angular-18-red" alt="Angular" />
  <img src="https://img.shields.io/badge/Database-SQL%20Server-lightgrey" alt="SQL Server" />
  <img src="https://img.shields.io/github/license/nhdinhdev03/BookWorld" alt="License" />
</p>

---

## 📌 About

**BookWorld** is a secure full-stack web application for discovering, purchasing, and managing books.  
The application is built with **Java Spring Boot** (back-end), **Angular** (front-end), and **SQL Server** (database), following clean architecture principles.

---

## 🚀 Features

- **Book Catalog**: Browse and search books by category, author, or keyword.
- **Shopping Cart & Checkout**: Add books to cart, process orders securely.
- **Wishlist**: Save books for later.
- **Authentication & Authorization**: Spring Security with JWT for role-based access (Admin, User).
- **Admin Dashboard**: Manage books, users, and orders.
- **Responsive UI**: Works on desktop, tablet, and mobile.

---

## 🛠 Tech Stack

**Front-end**  
- Angular 18  
- TypeScript  
- Angular Material / Tailwind CSS  

**Back-end**  
- Java 21  
- Spring Boot 3 (Web, Data JPA, Security, Validation)  
- JWT Authentication  
- SQL Server Database  

**DevOps & Tools**  
- Maven for dependency management  
- Docker for containerization  
- GitHub Actions for CI/CD  
- Postman for API testing  

---

## 🗄 Database Schema

Main tables:
- `users` — user accounts, roles, encrypted passwords.
- `books` — book details.
- `orders` & `order_items` — order management.
- `wishlist` — saved books for later purchase.

---

## 🔒 Security

- **Spring Security** with **JWT**:
  - Login returns a signed JWT token.
  - Role-based authorization (Admin/User).
  - Passwords hashed with BCrypt.
- **CORS Configuration** for Angular front-end.
- SQL Injection protection via JPA Prepared Statements.

---

## 📦 Installation & Setup

### 1️⃣ Back-end (Spring Boot)
```bash
# Clone repository
git clone https://github.com/nhdinhdev03/BookWorld.git

cd backend
mvn clean install
mvn spring-boot:run
