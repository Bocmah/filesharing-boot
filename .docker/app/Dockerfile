FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/filesharing-1.0-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]