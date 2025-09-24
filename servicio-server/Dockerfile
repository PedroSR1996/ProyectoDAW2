FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/servicio-server-0.0.1-SNAPSHOT.jar servicio-server.jar
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "servicio-server.jar"]