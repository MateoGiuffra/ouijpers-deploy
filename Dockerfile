# Etapa 1: Construcción de la aplicación
FROM eclipse-temurin:17-jdk-alpine as build

# Asegúrate de que gradlew sea ejecutable
RUN chmod +x gradlew

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos necesarios para el build
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src



# Construye la aplicación
RUN ./gradlew bootJar --no-daemon

# Etapa 2: Imagen de producción
FROM eclipse-temurin:17-jre-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado en la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Expone el puerto en el que la aplicación escuchará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
