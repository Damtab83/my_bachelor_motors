FROM maven:4.0.0-rc-7-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine-3.24

WORKDIR /app

COPY --from=build /app/target/*.war app.war

#ENV PORT 8080

#EXPOSE 8080
EXPOSE 10000

#ENTRYPOINT ["java", "-jar", "app.war", "--debug"]
#ENTRYPOINT ["sh", "-c", "java -jar -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} app.war"]
ENTRYPOINT ["sh", "-c", "java -XX:MaxRAMPercentage=65 -XX:+UseSerialGC -Xss512k -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar app.war"]