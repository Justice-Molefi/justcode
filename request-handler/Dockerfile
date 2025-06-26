# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY ../ ../ ./
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:17-slim
RUN useradd -m appuser
USER appuser
WORKDIR /app
COPY --from=builder /build/request-handler/target/request-handler-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
