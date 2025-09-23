# Triage System Spring Boot API

Spring Boot REST API สำหรับระบบ Triage ที่รองรับการทำงานของ Python client applications

## คุณสมบัติ

- REST API endpoints สำหรับจัดการข้อมูล Triage
- การจัดการ Call Logs
- การดึงข้อมูล Counters
- API Documentation ด้วย Swagger/OpenAPI
- CORS support สำหรับ cross-origin requests
- Global exception handling
- Health check endpoint

## เทคโนโลยีที่ใช้

- **Spring Boot 3.2.0**
- **Spring Data JPA** - สำหรับ database operations
- **SQL Server** - ฐานข้อมูล
- **Swagger/OpenAPI 3** - API documentation
- **Maven** - dependency management

## การติดตั้งและรัน

### ข้อกำหนดเบื้องต้น

- Java 17 หรือสูงกว่า
- Maven 3.6+
- SQL Server database
- ฐานข้อมูล Triage ที่มีตาราง: `triage`, `calllog`, `cs`

### การตั้งค่าฐานข้อมูล

1. แก้ไขไฟล์ `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://your-server:1433;databaseName=Triage;encrypt=false;trustServerCertificate=true
    username: your-username
    password: your-password
```

2. หรือใช้ environment variables:

```bash
export DB_USERNAME=your-username
export DB_PASSWORD=your-password
```

### การรันแอปพลิเคชัน

```bash
# Clone และเข้าไปในโฟลเดอร์
cd springboot-api

# รันด้วย Maven
mvn spring-boot:run

# หรือ build แล้วรัน
mvn clean package
java -jar target/triage-api-0.0.1-SNAPSHOT.jar
```

แอปพลิเคชันจะรันที่ `http://localhost:8080`

## API Endpoints

### Triage Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/triage/columns` | ดึงรายชื่อ columns ของตาราง triage |
| GET | `/api/triage/values/{columnName}` | ดึงค่าใน column ที่ระบุ |
| DELETE | `/api/triage/record` | ลบ record ตามเงื่อนไข |
| GET | `/api/triage/check-nulls/{number}` | ตรวจสอบค่า null ใน record |
| PUT | `/api/triage/update` | อัปเดต column ใน record |
| POST | `/api/triage/insert` | เพิ่ม record ใหม่ |

### Call Log Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/calllog/today` | ดึง call logs ของวันนี้ |
| POST | `/api/calllog/insert` | เพิ่ม call log ใหม่ |
| PUT | `/api/calllog/update-get` | อัปเดต get field ใน call log |

### Counter Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/counters` | ดึงค่า counters จากตาราง cs |

### System Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | ตรวจสอบสถานะ API |

## API Documentation

เมื่อรันแอปพลิเคชันแล้ว สามารถเข้าดู API documentation ได้ที่:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`

## ตัวอย่างการใช้งาน

### 1. ดึงรายชื่อ columns

```bash
curl -X GET http://localhost:8080/api/triage/columns
```

### 2. เพิ่ม triage record

```bash
curl -X POST http://localhost:8080/api/triage/insert \
  -H "Content-Type: application/json" \
  -d '{"Triage1": "Station1", "Triage5": "Station1"}'
```

### 3. เพิ่ม call log

```bash
curl -X POST http://localhost:8080/api/calllog/insert \
  -H "Content-Type: application/json" \
  -d '{"req": "Station1", "get": "Triage1"}'
```

### 4. ตรวจสอบสถานะ

```bash
curl -X GET http://localhost:8080/api/health
```

## โครงสร้างโปรเจค

```
src/main/java/com/triage/
├── TriageApiApplication.java          # Main application class
├── config/
│   ├── SwaggerConfig.java            # Swagger configuration
│   └── WebConfig.java                # Web/CORS configuration
├── controller/
│   ├── CallLogController.java        # Call log endpoints
│   ├── CounterController.java        # Counter endpoints
│   ├── HealthController.java         # Health check endpoint
│   └── TriageController.java         # Triage endpoints
├── entity/
│   ├── CallLog.java                  # Call log entity
│   ├── Counter.java                  # Counter entity
│   └── Triage.java                   # Triage entity
├── exception/
│   └── GlobalExceptionHandler.java   # Global exception handling
├── repository/
│   ├── CallLogRepository.java        # Call log repository
│   ├── CounterRepository.java        # Counter repository
│   └── TriageRepository.java         # Triage repository
└── service/
    ├── CallLogService.java           # Call log business logic
    ├── CounterService.java           # Counter business logic
    └── TriageService.java            # Triage business logic
```

## การแก้ไขปัญหา

### ปัญหาการเชื่อมต่อฐานข้อมูล

1. ตรวจสอบ connection string ใน `application.yml`
2. ตรวจสอบว่า SQL Server รันอยู่และเปิด port 1433
3. ตรวจสอบ username/password
4. ตรวจสอบว่าฐานข้อมูล Triage มีอยู่จริง

### ปัญหา CORS

- API มี CORS configuration แล้ว รองรับ requests จากทุก origin
- หากยังมีปัญหา ให้ตรวจสอบ browser console

### ปัญหา Port

- หาก port 8080 ถูกใช้งานแล้ว สามารถเปลี่ยนใน `application.yml`:

```yaml
server:
  port: 8081
```

## การพัฒนาต่อ

1. **Authentication/Authorization** - เพิ่ม Spring Security
2. **Database Migration** - ใช้ Flyway หรือ Liquibase
3. **Caching** - เพิ่ม Redis caching
4. **Monitoring** - เพิ่ม Actuator endpoints
5. **Testing** - เพิ่ม unit และ integration tests

## การ Deploy

### Development
```bash
mvn spring-boot:run
```

### Production
```bash
mvn clean package
java -jar target/triage-api-0.0.1-SNAPSHOT.jar
```

### Docker (ถ้าต้องการ)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/triage-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```
