FROM maven:3.9.8-eclipse-temurin-21 AS build-stage
LABEL stage=builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

FROM openjdk:21-slim AS runtime-stage
WORKDIR /app

ENV PORT=${PORT} \
    PROFILE=${PROFILE} \
    DB_URL=${DB_URL} \
    DB_USERNAME=${DB_USERNAME} \
    DB_PASSWORD=${DB_PASSWORD}

COPY --from=build-stage /app/target/*.jar app.jar

EXPOSE ${PORT}

CMD ["java", "-Xmx512m", "-jar", "-Dserver.port=${PORT}", "-Dspring.profiles.active=${PROFILE}", "app.jar"]