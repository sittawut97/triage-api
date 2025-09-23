@echo off
echo ========================================
echo    Deploy Triage API to Render.com
echo ========================================
echo.

REM ตรวจสอบ Git
git --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Git ไม่ได้ติดตั้งหรือไม่อยู่ใน PATH
    echo กรุณาติดตั้ง Git จาก https://git-scm.com/
    pause
    exit /b 1
)

echo Git พร้อมใช้งาน
echo.

REM ตรวจสอบว่ามี remote repository หรือไม่
git remote -v >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  ยังไม่ได้ตั้งค่า Git repository
    echo.
    echo ขั้นตอนที่ต้องทำ:
    echo 1. สร้าง repository ใหม่บน GitHub
    echo 2. รันคำสั่งเหล่านี้:
    echo.
    echo    git init
    echo    git add .
    echo    git commit -m "Initial commit"
    echo    git remote add origin https://github.com/yourusername/triage-api.git
    echo    git branch -M main
    echo    git push -u origin main
    echo.
    echo 3. แล้วไปที่ https://render.com เพื่อ deploy
    echo.
    pause
    exit /b 0
)

echo 📦 กำลังเตรียม code สำหรับ deploy...

REM Add และ commit การเปลี่ยนแปลงทั้งหมด
git add .
git status

echo.
set /p commit_msg="Enter commit message (หรือกด Enter สำหรับ default): "
if "%commit_msg%"=="" set commit_msg=Update for Render deployment

git commit -m "%commit_msg%"

echo.
echo 🚀 กำลัง push ไปยัง GitHub...
git push origin main

if %errorlevel% equ 0 (
    echo.
    echo ✅ Push สำเร็จ!
    echo.
    echo ขั้นตอนถัดไป:
    echo 1. ไปที่ https://render.com
    echo 2. เลือก "New +" ^> "Web Service"
    echo 3. Connect GitHub repository
    echo 4. ใช้การตั้งค่าใน RENDER-DEPLOY.md
    echo.
    echo 📖 อ่านคู่มือเต็มใน RENDER-DEPLOY.md
    echo.
    
    REM เปิด browser ไปยัง Render
    set /p open_browser="เปิด Render.com ใน browser? (y/n): "
    if /i "%open_browser%"=="y" (
        start https://render.com/
    )
) else (
    echo.
    echo ❌ Push ล้มเหลว
    echo กรุณาตรวจสอบ GitHub credentials และ repository settings
)

echo.
pause
