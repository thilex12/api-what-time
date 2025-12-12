FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

COPY target/api-what-time-1.0.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xms256m -Xmx512m"
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar app.jar"]
#docker build -t thilex12/what-time:1.13 .