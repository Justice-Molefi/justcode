FROM openjdk:17-slim

# Create a user and switch to non-root
RUN useradd -m appuser
USER appuser

# Create app directory and set working dir
WORKDIR /app

# Copy the JAR from Maven target folder
COPY --chown=appuser:appuser target/request-handler-0.0.1-SNAPSHOT.jar app.jar

# Expose the app port (Spring default is 8080; change if needed)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
