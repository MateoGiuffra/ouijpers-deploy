# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Crea un directorio de trabajo
WORKDIR /app

# Copia gradlew y gradle-wrapper.jar al contenedor
COPY gradlew .
COPY gradle/ gradle/

# Asegúrate de que gradlew sea ejecutable
RUN chmod +x gradlew

# Copia el resto de los archivos de la aplicación al contenedor
COPY . .

# Compila la aplicación
RUN ./gradlew bootJar --no-daemon

# Expone el puerto
EXPOSE 8080

# Ejecuta la aplicación
CMD ["java", "-jar", "build/libs/demo-0.0.1-SNAPSHOT.jar"]
