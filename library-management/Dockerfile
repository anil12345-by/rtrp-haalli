# Use official Java 17 runtime base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the built executable JAR file into container
COPY target/library-management.jar app.jar

# Expose port 8080 for application access
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
