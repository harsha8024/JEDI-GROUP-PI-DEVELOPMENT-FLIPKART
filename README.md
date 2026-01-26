# FlipFit - Gym Booking System 🏋️‍♂️

**FlipFit** is a Java-based enterprise application designed to streamline gym bookings, center management, and fitness scheduling. This project was developed as part of the Flipkart JEDI program to digitize the fitness space by connecting gym owners with fitness enthusiasts.

## 📋 Problem Statement
Flipkart is entering the fitness market by partnering with gyms across Bangalore. The goal is to build a scalable UI and backend system that allows:
* **Gym Owners** to register centers and manage slots.
* **Customers** to view availability and book workout sessions.
* **Admins** to validate and approve gym centers and owners.

## 🚀 Features

### 💪 For Gym Customers
* **User Registration & Login:** Secure access to the platform.
* **View Gyms:** Browse gym centers by city and view details.
* **Book Slots:** Real-time booking of workout slots (checking availability to prevent overbooking).
* **Manage Bookings:** View past/upcoming bookings and cancel scheduled sessions.
* **Waitlist System:** (Bonus) Automatic waitlisting when slots are full.

### 🏢 For Gym Owners
* **Gym Management:** Add new gym centers and details (Location, Name, etc.).
* **Slot Management:** Add and update time slots for specific gyms.
* **View Bookings:** Monitor customer bookings for owned centers.
* **Profile Management:** Update owner details and credentials.

### 🛠 For Admins
* **Approvals:** Validate and approve new Gym Owners and Gym Centers before they go live.
* **System Monitoring:** View all users, gyms, and bookings across the platform.
* **Reports:** Generate reports on gym utilization and booking trends.

---

## 🛠️ Tech Stack
* **Language:** Java 17
* **Build Tool:** Maven
* **IDE:** Spring Tool Suite (STS) / Visual Studio Code / Eclipse
* **Database:** SQL (JDBC implementation)
* **Documentation:** UML (StarUML/Draw.io)

---

## ⚙️ Setup & Installation

### Prerequisites
* **Java Development Kit (JDK) 17** or higher.
* **Maven** (optional if using an IDE with built-in Maven support).
* **MySQL** (or any configured SQL database).

### Steps to Run Locally
1.  **Clone the Repository**
    ```bash
    git clone https://github.com/your-username/JEDI-GROUP-PI-DEVELOPMENT-FLIPKART.git
    ```
2.  **Import into IDE**
    * **STS/Eclipse:** `File` > `Import` > `Maven` > `Existing Maven Projects`. Select the `JEDIPIDevelopmentJAVAPOS` folder.
    * **VS Code:** Open the `JEDIPIDevelopmentJAVAPOS` folder directly.
3.  **Configure Database**
    * Ensure your MySQL server is running.
    * Update the `DBConnection.java` properties (URL, Username, Password) in the `utils` package.
4.  **Run the Application**
    * Navigate to `src/com/flipfit/client/FlipfitApplication.java`.
    * Run as **Java Application**.
    * Follow the on-screen console prompts to interact with the system.
