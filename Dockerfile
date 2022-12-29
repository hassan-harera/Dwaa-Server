FROM openjdk:17.0.2-slim

COPY ./target/*.jar /project/app.jar
COPY ./.env /project/.env
ENTRYPOINT ["java","-Djava.security.egd=file:/project/.env", "-jar","/project/app.jar"]