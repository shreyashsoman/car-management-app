# Stage 1: Build
# Use an official OpenJDK 21 image for the build process
FROM openjdk:21-jdk AS build

# Set the working directory inside the container
WORKDIR /app

# Copy all the files from your local project to the container's working directory
COPY . .

# Make the Maven wrapper script executable
RUN chmod +x ./mvnw

# Run the Maven wrapper to clean, compile, and package the Spring Boot application
# Skip tests to speed up the build
RUN ./mvnw clean install -DskipTests

# Stage 2: Run
# Use an official OpenJDK 21 image to run the application
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the application runs on (default Spring Boot port)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
