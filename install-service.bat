@echo off
echo ========================================
echo    ติดตั้ง Triage API Service
echo ========================================
echo.

REM สร้าง shortcut บน Desktop
set DESKTOP=%USERPROFILE%\Desktop
set SHORTCUT_NAME=Triage API Service
set TARGET_PATH=%~dp0run-service.bat

echo กำลังสร้าง shortcut บน Desktop...

REM สร้างไฟล์ VBS เพื่อสร้าง shortcut
echo Set oWS = WScript.CreateObject("WScript.Shell") > CreateShortcut.vbs
echo sLinkFile = "%DESKTOP%\%SHORTCUT_NAME%.lnk" >> CreateShortcut.vbs
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> CreateShortcut.vbs
echo oLink.TargetPath = "%TARGET_PATH%" >> CreateShortcut.vbs
echo oLink.WorkingDirectory = "%~dp0" >> CreateShortcut.vbs
echo oLink.Description = "Triage API Service - รันหลังบ้านตลอดเวลา" >> CreateShortcut.vbs
echo oLink.Save >> CreateShortcut.vbs

REM รัน VBS script
cscript CreateShortcut.vbs >nul

REM ลบไฟล์ VBS ชั่วคราว
del CreateShortcut.vbs

echo.
echo ✅ สร้าง shortcut สำเร็จ!
echo.
echo วิธีใช้งาน:
echo 1. ดับเบิลคลิกที่ "Triage API Service" บน Desktop
echo 2. API จะรันหลังบ้านตลอดเวลา
echo 3. หากต้องการหยุด กด Ctrl+C ในหน้าต่าง Command
echo.
echo API จะทำงานที่: http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo.
pause
