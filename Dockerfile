# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/SaleCalc-back-0.0.1-SNAPSHOT.jar /app/salecalc-back.jar

# Copy the application properties file into the container
COPY src/main/resources/application-dev.properties /app/application.properties

# Expose the port your app runs on
EXPOSE 9090

# Run the jar file
CMD ["java", "-jar", "salecalc-back.jar","--spring.config.location=classpath:/application.properties"]
