FROM maven:3.9.3-amazoncorretto-17 AS build

WORKDIR /usr/src/app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:19-alpine

WORKDIR /usr/app

COPY --from=build /usr/src/app/target/spring-hexagonal-template-0.0.1-SNAPSHOT.jar /usr/app

EXPOSE 8080

CMD ["java", "-jar", "spring-hexagonal-template-0.0.1-SNAPSHOT.jar"]