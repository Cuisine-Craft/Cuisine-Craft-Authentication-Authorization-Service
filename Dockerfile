# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY ./build/libs/authservice-0.0.1-SNAPSHOT.jar ./

# Run the application
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar authservice-0.0.1-SNAPSHOT.jar"]
