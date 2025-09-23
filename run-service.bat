@echo off
title Spring Boot Triage API Service
echo ========================================
echo    Spring Boot Triage API Service
echo ========================================
echo.

:start
echo [%date% %time%] กำลังเริ่มต้น Spring Boot API...
echo.

REM ตรวจสอบ Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java ไม่ได้ติดตั้งหรือไม่อยู่ใน PATH
    echo กรุณาติดตั้ง Java 17+
    pause
    exit /b 1
)

REM ตรวจสอบ Maven
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven ไม่ได้ติดตั้งหรือไม่อยู่ใน PATH
    echo กรุณาติดตั้ง Maven 3.6+
    pause
    exit /b 1
)

echo [%date% %time%] เริ่มต้น API Server...
echo Database: 172.28.130.166:1433
echo API URL: http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo.
echo หากต้องการหยุดการทำงาน กด Ctrl+C
echo.

REM รัน Spring Boot
mvn spring-boot:run

REM ถ้า Spring Boot หยุดทำงาน จะรีสตาร์ทอัตโนมัติ
echo.
echo [%date% %time%] Spring Boot หยุดทำงาน กำลังรีสตาร์ท...
echo รอ 5 วินาที...
timeout /t 5 /nobreak >nul
goto start
