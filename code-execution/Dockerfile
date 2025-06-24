FROM openjdk:17-slim

# Create a non-root user
RUN useradd -m appuser
USER appuser

# Set working directory
WORKDIR /app

# Copy the compiled JAR (adjust name if needed)
COPY --chown=appuser:appuser target/*.jar app.jar

# No port exposure needed since this is internal
# EXPOSE is omitted on purpose

# Run the Spring Boot worker
ENTRYPOINT ["java", "-jar", "app.jar"]
