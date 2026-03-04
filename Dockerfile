# 后端 Dockerfile
FROM maven:3.6.3-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:8-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

RUN mkdir -p /app/config
COPY src/main/resources/application-docker.yml /app/config/application.yml
COPY src/main/resources/ip2region /app/ip2region

ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV SPRING_PROFILES_ACTIVE=docker

EXPOSE 8085
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar -Dspring.config.additional-location=/app/config/ app.jar"]
