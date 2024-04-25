FROM gradle:8.7.0-jdk17
WORKDIR /app
COPY build/libs/*.jar /app/manager.jar
ENTRYPOINT ["java","-jar","/app/manager.jar"]