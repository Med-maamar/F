# Étape 1 : Compilation de l'application
FROM maven:3.9.2-eclipse-temurin-17 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Création de l'image finale
FROM --platform=linux/amd64 openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=/app/target/Foyer-1.5.0-SNAPSHOT.jar
COPY --from=builder ${JAR_FILE} app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "/app.jar"]
