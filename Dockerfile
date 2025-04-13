# Étape 1 : Build de l’application
FROM maven:3.9.2-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Image finale
FROM eclipse-temurin:17-jdk
WORKDIR /app

ARG JAR_FILE=target/Foyer-1.8.0-SNAPSHOT.jar
COPY --from=builder /app/${JAR_FILE} app.jar

EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]
