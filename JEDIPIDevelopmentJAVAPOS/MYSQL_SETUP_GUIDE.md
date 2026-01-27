# FlipFit MySQL Integration - Step-by-Step Setup Guide

This guide will walk you through setting up MySQL for your FlipFit POS system.

---

## 📋 Prerequisites Checklist

- [ ] macOS with Homebrew installed
- [ ] Java Development Kit (JDK) installed
- [ ] Eclipse IDE (already set up for your project)

---

## Step 1: Install MySQL Server

### Option A: Using Homebrew (Recommended)

```bash
# Install MySQL
brew install mysql

# Start MySQL service
brew services start mysql

# Secure the installation
mysql_secure_installation
```

When running `mysql_secure_installation`, you'll be prompted:
1. **Set root password**: Choose a secure password (remember this!)
2. **Remove anonymous users**: Yes
3. **Disallow root login remotely**: Yes
4. **Remove test database**: Yes
5. **Reload privilege tables**: Yes

### Option B: Download MySQL Installer

1. Visit: https://dev.mysql.com/downloads/mysql/
2. Download MySQL Community Server for macOS
3. Follow installation wizard
4. Note down your root password

---

## Step 2: Verify MySQL Installation

```bash
# Check MySQL is running
brew services list | grep mysql

# Login to MySQL
mysql -u root -p
# Enter your root password when prompted

# You should see MySQL prompt:
mysql>

# Exit MySQL
exit
```

---

## Step 3: Create FlipFit Database

```bash
# Login to MySQL
mysql -u root -p

# Run the following SQL commands:
```

```sql
-- Create the database
CREATE DATABASE IF NOT EXISTS flipfit_db;
	
-- Verify database was created
SHOW DATABASES;

-- Exit
exit
```

---

## Step 4: Execute Database Schema

Navigate to your project directory and run:

```bash
cd /Users/deepanshu.gupta3/Desktop/JEDI-Project/JEDI-GROUP-PI-DEVELOPMENT-FLIPKART/JEDIPIDevelopmentJAVAPOS

# Execute the schema file
mysql -u root -p flipfit_db < database/schema.sql
```

### Verify Tables Were Created

```bash
mysql -u root -p flipfit_db -e "SHOW TABLES;"
```

You should see:
- bookings
- gyms
- id_counters
- slots
- users

---

## Step 5: Download MySQL Connector/J (JDBC Driver)

### Download the Driver

1. Visit: https://dev.mysql.com/downloads/connector/j/
2. Select "Platform Independent"
3. Download the `.tar.gz` or `.zip` file
4. Extract the archive
5. Locate the JAR file: `mysql-connector-j-X.X.X.jar`

### Add to Eclipse Project

1. In Eclipse, right-click on your project
2. Select **Build Path** → **Configure Build Path**
3. Go to **Libraries** tab
4. Click **Add External JARs**
5. Navigate to and select the `mysql-connector-j-X.X.X.jar` file
6. Click **Apply and Close**

---

## Step 6: Configure Database Connection

Update the database password in `database/database.properties`:

```properties
# Open this file and update your MySQL password
db.password=YOUR_MYSQL_ROOT_PASSWORD
```

Replace `YOUR_MYSQL_ROOT_PASSWORD` with the password you set during MySQL installation.

---

## Step 7: Test Database Connection

Run the connection test:

1. In Eclipse, navigate to:
   ```
   src/com/flipfit/database/DatabaseConnection.java
   ```

2. Right-click on the file → **Run As** → **Java Application**

3. You should see output like:
   ```
   ========================================
     TESTING DATABASE CONNECTION
   ========================================
   
   ✓ MySQL JDBC Driver loaded successfully
   ✓ Database connection established
   ✓ Database connection test PASSED
   
   ========================================
     DATABASE CONNECTION INFO
   ========================================
   Database: flipfit_db
   URL: jdbc:mysql://localhost:3306/flipfit_db
   User: root@localhost
   Driver: MySQL Connector/J
   ```

---

## Step 8: What's Next?

You've completed the MySQL setup! Here's what has been created:

### ✅ Files Created

1. **Database Schema**: `database/schema.sql`
   - All tables with proper relationships
   - Indexes for performance
   - ID counter tables

2. **Configuration**:
   - `database/database.properties` - Connection settings
   - `src/com/flipfit/database/DatabaseConfig.java` - Config manager
   - `src/com/flipfit/database/DatabaseConnection.java` - Connection manager

3. **Data Access Layer**:
   - `src/com/flipfit/dao/UserDAO.java` - User operations
   - `src/com/flipfit/dao/GymDAO.java` - Gym operations
   - `src/com/flipfit/dao/SlotDAO.java` - Slot operations
   - `src/com/flipfit/dao/BookingDAO.java` - Booking operations

### 📝 Next Steps to Complete Integration

To use MySQL in your application, I need to update your service layer classes to use the DAOs instead of the file-based storage. This involves:

1. Updating `GymUserServiceImpl.java`
2. Updating `GymOwnerServiceImpl.java`
3. Updating `GymAdminServiceImpl.java`
4. Updating `SlotServiceImpl.java`
5. Updating `GymCustomerServiceImpl.java`

**Would you like me to proceed with updating the service layer to use MySQL?**

---

## 🔧 Troubleshooting

### MySQL Won't Start
```bash
# Check MySQL status
brew services list

# Restart MySQL
brew services restart mysql
```

### Can't Connect to MySQL
```bash
# Reset MySQL root password
mysql.server stop
mysqld_safe --skip-grant-tables &
mysql -u root
```

```sql
FLUSH PRIVILEGES;
ALTER USER 'root'@'localhost' IDENTIFIED BY 'newpassword';
FLUSH PRIVILEGES;
exit
```

```bash
# Restart MySQL normally
brew services restart mysql
```

### JDBC Driver Not Found
- Verify the JAR is in Eclipse build path
- Clean and rebuild project: **Project** → **Clean**

---

## 📚 Useful MySQL Commands

```sql
-- Show all databases
SHOW DATABASES;

-- Use flipfit database
USE flipfit_db;

-- Show all tables
SHOW TABLES;

-- Describe table structure
DESCRIBE users;
DESCRIBE gyms;

-- View table data
SELECT * FROM users;
SELECT * FROM gyms;

-- Count records
SELECT COUNT(*) FROM users;
```

---

## 🎯 Quick Reference

**MySQL Login**: `mysql -u root -p`
**Create Database**: Already done via schema.sql
**Database Name**: `flipfit_db`
**Connection URL**: `jdbc:mysql://localhost:3306/flipfit_db`
**Config File**: `database/database.properties`
