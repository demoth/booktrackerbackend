FROM openjdk:17-alpine
RUN addgroup -S bktuser && adduser -S bktuser -G bktuser
USER bktuser:bktuser
COPY app/build/libs/*.jar app.jar
ENV JWT_SECRET=changeme
ENTRYPOINT ["java", "-jar", "/app.jar"]