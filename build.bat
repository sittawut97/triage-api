@echo off
echo กำลัง Build Triage API...
echo.

REM ตรวจสอบว่ามี Maven หรือไม่
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven ไม่ได้ติดตั้งหรือไม่อยู่ใน PATH
    echo กรุณาติดตั้ง Maven 3.6+
    pause
    exit /b 1
)

echo Maven พร้อมใช้งาน
echo.

REM Clean และ Build project
echo กำลัง Clean project...
mvn clean

echo.
echo กำลัง Build project...
mvn package -DskipTests

if %errorlevel% equ 0 (
    echo.
    echo ✅ Build สำเร็จ!
    echo JAR file: target\triage-api-0.0.1-SNAPSHOT.jar
    echo.
    echo วิธีการรัน:
    echo   java -jar target\triage-api-0.0.1-SNAPSHOT.jar
    echo หรือ
    echo   run.bat
) else (
    echo.
    echo ❌ Build ล้มเหลว!
    echo กรุณาตรวจสอบ error messages ด้านบน
)

echo.
pause
