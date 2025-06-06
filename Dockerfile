# Dockerfile básico - versão mais simples possível
FROM maven:3.8.6-openjdk-17 AS build

WORKDIR /app

# Copiar tudo de uma vez
COPY Backend/ ./

# Build simples
RUN mvn clean package -DskipTests

# Runtime
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar JAR
COPY --from=build /app/target/*.jar app.jar

# Porta padrão do Render
EXPOSE 10000

# Executar
CMD ["java", "-jar", "app.jar"]