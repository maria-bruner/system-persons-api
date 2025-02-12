FROM openjdk:17-jdk-slim

COPY person-api.jar /app/backend.jar

WORKDIR /app

ENTRYPOINT ["java", "-jar", "backend.jar"]

EXPOSE 8080