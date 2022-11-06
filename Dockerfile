FROM openjdk:17-oracle
COPY build/libs/Hayat-Server-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]