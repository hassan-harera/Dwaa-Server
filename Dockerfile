FROM openjdk:17-oracle
COPY build/libs/Hayat-Server-1.0.0-SNAPSHOT.jar /
RUN ['java', 'jar', 'Hayat-Server-1.0.0-SNAPSHOT.jar']