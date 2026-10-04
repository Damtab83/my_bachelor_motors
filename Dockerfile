FROM maven:4.0.0-rc-7-eclipse-temurin-21-alpine as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

#RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine-3.24

WORKDIR /app

COPY --from=build /app/target/*.war app.war

ENV PORT 8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]