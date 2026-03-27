# TranspoGov

A Spring Boot–based backend for managing transportation/government services.  
This repository follows a conventional layered architecture with clear separation of concerns.

---

## 📂 Project Structure
src/main/java/com/cts/transpogov/
├── 🎮 controllers/          # REST Controllers (API Endpoints)
├── 📦 dtos/                 # Data Transfer Objects (Request/Response mapping)
│   ├── citizen/             # Citizen-related data structures
│   ├── program/             # Program-related data structures
│   ├── route/               # Route-related data structures
│   ├── ticket/              # Ticket-related data structures
│   └── user/                # User-related data structures
├── 🛠️ service/              # Business Logic & Service Interfaces
├── 💾 models/               # JPA Entities / Database Schema
├── 🗄️ repositories/         # Data Access Layer (Spring Data JPA)
├── 📑 enums/                # Constant Types (e.g., Status, Roles)
└── ⚠️ exceptions/           # Global Exception Handling & Custom Errors