FROM amazoncorretto:21-al2-generic-jdk


COPY build/libs/epersgeist-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]