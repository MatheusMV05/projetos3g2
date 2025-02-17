# Projetos3 🚀

[![GitHub license](https://img.shields.io/github/license/MatheusMV05/projetos3)](https://github.com/MatheusMV05/projetos3/blob/main/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/MatheusMV05/projetos3)](https://github.com/MatheusMV05/projetos3/issues)
[![GitHub stars](https://img.shields.io/github/stars/MatheusMV05/projetos3)](https://github.com/MatheusMV05/projetos3/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/MatheusMV05/projetos3)](https://github.com/MatheusMV05/projetos3/network)
![Azure DevOps builds](https://img.shields.io/azure-devops/build/seu-projeto/seu-pipeline)
![Azure DevOps coverage](https://img.shields.io/azure-devops/coverage/seu-projeto/seu-pipeline)

Breve descrição do projeto explicando seu propósito principal e funcionalidades em poucas linhas.

## ⚡ Tecnologias Utilizadas

### Backend
- ☕ Java 17
- 🍃 Spring Boot 3.x
- 🔒 Spring Security
- 🎯 Spring Data JPA
- 🐘 PostgreSQL
- 🔧 Maven
- 📚 Swagger/OpenAPI

### Frontend
- ⚛️ React 18
- 🎨 Material UI
- 🔄 React Query
- 🛣️ React Router
- 📡 Axios
- 💅 Styled Components

### DevOps & Cloud
- ☁️ Microsoft Azure
  - 🌐 Azure App Service
  - 🗄️ Azure Database for PostgreSQL
  - 🔄 Azure DevOps
  - 📦 Azure Container Registry
- 🐳 Docker
- 🧪 JUnit 5 & React Testing Library

## 📋 Pré-requisitos

- ☕ Java Development Kit (JDK) 17 ou superior
- 🔧 Maven 3.8+
- 📦 Node.js 18+ e npm/yarn
- 🐳 Docker e Docker Compose
- ☁️ Conta Azure e Azure CLI

## 🛠️ Configuração do Ambiente

1. Clone o repositório:
```bash
git clone https://github.com/MatheusMV05/projetos3.git
cd projetos3
```

2. Configure as variáveis de ambiente:
   - Crie arquivos `.env` para backend e frontend baseados nos `.env.example`
   - Configure as credenciais do Azure

3. Instale as dependências:

Backend:
```bash
cd backend
mvn install
```

Frontend:
```bash
cd frontend
npm install
```

## 🚀 Executando o Projeto

### Ambiente Local

Backend:
```bash
cd backend
mvn spring-boot:run
```

Frontend:
```bash
cd frontend
npm run dev
```

### Usando Docker:

```bash
docker-compose up --build
```

- Backend: `http://localhost:8080`
- Frontend: `http://localhost:5173`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## 📁 Estrutura do Projeto

```
/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── hooks/
│   ├── public/
│   └── package.json
│
├── .github/
├── azure-pipelines.yml
└── docker-compose.yml
```

## 🧪 Testes

Backend:
```bash
cd backend
mvn test
```

Frontend:
```bash
cd frontend
npm test
```

## ☁️ Deploy Azure

1. Configure o Azure CLI:
```bash
az login
```

2. Deploy do Backend:
```bash
az webapp up --runtime JAVA:17-java17 --sku B1 --name seu-app-name
```

3. Deploy do Frontend:
```bash
az staticwebapp create --name seu-frontend-name --resource-group seu-grupo
cd frontend && npm run build
az staticwebapp deploy --app-location ./dist
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

## 📊 Status do Projeto

![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-green)
* Versão atual: 1.0.0
* Última atualização: 17/02/2025

---
⌨️ com ❤️ por [MatheusMV05](https://github.com/MatheusMV05) 😊
