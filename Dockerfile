FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app

COPY src ./src
COPY pom.xml .

ENV APP_NAME "Shopino"

RUN mvn clean package -DskipTests \
    && mv target/*.jar app.jar


FROM openjdk:17-jdk-slim

LABEL maintainer="abdessamad.abidar@hotmail.com"

WORKDIR /app

COPY --from=builder /app/app.jar app.jar

RUN apt-get update && apt-get install -y curl

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]