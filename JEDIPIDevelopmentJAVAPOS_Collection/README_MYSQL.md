# FlipFit MySQL Integration - Quick Start

## ⚡ Quick Setup (5 Minutes)

Follow these steps to get MySQL connected to your FlipFit application:

### 1️⃣ Install & Start MySQL
```bash
brew install mysql
brew services start mysql
mysql_secure_installation
```

### 2️⃣ Create Database
```bash
mysql -u root -p
```
```sql
CREATE DATABASE IF NOT EXISTS flipfit_db;
exit
```

### 3️⃣ Load Schema
```bash
cd /Users/deepanshu.gupta3/Desktop/JEDI-Project/JEDI-GROUP-PI-DEVELOPMENT-FLIPKART/JEDIPIDevelopmentJAVAPOS
mysql -u root -p flipfit_db < database/schema.sql
```

### 4️⃣ Download JDBC Driver
- Download from: https://dev.mysql.com/downloads/connector/j/
- Add JAR to Eclipse: Right-click project → Build Path → Configure Build Path → Libraries → Add External JARs

### 5️⃣ Update Password
Edit `database/database.properties`:
```properties
db.password=YOUR_MYSQL_PASSWORD
```

### 6️⃣ Test Connection
Run `DatabaseConnection.java` main method in Eclipse

---

## 📁 What's Been Created

✅ **Database Schema** - `database/schema.sql` (users, gyms, slots, bookings tables)  
✅ **Configuration** - `DatabaseConfig.java`, `DatabaseConnection.java`  
✅ **DAOs** - `UserDAO`, `GymDAO`, `SlotDAO`, `BookingDAO`  

---

## ⚠️ Important

Before running the application:
1. Make sure MySQL is running: `brew services list`
2. Update your password in `database/database.properties`
3. Service layer needs to be updated to use DAOs (next step)

---

## 📖 Full Documentation

See `MYSQL_SETUP_GUIDE.md` for detailed instructions and troubleshooting.
