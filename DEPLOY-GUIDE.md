# 🚀 คู่มือ Deploy Triage API ขึ้น Cloud

## 📋 **ตัวเลือก Cloud Platform**

### 🆓 **Railway (แนะนำ - ฟรี 500 ชั่วโมง/เดือน)**

#### ขั้นตอนการ Deploy:

1. **สร้าง GitHub Repository**
   ```bash
   # ใน folder springboot-api
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin https://github.com/yourusername/triage-api.git
   git push -u origin main
   ```

2. **สมัคร Railway**
   - ไปที่ https://railway.app
   - Sign up ด้วย GitHub account
   - Connect GitHub repository

3. **Deploy**
   - เลือก repository "triage-api"
   - Railway จะ auto-deploy
   - รอ 5-10 นาที

4. **ตั้งค่า Environment Variables**
   ```
   DATABASE_URL=jdbc:sqlserver://172.28.130.166:1433;databaseName=TriageNew;encrypt=false;trustServerCertificate=true
   DB_USERNAME=sa
   DB_PASSWORD=your_actual_password
   SPRING_PROFILES_ACTIVE=railway
   ```

5. **ได้ URL**
   - Railway จะให้ URL เช่น: https://triage-api-production.up.railway.app
   - API จะทำงานที่ URL นี้

---

### 🆓 **Render (ทางเลือก)**

#### ขั้นตอนการ Deploy:

1. **สมัคร Render**
   - ไปที่ https://render.com
   - Sign up ด้วย GitHub

2. **สร้าง Web Service**
   - เลือก "Web Service"
   - Connect GitHub repository
   - ตั้งค่า:
     ```
     Build Command: ./mvnw clean package -DskipTests
     Start Command: java -Dspring.profiles.active=railway -jar target/triage-api-1.0.0.jar
     ```

3. **ตั้งค่า Environment Variables**
   - เหมือน Railway

---

### 💰 **AWS (สำหรับ Production)**

#### ขั้นตอนการ Deploy:

1. **สร้าง AWS Account**
   - ไปที่ https://aws.amazon.com
   - สมัคร account (ต้องใช้บัตรเครดิต)

2. **ใช้ Elastic Beanstalk**
   - Upload JAR file
   - ตั้งค่า environment variables
   - Deploy

3. **ตั้งค่า RDS (Database)**
   - สร้าง SQL Server instance
   - Update connection string

---

## 🔧 **การตั้งค่าเพิ่มเติม**

### **Custom Domain (ถ้าต้องการ)**
```
api.yourdomain.com -> Cloud URL
```

### **HTTPS Certificate**
- Railway/Render: ให้ฟรีอัตโนมัติ
- AWS: ใช้ Certificate Manager

### **Monitoring**
- Railway: Built-in monitoring
- AWS: CloudWatch
- External: UptimeRobot (ฟรี)

---

## 💰 **ค่าใช้จ่าย (ประมาณการ)**

| Platform | ฟรี | Paid |
|----------|-----|------|
| Railway | 500 ชม/เดือน | $5/เดือน |
| Render | Sleep หลัง 15 นาที | $7/เดือน |
| AWS | 12 เดือนแรก | $10-50/เดือน |
| Google Cloud | $300 credit | $10-30/เดือน |

---

## 🚨 **ข้อควรระวัง**

### **Database Connection**
- ต้องเปิด firewall ให้ cloud access database
- หรือย้าย database ขึ้น cloud ด้วย

### **Environment Variables**
- **ห้าม** commit password ใน code
- ใช้ environment variables เสมอ

### **Free Tier Limitations**
- Railway: 500 ชั่วโมง/เดือน
- Render: Sleep หลัง 15 นาที idle
- AWS: จำกัดเวลา 12 เดือน

---

## 📞 **ขั้นตอนถัดไป**

1. เลือก platform ที่ต้องการ
2. สร้าง GitHub repository
3. Follow ขั้นตอนตาม platform
4. Test API ที่ cloud URL
5. Update Python client ให้ใช้ cloud URL

---

## 🆘 **แก้ไขปัญหา**

### **Deploy Failed**
- ตรวจสอบ Dockerfile syntax
- ตรวจสอบ Maven build
- ดู deployment logs

### **Database Connection Error**
- ตรวจสอบ firewall settings
- ตรวจสอบ environment variables
- Test connection จาก local

### **API ไม่ตอบสนอง**
- ตรวจสอบ health endpoint
- ดู application logs
- ตรวจสอบ port configuration
