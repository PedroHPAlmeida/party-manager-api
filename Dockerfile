FROM maven:3.9.8-eclipse-temurin-21 AS build-stage
LABEL stage=builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

FROM openjdk:21-slim AS runtime-stage
WORKDIR /app

ENV PORT=8080

COPY --from=build-stage /app/target/*.jar app.jar

EXPOSE $PORT

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]