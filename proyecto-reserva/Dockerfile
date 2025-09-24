FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/proyecto-reserva-0.0.1-SNAPSHOT.jar servicio-reserva.jar
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "servicio-reserva.jar"]



