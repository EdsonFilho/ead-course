# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY target/course-0.0.1-SNAPSHOT.jar .

EXPOSE 8082

CMD ["java", "-jar", "course-0.0.1-SNAPSHOT.jar"]

