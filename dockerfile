# 1) Build stage
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /build

# Pre-fetch dependencies (won’t re-download on source changes)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy your code and build (skip tests if they’re still red)
COPY src ./src
RUN mvn clean package -DskipTests -B

# 2) Runtime stage
FROM eclipse-temurin:17-jdk-slim
WORKDIR /app

# Grab the fat-jar from build stage
COPY --from=build /build/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
