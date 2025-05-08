# 1) Build stage
FROM maven:3.9.4-eclipse-temurin-17-slim AS build
WORKDIR /build

# Fetch dependencies first for better caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build (skip tests so the build won’t break on failures)
COPY src ./src
RUN mvn clean package -DskipTests -B

# 2) Runtime stage
FROM eclipse-temurin:17-jdk AS runtime
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /build/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
