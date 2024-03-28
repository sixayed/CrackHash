FROM openjdk:17-alpine
COPY build/libs/CrackHash-0.0.1-SNAPSHOT.jar CrackHash-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/CrackHash-0.0.1.jar"]