# ใช้ OpenJDK 17 เป็น base image
FROM openjdk:17-jdk-slim

# ตั้งค่า working directory
WORKDIR /app

# คัดลอก Maven wrapper และ pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline

# คัดลอก source code
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# รัน application
CMD ["java", "-jar", "target/triage-api-1.0.0.jar"]
