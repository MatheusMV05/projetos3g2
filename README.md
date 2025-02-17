# Projetos3 🚀

[![GitHub license](https://img.shields.io/github/license/MatheusMV05/projetos3)](https://github.com/MatheusMV05/projetos3/blob/main/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/MatheusMV05/projetos3)](https://github.com/MatheusMV05/projetos3/issues)
[![GitHub stars](https://img.shields.io/github/stars/MatheusMV05/projetos3)](https://github.com/MatheusMV05/projetos3/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/MatheusMV05/projetos3)](https://github.com/MatheusMV05/projetos3/network)

Breve descrição do projeto explicando seu propósito principal e funcionalidades em poucas linhas.

## ⚡ Tecnologias Utilizadas

- ☕ Java 17
- 🍃 Spring Boot 3.x
- 🔒 Spring Security
- 🎯 Spring Data JPA
- 🐘 PostgreSQL
- 🔧 Maven
- 🐳 Docker
- 🧪 JUnit 5
- 📚 Swagger/OpenAPI

## 📋 Pré-requisitos

Liste todos os pré-requisitos necessários para executar o projeto:

- ☕ Java Development Kit (JDK) 17 ou superior
- 🔧 Maven 3.8+
- 🐳 Docker e Docker Compose
- 🐘 PostgreSQL (se não estiver usando Docker)

## 🛠️ Configuração do Ambiente

1. Clone o repositório:
```bash
git clone https://github.com/MatheusMV05/projetos3.git
cd projetos3
```

2. Configure as variáveis de ambiente:
   - Crie um arquivo `.env` baseado no `.env.example`
   - Ajuste as variáveis conforme seu ambiente

3. Configure o banco de dados:
   - Se estiver usando Docker:
   ```bash
   docker-compose up -d database
   ```
   - Se estiver usando PostgreSQL local:
     - Crie um banco de dados
     - Atualize as configurações em `application.properties`

## 🚀 Executando o Projeto

### Usando Maven:

```bash
mvn spring-boot:run
```

### Usando Docker:

```bash
docker-compose up --build
```

A aplicação estará disponível em: `http://localhost:8080`

## 📖 Documentação da API

A documentação da API está disponível através do Swagger UI:
- 💻 Local: `http://localhost:8080/swagger-ui.html`
- 🌐 Produção: `https://seu-dominio.com/swagger-ui.html`

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/empresa/projeto/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       └── security/
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test/
    └── java/
        └── com/empresa/projeto/
            ├── controller/
            ├── service/
            └── repository/
```

## 🧪 Testes

Execute os testes usando:

```bash
mvn test
```

Para relatório de cobertura:

```bash
mvn verify
```

## 🚀 Deploy

Instruções para deploy em diferentes ambientes:

### 💻 Desenvolvimento
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

### 🌐 Produção
```bash
mvn spring-boot:run -Dspring.profiles.active=prod
```

## 🤝 Contribuindo

1. Fork o projeto
2. Crie sua branch de feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📌 Versionamento

Usamos [SemVer](http://semver.org/) para versionamento. Para as versões disponíveis, veja as [tags neste repositório](https://github.com/MatheusMV05/projetos3/tags).

## ✨ Autores

* **Matheus Vieira** - *Trabalho inicial* - [MatheusMV05](https://github.com/MatheusMV05)

Veja também a lista de [contribuidores](https://github.com/MatheusMV05/projetos3/contributors) que participaram deste projeto.

## 📝 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE.md](LICENSE.md) para detalhes

## 🙏 Agradecimentos

* Mencione pessoas ou projetos que ajudaram
* Inspirações
* Referências

## 📊 Status do Projeto

![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-green)
* Versão atual: 1.0.0
* Última atualização: 17/02/2025

---
⌨️ com ❤️ por [MatheusMV05](https://github.com/MatheusMV05) 😊
