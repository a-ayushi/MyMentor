  # Build stage
  FROM maven:3.9.4-eclipse-temurin-17 AS build
  COPY . .
  RUN mvn clean install
  
  # Package stage
  FROM eclipse-temurin:17-jdk
  COPY --from=build /target/*.jar app.jar
  ENV PORT=8080
  EXPOSE 8080
  ENTRYPOINT ["java","-jar","app.jar"]