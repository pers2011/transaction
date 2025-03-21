FROM openjdk:21-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8888
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]