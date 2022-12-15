FROM openjdk:17-jdk-alpine
MAINTAINER xevi8x
COPY target/projektZPOIF-0.0.1-SNAPSHOT.jar projektZPOIF-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Djava.net.preferIPv4Stack=true","/projektZPOIF-0.0.1-SNAPSHOT.jar"]