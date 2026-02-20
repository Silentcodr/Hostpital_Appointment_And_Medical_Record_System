# Hospital Appointment & Medical Record System

The Hospital Appointment & Medical Record System is a full-stack, comprehensive web application designed to streamline the management of healthcare facilities. Built with a modern, responsive Glassmorphic UI and a robust database-driven backend, the system allows hospital administrators to efficiently track departments, manage doctor schedules, register patients, and organize upcoming appointments—all in real time.

---

## 🚀 Features

- **Dashboard Operations:** Quick key performance indicators (KPIs) overlaying recent hospital activity.
- **Department Management:** Add, update, and manage different medical specialties within the hospital.
- **Doctor Roster:** Register doctors with their corresponding departments, specialities, consultation fees, and available days.
- **Patient Registration:** Seamlessly register new patients into the healthcare system.
- **Appointment Scheduling:** Book, reschedule, and cancel patient visits with dynamic date tracking.
- **Modern UI:** Built from the ground up utilizing CSS Glassmorphism to create a sleek, premium, interview-friendly aesthetic.

## 💻 Tech Stack

### Frontend (User Interface)
- **Framework:** React.js (via Vite)
- **Routing:** React Router v6
- **Styling:** Custom CSS (Glassmorphism design language)
- **Icons:** Lucide React
- **HTTP Client:** Axios (Configured for environment-based routing)

### Backend (Server & API)
- **Framework:** Spring Boot (Java 21)
- **ORM:** Spring Data JPA (Hibernate)
- **Database:** PostgreSQL
- **Build Tool:** Maven

---

## 🛠️ Local Setup & Installation

### Prerequisites
- Node.js (v18 or higher)
- Java Development Kit (JDK 21 or higher)
- PostgreSQL Server

### Backend Initialization
1. Ensure your local PostgreSQL server is running.
2. Initialize a new database (e.g., `hospital_db`).
3. Navigate to the backend directory:
   ```bash
   cd hospital_appoinment_record_system
   ```
4. Verify your database connection settings in `src/main/resources/application.properties` (or set the `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` environment variables).
5. Build and run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
   *The backend will typically start on `http://localhost:8080`.*

### Frontend Initialization
1. Open a new terminal and navigate to the frontend directory:
   ```bash
   cd hospital_appointment_frontend
   ```
2. Install the necessary Node.js dependencies:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
   *The frontend will typically start on `http://localhost:5173`.*

---

## 🌐 Production Readiness

This project is configured out-of-the-box for cloud hosting:
- Frontend API calls default to `VITE_API_BASE_URL` if present, routing gracefully in production.
- Backend database targets default to standard cloud environment variables (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`).
