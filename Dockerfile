FROM openjdk:17-jdk-slim

LABEL maintainer="abdessamad.abidar@hotmail.com"

WORKDIR /app

# Only copy the pre-built JAR
COPY target/*.jar app.jar

RUN apt-get update && apt-get install -y curl

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
