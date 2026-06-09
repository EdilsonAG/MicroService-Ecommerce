FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/*.jar /app/api.jar

EXPOSE 8081

CMD ["java", "-jar","api.jar"]