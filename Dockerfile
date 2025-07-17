# Use OpenJDK base image
FROM openjdk:21-jdk-slim

# Add a volume pointing to /tmp
VOLUME /tmp

# Set the working directory
WORKDIR /app

# Copy built jar
COPY target/bankapp.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
