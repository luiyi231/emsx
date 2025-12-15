# Etapa de construcción (Mantenemos esta porque funcionó)
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución (CORREGIDA: Cambiamos openjdk por eclipse-temurin)
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]