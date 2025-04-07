# Utiliser une image de base OpenJDK 17 sur Alpine Linux
FROM openjdk:17-jdk-alpine

# Créer un volume pour stocker des fichiers temporaires
VOLUME /tmp

# Argument pour le fichier JAR généré par Maven (assurez-vous que le nom correspond)
ARG JAR_FILE=target/Foyer-1.5.0-SNAPSHOT.jar

# Copier le fichier JAR dans l’image
COPY ${JAR_FILE} app.jar

# Exposer le port 8080 (modifiez si nécessaire)
EXPOSE 8080

# Commande d’exécution de l’application
ENTRYPOINT ["java", "-jar", "/app.jar"]
