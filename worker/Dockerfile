FROM gradle:8.7.0-jdk17
WORKDIR /app
COPY build/libs/*.jar /app/worker.jar
ENTRYPOINT ["java","-jar","/app/worker.jar"]