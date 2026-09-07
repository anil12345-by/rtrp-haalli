# Library Management System

A Maven Java Application built with Java 17 and Dockerized for container deployment.

## Features
- Fixed pom.xml configured for Java 17 compilation and packaging.
- Executable JAR packaging with Main-Class specified in Manifest (com.library.LibraryManagement).
- Built-in HTTP server listening on port 8080 (http://localhost:8080).
- Dockerized setup with Alpine-based Java 17 runtime image (eclipse-temurin:17-jdk-alpine).

## Project Structure
`
library-management/
├── pom.xml
├── Dockerfile
├── .dockerignore
├── .gitignore
├── README.md
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── library/
│                   ├── Book.java
│                   └── LibraryManagement.java
└── target/
    └── library-management.jar
`

## Maven Commands
`ash
# Clean and compile project
mvn clean compile

# Clean and package into executable JAR
mvn clean package

# Run generated JAR directly
java -jar target/library-management.jar
`

## Docker Commands
`ash
# Build Docker image
docker build -t library-management:latest .

# Run container in detached mode with port 8080 exposed
docker run -d -p 8080:8080 --name library-app library-management:latest

# Access in browser
http://localhost:8080
`
