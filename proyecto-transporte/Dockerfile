FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/proyecto-transporte-0.0.1-SNAPSHOT.jar servicio-transporte.jar
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "servicio-transporte.jar"]