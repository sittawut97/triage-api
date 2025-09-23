# 🚀 วิธีการรัน Triage API แบบหลังบ้านตลอดเวลา

## 📋 **ขั้นตอนการติดตั้ง (ทำครั้งเดียว)**

### 1. ติดตั้ง Service
```
1. ดับเบิลคลิกที่ไฟล์ "install-service.bat"
2. รอจนกว่าจะขึ้นข้อความ "สร้าง shortcut สำเร็จ!"
3. จะมี shortcut "Triage API Service" ปรากฏบน Desktop
```

## 🔄 **วิธีการใช้งาน**

### เริ่มต้น Service
```
1. ดับเบิลคลิกที่ "Triage API Service" บน Desktop
2. หน้าต่าง Command Prompt จะเปิดขึ้น
3. รอจนกว่าจะเห็นข้อความ "Started TriageApiApplication"
4. API พร้อมใช้งานที่ http://localhost:8080
```

### หยุด Service
```
1. ไปที่หน้าต่าง Command Prompt ที่เปิดอยู่
2. กด Ctrl+C
3. กด Y เพื่อยืนยัน
```

## 🌐 **URL สำคัญ**

| Service | URL |
|---------|-----|
| API Base | http://localhost:8080 |
| Health Check | http://localhost:8080/api/health |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| Counters | http://localhost:8080/api/counters |

## 🔧 **คุณสมบัติพิเศษ**

### Auto-Restart
- หาก API หยุดทำงานเอง จะรีสตาร์ทอัตโนมัติ
- ไม่ต้องกังวลเรื่อง crash หรือ error

### Logging
- แสดงเวลาที่เริ่มต้นและรีสตาร์ท
- แสดงสถานะการทำงาน

### Easy Management
- เริ่ม/หยุดง่ายๆ ผ่าน shortcut
- ไม่ต้องเข้า Command Line

## ⚠️ **ข้อควรระวัง**

1. **อย่าปิดหน้าต่าง Command Prompt** หาก API กำลังทำงาน
2. **ตรวจสอบ Port 8080** ว่าไม่มีโปรแกรมอื่นใช้อยู่
3. **Database Connection** ต้องเชื่อมต่อ 172.28.130.166:1433 ได้

## 🆘 **แก้ไขปัญหา**

### API ไม่เริ่มต้น
```
1. ตรวจสอบ Java และ Maven ติดตั้งแล้ว
2. ตรวจสอบ Database เชื่อมต่อได้
3. ตรวจสอบ Port 8080 ว่าง
```

### API หยุดทำงานบ่อย
```
1. ตรวจสอบ Database connection
2. ตรวจสอบ Memory และ CPU
3. ดู log ใน Command Prompt
```

### ไม่สามารถเข้าถึง API
```
1. ตรวจสอบ Windows Firewall
2. ตรวจสอบ Antivirus
3. ลองเข้า http://localhost:8080/api/health
```

## 📞 **ติดต่อสอบถาม**

หากมีปัญหาการใช้งาน กรุณาติดต่อทีม IT
