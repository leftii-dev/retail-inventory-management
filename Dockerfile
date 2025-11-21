FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

COPY target/retail-inventory-management-system.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]