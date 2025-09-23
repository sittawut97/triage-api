# 🚀 คู่มือ Deploy บน Render.com

## 📋 **ขั้นตอนการ Deploy**

### **Step 1: เตรียม GitHub Repository**

1. **สร้าง GitHub Repository**
   ```bash
   # ใน folder springboot-api
   git init
   git add .
   git commit -m "Initial commit for Render deployment"
   
   # สร้าง repository ใหม่บน GitHub แล้ว push
   git remote add origin https://github.com/yourusername/triage-api.git
   git branch -M main
   git push -u origin main
   ```

### **Step 2: สมัคร Render Account**

1. ไปที่ https://render.com
2. คลิก **"Get Started for Free"**
3. Sign up ด้วย GitHub account
4. Authorize Render เข้าถึง GitHub repositories

### **Step 3: สร้าง Web Service**

1. **ใน Render Dashboard:**
   - คลิก **"New +"** 
   - เลือก **"Web Service"**

2. **Connect Repository:**
   - เลือก GitHub repository "triage-api"
   - คลิก **"Connect"**

3. **ตั้งค่า Service:**
   ```
   Name: triage-api
   Environment: Docker
   Region: Singapore (ใกล้ที่สุด)
   Branch: main
   Build Command: chmod +x build.sh && ./build.sh
   Start Command: java -Dspring.profiles.active=render -jar target/triage-api-1.0.0.jar
   ```

### **Step 4: ตั้งค่า Environment Variables**

ใน **Environment** tab เพิ่ม:

```
DATABASE_URL = jdbc:sqlserver://172.28.130.166:1433;databaseName=TriageNew;encrypt=false;trustServerCertificate=true
DB_USERNAME = sa
DB_PASSWORD = your_actual_password
SPRING_PROFILES_ACTIVE = render
PORT = 8080
```

### **Step 5: Deploy**

1. คลิก **"Create Web Service"**
2. Render จะเริ่ม build และ deploy
3. รอ 5-10 นาที (ครั้งแรกอาจนานหน่อย)
4. ดู logs ใน **"Logs"** tab

### **Step 6: ทดสอบ**

1. **ได้ URL:** `https://triage-api-xxxx.onrender.com`
2. **ทดสอบ endpoints:**
   ```
   Health Check: https://your-app.onrender.com/api/health
   Counters: https://your-app.onrender.com/api/counters
   Swagger: https://your-app.onrender.com/swagger-ui.html
   ```

---

## 🔧 **การตั้งค่าเพิ่มเติม**

### **Auto-Deploy**
- Render จะ auto-deploy เมื่อ push code ใหม่
- ไม่ต้องทำอะไรเพิ่ม

### **Custom Domain (ถ้าต้องการ)**
1. ใน **Settings** tab
2. เพิ่ม **Custom Domain**
3. ตั้งค่า DNS records

### **Health Checks**
- Render จะ ping `/api/health` ทุก 30 วินาที
- หาก fail จะรีสตาร์ทอัตโนมัติ

---

## 💰 **ค่าใช้จ่าย**

### **Free Tier:**
- **ข้อดี:** ฟรี 100%
- **ข้อเสีย:** Sleep หลัง 15 นาที idle
- **Wake up time:** 30-60 วินาที

### **Paid Plan ($7/เดือน):**
- **ข้อดี:** ไม่ sleep, เร็วกว่า
- **ข้อเสีย:** เสียเงิน

---

## 🚨 **ข้อควรระวัง**

### **Free Tier Limitations:**
1. **Sleep after 15 minutes idle**
   - API จะหยุดทำงานหากไม่มีคนใช้ 15 นาที
   - ครั้งแรกที่เรียกใช้หลัง sleep จะช้า 30-60 วินาที

2. **Monthly Usage Limit:**
   - 750 ชั่วโมง/เดือน (เพียงพอสำหรับใช้งานปกติ)

3. **Build Time:**
   - Build ครั้งแรกอาจใช้เวลา 10-15 นาที

### **Database Connection:**
- ต้องให้ database server เปิด firewall สำหรับ Render IPs
- หรือใช้ public database

---

## 🔄 **การอัปเดต Code**

```bash
# แก้ไข code
git add .
git commit -m "Update API"
git push origin main

# Render จะ auto-deploy ใน 2-5 นาที
```

---

## 📱 **Update Python Client**

แก้ไขไฟล์ `api_config.py`:

```python
# เปลี่ยนจาก localhost เป็น Render URL
API_BASE_URL = "https://your-app-name.onrender.com"

# เพิ่ม retry logic สำหรับ cold start
import time
import requests

def api_request_with_retry(url, max_retries=3):
    for i in range(max_retries):
        try:
            response = requests.get(url, timeout=60)  # เพิ่ม timeout
            return response
        except requests.exceptions.Timeout:
            if i < max_retries - 1:
                print(f"Timeout, retrying... ({i+1}/{max_retries})")
                time.sleep(5)
            else:
                raise
```

---

## 🆘 **แก้ไขปัญหา**

### **Build Failed:**
```bash
# ดู build logs ใน Render dashboard
# มักเป็นปัญหา Maven dependencies หรือ Java version
```

### **App Crashes:**
```bash
# ดู runtime logs
# ตรวจสอบ database connection
# ตรวจสอบ environment variables
```

### **Slow Response (Cold Start):**
```bash
# Normal สำหรับ free tier
# ใช้ paid plan หรือ keep-alive service
```

### **Database Connection Error:**
```bash
# ตรวจสอบ firewall settings
# ตรวจสอบ DATABASE_URL format
# ลอง connect จาก local ก่อน
```

---

## 📞 **ขั้นตอนถัดไป**

1. ✅ สร้าง GitHub repository
2. ✅ สมัคร Render account  
3. ✅ Deploy service
4. ✅ ทดสอบ API endpoints
5. ✅ Update Python client
6. ✅ Test end-to-end functionality

---

## 💡 **Tips & Tricks**

### **Keep App Awake (Free Tier):**
```bash
# ใช้ UptimeRobot ping ทุก 5 นาที
# หรือสร้าง cron job ping API
```

### **Faster Builds:**
```bash
# ใช้ Maven wrapper caching
# ลด dependencies ที่ไม่จำเป็น
```

### **Monitoring:**
```bash
# ใช้ Render built-in metrics
# เพิ่ม external monitoring (UptimeRobot)
```
