FROM openjdk:17-jdk-alpine

# Create a directory
WORKDIR /app

# Copy all the files from the current directory to the image
COPY . .

# build the project avoiding tests
RUN ./gradlew clean build -x test

# Expose port 8080
EXPOSE 3030

# Run the jar file
CMD ["java", "-jar", "./build/libs/core-0.0.1-SNAPSHOT.jar"]