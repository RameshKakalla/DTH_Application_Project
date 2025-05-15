# Step 1: Use a lightweight Java image
FROM openjdk:17-jdk-slim

# Step 2: Set a working directory inside the container
WORKDIR /app

# Step 3: Copy the jar file into container
# NOTE: Make sure your final jar is inside 'target' folder
COPY target/*.jar app.jar

# Step 4: Expose the port (same as in your SpringBoot app)
EXPOSE 8080

# Step 5: Command to run when container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
