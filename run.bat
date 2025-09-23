@echo off
echo กำลังรัน Triage API Server...
echo.

REM ตรวจสอบว่ามี Java หรือไม่
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java ไม่ได้ติดตั้งหรือไม่อยู่ใน PATH
    echo กรุณาติดตั้ง Java 17 หรือสูงกว่า
    pause
    exit /b 1
)

REM ตรวจสอบว่ามี Maven หรือไม่
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven ไม่ได้ติดตั้งหรือไม่อยู่ใน PATH
    echo กรุณาติดตั้ง Maven 3.6+
    pause
    exit /b 1
)

echo Java และ Maven พร้อมใช้งาน
echo.

REM รัน Spring Boot application
echo กำลังรัน Spring Boot API (SQL Server Database)...
echo Database: 172.28.130.166:1433
echo API จะทำงานที่ http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo.
echo กด Ctrl+C เพื่อหยุดการทำงาน
echo.

mvn spring-boot:run

pause
