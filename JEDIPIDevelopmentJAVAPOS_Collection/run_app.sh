#!/bin/bash
# FlipFit Application Runner Script

echo "========================================"
echo "  FlipFit Application Launcher"
echo "========================================"

# Set the project directory
PROJECT_DIR="/Users/deepanshu.gupta3/Desktop/JEDI-Project/JEDI-GROUP-PI-DEVELOPMENT-FLIPKART/JEDIPIDevelopmentJAVAPOS"
cd "$PROJECT_DIR"

# Compile the Java files if needed
echo "Compiling Java files..."
javac -d bin -cp "lib/mysql-connector-j-8.3.0.jar" $(find src -name "*.java")

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo ""
    echo "Starting FlipFit Application..."
    echo "========================================"
    echo ""
    
    # Run the application
    java -cp "bin:lib/mysql-connector-j-8.3.0.jar" com.flipfit.client.FlipfitApplication
else
    echo "✗ Compilation failed!"
    exit 1
fi
