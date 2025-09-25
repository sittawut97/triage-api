# ใช้ Maven image สำหรับ build stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# ตั้งค่า working directory
WORKDIR /app

# คัดลอก pom.xml และ download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# คัดลอก source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests -B

# Production stage - ใช้ Eclipse Temurin JRE
FROM eclipse-temurin:17-jre-alpine

# ตั้งค่า working directory
WORKDIR /app

# คัดลอก JAR file จาก build stage
COPY --from=build /app/target/triage-api-0.0.1-SNAPSHOT.jar app.jar

# สร้าง user ที่ไม่ใช่ root เพื่อความปลอดภัย (Alpine Linux)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose port 8080
EXPOSE 8080

# รัน application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
