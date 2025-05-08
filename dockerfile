# 1) Build stage
FROM maven:3.9.4-eclipse-temurin-17-slim AS build

WORKDIR /build

# 1a) Copy only pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 1b) Copy source and build (skip tests)
COPY src ./src
RUN mvn clean package -DskipTests -B

# 2) Runtime stage
FROM eclipse-temurin:17-jdk-slim

WORKDIR /app

# Copy the fat JAR from the build stage
COPY --from=build /build/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
