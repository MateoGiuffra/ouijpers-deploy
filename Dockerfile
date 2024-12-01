FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install -y openjdk-11-jdk
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM openjdk:11-jre-slim
EXPOSE 8080
COPY --from=build /libs/epersgeist-1.0-SNAPSHOT.jar app.jar


ENTRYPOINT ["java","-jar","app.jar"]