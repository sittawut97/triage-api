#!/bin/bash

echo "🚀 Building Triage API for Render..."

# ตั้งค่า Java version
export JAVA_HOME=/opt/java/openjdk
export PATH=$JAVA_HOME/bin:$PATH

# แสดง Java version
echo "Java version:"
java -version

# แสดง Maven version  
echo "Maven version:"
./mvnw -version

# Clean และ build project
echo "📦 Building application..."
./mvnw clean package -DskipTests -Dspring.profiles.active=render

# ตรวจสอบว่า JAR file ถูกสร้างแล้ว
if [ -f "target/triage-api-1.0.0.jar" ]; then
    echo "✅ Build successful! JAR file created."
    ls -la target/triage-api-1.0.0.jar
else
    echo "❌ Build failed! JAR file not found."
    exit 1
fi

echo "🎉 Build completed successfully!"
