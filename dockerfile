# 1) Build stage
FROM maven:3.9.4-openjdk-17-slim AS build
WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# 2) Runtime stage
FROM openjdk:17-jdk-slim AS runtime
WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
