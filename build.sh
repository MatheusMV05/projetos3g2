#!/bin/bash

# build.sh - Script de build para o Render
# Arquivo para colocar na raiz do repositório

echo "🚀 Iniciando build do Backend BRASFI..."

# Navegar para o diretório do backend
cd Backend

echo "📦 Instalando dependências Maven..."
mvn clean compile

echo "🧪 Executando testes..."
mvn test

echo "🔨 Gerando JAR..."
mvn package -DskipTests

echo "✅ Build concluído com sucesso!"

# Verificar se o JAR foi criado
if [ -f "target/*.jar" ]; then
    echo "✅ JAR criado com sucesso!"
    ls -la target/*.jar
else
    echo "❌ Erro: JAR não foi criado!"
    exit 1
fi