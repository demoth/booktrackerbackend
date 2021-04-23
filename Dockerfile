FROM openjdk:17-alpine
COPY build/libs/*.jar app.jar
ENV JWT_SECRET=changeme
ENTRYPOINT ["java", "-jar", "/app.jar"]