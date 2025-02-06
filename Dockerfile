# Use the Maven image to build the app
FROM maven:3.8.7-openjdk-23 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the project and package the application
RUN mvn clean package -DskipTests

# Use the official OpenJDK 23 image to run the app
FROM openjdk:23-jdk

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/URLshortener-0.0.1-SNAPSHOT.jar /URLshortener.jar

# Expose port 8080 for the app
EXPOSE 8080

# Run the app using the specified JAR
ENTRYPOINT ["java", "-jar", "/URLshortener.jar"]