# Chemin par défaut vers le JAR construit par Maven
ARG JAR_FILE=target/api-what-time-1.0.jar

# Runtime final minimal prêt à l'emploi (Java 25 JRE sur UBI 10 minimal)
FROM eclipse-temurin:25-jre-ubi10-minimal

# Répertoire de travail de l'application
WORKDIR /app

# Copie du JAR Spring Boot depuis target/
COPY ${JAR_FILE} /app/app.jar

# Port HTTP par défaut de Spring Boot (modifiable par configuration)
EXPOSE 8080

# Options JVM injectables au runtime (mémoire, GC, profils, etc.)
ENV JAVA_OPTS=""

# Lancement de l'application
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
