# Use Maven and OpenJDK 17 image as base image
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the project and package the application
RUN mvn clean package -DskipTests

# Use OpenJDK 17 to run the app
FROM openjdk:17-jdk

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/URLshortener-0.0.1-SNAPSHOT.jar /URLshortener.jar

# Expose port 8080 for the app
EXPOSE 8080

# Run the app using the specified JAR
ENTRYPOINT ["java", "-jar", "/URLshortener.jar"]

