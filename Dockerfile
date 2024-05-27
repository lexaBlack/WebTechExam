# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Build the application using Maven
RUN ./mvnw clean install

# Run the application
CMD ["java", "-jar", "target/ecommerce-cars-1.0.0.jar"]
