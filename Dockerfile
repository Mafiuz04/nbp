FROM amazoncorretto:21-alpine-jdk
COPY target/nbp-0.0.1-SNAPSHOT.jar nbp-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/nbp-0.0.1-SNAPSHOT.jar"]