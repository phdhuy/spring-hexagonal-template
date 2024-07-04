FROM openjdk:19-alpine
EXPOSE 8080

WORKDIR /usr/app

COPY target/spring-hexagonal-template-0.0.1-SNAPSHOT.jar /usr/app

CMD ["java", "-jar", "spring-hexagonal-template-0.0.1-SNAPSHOT.jar"]