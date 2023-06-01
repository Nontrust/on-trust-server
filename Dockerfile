# Base image
# Gradle setting
# Todo: connect mysql (fail to start spring boot)

FROM gradle:7.6.0-jdk17-alpine AS builder

# Set the working directory inside the container
RUN mkdir /app
WORKDIR /app

# Copy Gradle Running Script
COPY gradle gradle
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .

# Copy the source code
COPY src src

# Install xargs
RUN apk update && apk add findutils

# If gradle /bin/sh^M: bad interpreter
# vi -b /gradlew -> use command :%s/^M//g

# Build the project without daemon process
RUN ./gradlew clean build --no-daemon

# Final image openjdk->eclipsetemurin
# https://adoptium.net/temurin/releases/
FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port that the application will listen on
EXPOSE 8000

# Command to run the application
# set default gc
CMD ["java", "-Xms512m", "-Xmx1024m", "-jar", "app.jar"]
