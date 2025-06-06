# Dockerfile otimizado para plano gratuito do Render
FROM maven:3.9.4-openjdk-17-slim AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar apenas pom.xml primeiro para cache de dependências
COPY Backend/pom.xml ./
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY Backend/src ./src

# Fazer o build da aplicação (sem testes para economizar tempo)
RUN mvn clean package -DskipTests -B

# Estágio final - runtime otimizado
FROM openjdk:17-jdk-slim

# Instalar apenas o essencial
RUN apt-get update && \
    apt-get install -y --no-install-recommends curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Criar usuário não-root
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Criar diretórios necessários (mínimos)
RUN mkdir -p /tmp/backups && chown -R spring:spring /app /tmp/backups

# Mudar para usuário não-root
USER spring

# Expor a porta
EXPOSE 10000

# Health check simples
HEALTHCHECK --interval=60s --timeout=5s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:10000/actuator/health || exit 1

# Comando otimizado para free tier (menos memória)
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=prod", \
    "-Xmx400m", \
    "-Xms200m", \
    "-XX:+UseG1GC", \
    "-XX:MaxGCPauseMillis=200", \
    "-XX:+UseStringDeduplication", \
    "-jar", "app.jar"]