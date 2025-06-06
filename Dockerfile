# Dockerfile para Backend Spring Boot - VERSÃO DEFINITIVA
FROM openjdk:17-jdk-slim AS build

# Instalar Maven manualmente (mais confiável que imagens Maven)
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar arquivos do projeto
COPY Backend/pom.xml ./
COPY Backend/src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Estágio final - runtime
FROM openjdk:17-jdk-slim

# Instalar curl para health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expor a porta
EXPOSE 10000

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:10000/actuator/health || exit 1

# Comando para executar a aplicação
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]