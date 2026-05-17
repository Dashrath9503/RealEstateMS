# 🏠 Real Estate Property Management System

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen?style=for-the-badge&logo=springboot)
![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.x-purple?style=for-the-badge&logo=bootstrap)
![JWT](https://img.shields.io/badge/JWT-Security-red?style=for-the-badge&logo=jsonwebtokens)
![Maven](https://img.shields.io/badge/Maven-Build-yellow?style=for-the-badge&logo=apachemaven)

**A full-stack Real Estate web application inspired by MagicBricks & 99acres**

*MCA Final Year Project — Swami Ramanand Teerth Marathwada University, Nanded*

</div>

---

## 📋 Table of Contents

- [About the Project](#-about-the-project)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [Setup & Installation](#-setup--installation)
- [Configuration](#-configuration)
- [Running the Application](#-running-the-application)
- [API Endpoints](#-api-endpoints)
- [Screenshots](#-screenshots)
- [Important Notes](#-important-notes)
- [Future Scope](#-future-scope)

---

## 📖 About the Project

The **Real Estate Property Management System** is a web-based application that digitizes and simplifies property transactions in India. It provides a centralized platform where:

- 🏢 **Property Owners** can list and manage properties with images
- 🔍 **Buyers** can search, filter, save, and inquire about properties
- 👨‍💼 **Admins** can verify listings, manage users, and monitor analytics

> ⚠️ **Note:** This project requires manual configuration before running. Credentials are intentionally NOT included for security reasons.

---

## ✨ Features

### 🔐 Authentication & Security
- JWT-based stateless authentication
- BCrypt password encryption
- Role-based access control (Admin / Owner / Buyer)
- Spring Security filter chain

### 🏠 Property Management
- Add, edit, delete property listings
- Multi-image upload support (up to 10MB each)
- Property types: Apartment, Villa, Plot, Commercial
- Admin approval workflow (PENDING → APPROVED / REJECTED)
- Mark as SOLD functionality
- View count tracking per property

### 🔍 Search & Discovery
- Advanced search with multiple filters (City, Type, Price, Bedrooms, Area)
- Sort by: Newest, Price Low-High, Price High-Low, Most Viewed
- Recently viewed property tracking (localStorage)
- Top/Featured properties section

### 💬 Inquiry System
- Direct buyer-to-owner messaging
- Status tracking: PENDING → READ → REPLIED
- Email notifications on inquiry and reply
- Owner reply with email to buyer

### ❤️ Favorites / Wishlist
- Save and remove properties
- Duplicate save prevention (unique constraint)
- View all saved properties in dashboard

### ⭐ Reviews & Ratings
- 5-star rating system
- One review per buyer per property
- Average rating displayed on property cards

### 📊 Admin Dashboard
- Total Users, Properties, Inquiries, Reviews analytics
- Pending approvals management
- All properties overview
- User management (view and delete)

---

## 🛠️ Tech Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Backend Framework** | Spring Boot | 3.2.0 |
| **Language** | Java | JDK 17 |
| **Database** | MySQL | 8.0 |
| **ORM** | Hibernate / Spring Data JPA | 6.x |
| **Security** | Spring Security + JWT (JJWT) | 0.11.5 |
| **Email** | Spring Mail (Gmail SMTP) | - |
| **Build Tool** | Apache Maven | 3.x |
| **Frontend** | HTML5, CSS3, JavaScript (ES6+) | - |
| **UI Framework** | Bootstrap | 5.x (CDN) |
| **IDE** | Spring Tool Suite / Eclipse | 4.x |

---

## 📁 Project Structure

```
RealEstateMS/
├── src/
│   └── main/
│       ├── java/com/realestate/
│       │   ├── RealEstateMSApplication.java
│       │   ├── config/
│       │   │   ├── SecurityConfig.java
│       │   │   └── WebConfig.java
│       │   ├── controller/
│       │   │   ├── AdminController.java
│       │   │   ├── AuthController.java
│       │   │   ├── FavoriteController.java
│       │   │   ├── InquiryController.java
│       │   │   ├── PropertyController.java
│       │   │   └── ReviewController.java
│       │   ├── dto/
│       │   │   ├── AdminStatsResponse.java
│       │   │   ├── AuthResponse.java
│       │   │   ├── InquiryRequest.java
│       │   │   ├── InquiryResponse.java
│       │   │   ├── LoginRequest.java
│       │   │   ├── PropertyRequest.java
│       │   │   ├── PropertyResponse.java
│       │   │   ├── RegisterRequest.java
│       │   │   └── ReviewRequest.java
│       │   ├── model/
│       │   │   ├── Favorite.java
│       │   │   ├── Inquiry.java
│       │   │   ├── Property.java
│       │   │   ├── PropertyImage.java
│       │   │   ├── Review.java
│       │   │   └── User.java
│       │   ├── repository/
│       │   │   ├── FavoriteRepository.java
│       │   │   ├── InquiryRepository.java
│       │   │   ├── PropertyRepository.java
│       │   │   ├── ReviewRepository.java
│       │   │   └── UserRepository.java
│       │   ├── security/
│       │   │   ├── JwtAuthFilter.java
│       │   │   ├── JwtUtils.java
│       │   │   └── UserDetailsServiceImpl.java
│       │   └── service/
│       │       ├── AdminService.java
│       │       ├── AuthService.java
│       │       ├── EmailService.java
│       │       ├── FavoriteService.java
│       │       ├── InquiryService.java
│       │       ├── PropertyService.java
│       │       └── ReviewService.java
│       └── resources/
│           ├── application.properties   ← ⚠️ Configure this!
│           └── static/
│               ├── css/
│               │   └── style.css
│               ├── js/
│               │   └── api.js
│               └── pages/
│                   ├── index.html
│                   ├── login.html
│                   ├── listings.html
│                   ├── property-detail.html
│                   ├── dashboard.html
│                   ├── add-property.html
│                   ├── favorites.html
│                   └── admin-dashboard.html
└── pom.xml
```

---

## 🗄️ Database Schema

```sql
-- 6 Tables
users           → id, name, email, password, phone, role, created_at
properties      → id, title, description, price, city, address, 
                  property_type, bedrooms, bathrooms, area_sqft,
                  status, view_count, owner_id (FK), created_at
property_images → id, property_id (FK), image_url
favorites       → id, user_id (FK), property_id (FK), added_at
inquiries       → id, property_id (FK), buyer_id (FK), 
                  message, status, created_at
reviews         → id, property_id (FK), user_id (FK), 
                  rating, comment, created_at
```

---

## ⚙️ Setup & Installation

### Prerequisites

Make sure you have the following installed:

- ☕ **Java JDK 17** — [Download](https://adoptium.net/)
- 🐬 **MySQL 8.0** — [Download](https://dev.mysql.com/downloads/)
- 📦 **Maven 3.6+** — [Download](https://maven.apache.org/download.cgi)
- 🛠️ **Eclipse / Spring Tool Suite** — [Download](https://spring.io/tools)
- 📧 **Gmail account** with 2-Step Verification enabled

### Step 1 — Clone the Repository

```bash
git clone https://github.com/Dashrath9503/real-estate-management-system.git
cd real-estate-management-system
```

### Step 2 — Create MySQL Database

Open **MySQL Workbench** or MySQL CLI and run:

```sql
CREATE DATABASE realestate_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

> Tables are created automatically by Hibernate on first run (`ddl-auto=update`)

### Step 3 — Configure application.properties

Navigate to `src/main/resources/application.properties` and update:

```properties
# ⚠️ REQUIRED — Update these values

# Database
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

# JWT Secret (minimum 32 characters, use your own)
app.jwt.secret=YOUR_OWN_SECRET_KEY_MIN_32_CHARACTERS

# Email (Gmail SMTP)
spring.mail.username=YOUR_GMAIL@gmail.com
spring.mail.password=YOUR_16_DIGIT_APP_PASSWORD
```

### Step 4 — Gmail App Password Setup

```
1. Go to → myaccount.google.com
2. Security → 2-Step Verification → Enable it
3. Go to → myaccount.google.com/apppasswords
4. App name: RealEstateMS → Create
5. Copy the 16-digit password (no spaces)
6. Paste in application.properties
```

> ⚠️ Normal Gmail password will NOT work. App Password is required.

### Step 5 — Create Admin Account

After running the application:

```
1. Register normally at http://localhost:8080/pages/login.html
2. Open MySQL Workbench
3. Run this SQL query:

UPDATE users 
SET role = 'ADMIN' 
WHERE email = 'your-registered-email@gmail.com';
```

> ⚠️ Without this step, Admin dashboard will not be accessible!

---

## 🚀 Running the Application

### Option 1 — Eclipse / STS

```
1. File → Import → Maven → Existing Maven Projects
2. Browse → Select project folder → Finish
3. Wait for Maven to download dependencies (3-5 min)
4. Right-click RealEstateMSApplication.java
5. Run As → Java Application
```

### Option 2 — Maven Command Line

```bash
mvn clean install
mvn spring-boot:run
```

### Access the Application

```
🌐 Home Page     → http://localhost:8080
🔐 Login         → http://localhost:8080/pages/login.html
🏠 Properties    → http://localhost:8080/pages/listings.html
📊 Dashboard     → http://localhost:8080/pages/dashboard.html
👑 Admin Panel   → http://localhost:8080/pages/admin-dashboard.html
```

---

## 🔌 API Endpoints

### Auth
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/auth/register` | Public | Register new user |
| POST | `/auth/login` | Public | Login & get JWT token |

### Properties
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/properties` | Public | Get all approved properties |
| GET | `/properties/search` | Public | Search with filters |
| GET | `/properties/top` | Public | Get top viewed properties |
| GET | `/properties/{id}` | Public | Get property by ID |
| GET | `/properties/my` | Owner | Get owner's properties |
| POST | `/properties` | Owner | Add new property |
| PUT | `/properties/{id}` | Owner | Edit property |
| DELETE | `/properties/{id}` | Owner | Delete property |

### Inquiries
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/inquiries/property/{id}` | Buyer | Send inquiry |
| GET | `/inquiries/my` | Buyer | Get my inquiries |
| GET | `/inquiries/received` | Owner | Get received inquiries |
| PUT | `/inquiries/{id}/reply` | Owner | Reply to inquiry |

### Favorites
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/favorites/{propertyId}` | Buyer | Add to favorites |
| DELETE | `/favorites/{propertyId}` | Buyer | Remove from favorites |
| GET | `/favorites` | Buyer | Get all favorites |

### Reviews
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/reviews/property/{id}` | Buyer | Add review |
| GET | `/reviews/property/{id}` | Public | Get property reviews |

### Admin
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/admin/stats` | Admin | Get analytics stats |
| GET | `/admin/properties/pending` | Admin | Get pending properties |
| PUT | `/admin/properties/{id}/approve` | Admin | Approve property |
| PUT | `/admin/properties/{id}/reject` | Admin | Reject property |
| GET | `/admin/users` | Admin | Get all users |
| DELETE | `/admin/users/{id}` | Admin | Delete user |

---

## 📸 Screenshots

> Add screenshots after setting up the project

| Page | Description |
|------|-------------|
| Home Page | Hero section with search bar |
| Login/Register | JWT authentication |
| Property Listings | Filter sidebar with property cards |
| Property Detail | Full info with inquiry form |
| Owner Dashboard | Listings and inquiries management |
| Buyer Dashboard | Saved properties and activity |
| Admin Panel | Analytics and approval management |

---

## ⚠️ Important Notes

```
1. application.properties is NOT pre-configured
   → You MUST add your own DB credentials

2. Gmail App Password is REQUIRED
   → Normal password will NOT work

3. Admin account needs manual SQL setup
   → No default admin credentials

4. JWT Secret must be changed
   → Default is intentionally removed

5. MySQL must be running before starting the app
   → Start MySQL80 in Windows Services
   
6. Port 8080 must be free
   → Change server.port if needed
```

### Common Errors & Fixes

| Error | Fix |
|-------|-----|
| `Connection refused` | Start MySQL service |
| `Email auth failed` | Setup Gmail App Password |
| `403 Forbidden` | Clear localStorage, re-login |
| `Port 8080 in use` | Add `server.port=8085` |
| `Admin access denied` | Run SQL UPDATE for role |
| `JWT expired` | Clear localStorage, re-login |

---

## 🔮 Future Scope

- 🗺️ **Google Maps Integration** — Property location on map
- 💳 **Payment Gateway** — Razorpay online booking
- 💬 **Real-time Chat** — WebSocket buyer-owner chat
- 📱 **Mobile App** — React Native Android/iOS
- 🤖 **AI Price Prediction** — ML-based price suggestions
- 🌐 **Cloud Deployment** — AWS / Railway.app
- 🎥 **Virtual Tours** — 360° property view

---

## 👨‍💻 Developer

**Dashrath Deshmukh**

- 🎓 MCA Final Year — SRTMU, Nanded (2025-2026)
- 💼 GitHub: [@Dashrath9503](https://github.com/Dashrath9503)
- 🔗 LinkedIn: [dashrath-deshmukh](https://linkedin.com/in/dashrath-deshmukh-9a732b368/)
- 📧 Email: rohan9503473615@gmail.com

---

## 📄 License

This project is developed for **educational purposes** as an MCA Final Year Project.

---

<div align="center">

⭐ **If you found this helpful, please give it a star!** ⭐

*Made with ❤️ using Spring Boot & Java*

</div>
