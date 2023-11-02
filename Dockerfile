# Use a base image with Java installed
FROM openjdk:11-jre-slim

EXPOSE 8089

# Set the working directory in the container
WORKDIR /app

# Install curl in the container
RUN apt-get update && apt-get install -y curl

# Download the JAR file from the Nexus repository and name it achat-1.0.jar
RUN curl -o achat-1.0.jar -L http://192.168.33.130:8081/repository/maven-releases/tn/esprit/rh/achat/1.0/achat-1.0.jar

# Command to run the Java application when the container starts
ENTRYPOINT ["java", "-jar", "achat-1.0.jar"]
