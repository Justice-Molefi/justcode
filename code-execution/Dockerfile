# Stage 1: Build the Spring Boot JAR
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests
# Stage 2: Run the app
FROM openjdk:17-slim
RUN useradd -m appuser
USER appuser
WORKDIR /app
COPY --from=builder /build/code-execution/target/code-execution-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
