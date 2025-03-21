FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app.jar"]