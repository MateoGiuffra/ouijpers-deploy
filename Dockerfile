# Etapa de construcción
FROM ubuntu:latest AS build
RUN apt-get update && apt-get install -y openjdk-11-jdk
WORKDIR /app
COPY . .

# Otorgar permisos de ejecución a gradlew
RUN chmod +x ./gradlew

# Compilar el proyecto
RUN ./gradlew bootJar --no-daemon

# Etapa de ejecución
FROM openjdk:11-jre-slim
EXPOSE 8080
WORKDIR /app

# Copiar el archivo generado desde la etapa de construcción
COPY --from=build /app/build/libs/epersgeist-1.0-SNAPSHOT.jar app.jar

# Definir el punto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
