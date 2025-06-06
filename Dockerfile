# Dockerfile para Backend Spring Boot
FROM maven:3.9.4-openjdk-17 AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar apenas os arquivos necessários para o Maven primeiro (para cache de dependências)
COPY Backend/pom.xml ./
COPY Backend/src ./src

# Fazer o build da aplicação
RUN mvn clean package -DskipTests

# Estágio final - runtime
FROM openjdk:17-jdk-slim

# Instalar curl para health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Criar usuário não-root
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Criar diretórios necessários
RUN mkdir -p /app/backups /app/uploads && chown -R spring:spring /app

# Mudar para usuário não-root
USER spring

# Expor a porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando para executar a aplicação
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod", "-jar", "app.jar"]