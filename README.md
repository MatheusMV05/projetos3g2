# 🌐 Site Institucional - Brasfi

![GitHub](https://img.shields.io/github/license/MatheusMV05/projetos3g2)![GitHub repo size](https://img.shields.io/github/repo-size/MatheusMV05/projetos3g2)![GitHub language count](https://img.shields.io/github/languages/count/MatheusMV05/projetos3g2)![GitHub top language](https://img.shields.io/github/languages/top/MatheusMV05/projetos3g2)![GitHub last commit](https://img.shields.io/github/last-commit/MatheusMV05/projetos3g2)![GitHub contributors](https://img.shields.io/github/contributors/MatheusMV05/projetos3g2)![GitHub stars](https://img.shields.io/github/stars/MatheusMV05/projetos3g2?style=social)![GitHub forks](https://img.shields.io/github/forks/MatheusMV05/projetos3g2?style=social)![GitHub issues](https://img.shields.io/github/issues/MatheusMV05/projetos3g2)![GitHub pull requests](https://img.shields.io/github/issues-pr/MatheusMV05/projetos3g2)![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/MatheusMV05/projetos3g2)![GitHub commit activity](https://img.shields.io/github/commit-activity/m/MatheusMV05/projetos3g2)

Este repositório contém o código-fonte do site institucional da **Brasfi**, dividido entre frontend (React + TypeScript) e backend (Spring Boot), com persistência de dados em um banco PostgreSQL.

<div align="center">
  
[![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/) [![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/) [![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/) [![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/) [![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)](https://vitejs.dev/) [![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)

</div>

---


### 🛠️ Tecnologias Utilizadas

<table>
  <tr>
    <td align="center">
      <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" width="40" height="40" alt="Java"/>
      <br />
      <sub><b>Java</b></sub>
    </td>
    <td align="center">
      <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" width="40" height="40" alt="Spring"/>
      <br />
      <sub><b>Spring Boot</b></sub>
    </td>
    <td align="center">
      <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/react/react-original.svg" width="40" height="40" alt="React"/>
      <br />
      <sub><b>React</b></sub>
    </td>
    <td align="center">
      <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/typescript/typescript-original.svg" width="40" height="40" alt="TypeScript"/>
      <br />
      <sub><b>TypeScript</b></sub>
    </td>
    <td align="center">
      <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original.svg" width="40" height="40" alt="PostgreSQL"/>
      <br />
      <sub><b>PostgreSQL</b></sub>
    </td>
  </tr>
</table>

- **Backend**: Java + Spring Boot  
- **Frontend**: React + TypeScript  
- **Banco de Dados**: PostgreSQL  
- **Build Tools**: Maven (backend) & Vite (frontend)  

---

## 🗂️ Estrutura do Projeto


## 🖥️ **Frontend/** (React + TypeScript)

```
Frontend/
├── package.json              # Dependências e scripts do projeto
├── tsconfig.json             # Configuração do TypeScript
├── vite.config.ts            # Configuração do Vite
├── .env                      # Variáveis de ambiente (ex: API_URL)
└── src/
    ├── main.tsx              # Ponto de entrada da aplicação React
    ├── App.tsx               # Componente principal
    ├── App.css               # Estilos globais (ou .scss)
    ├── assets/               # Imagens, ícones, logos
    ├── components/           # Componentes reutilizáveis (Navbar, Footer, etc)
    ├── pages/                # Páginas do site (Home, Sobre, Contato, etc)
    ├── services/             # Módulos para chamadas à API (ex: paginaService.ts)
    ├── types/                # Tipagens TypeScript (ex: interfaces de Pagina)
    └── utils/                # Funções auxiliares (formatadores, validadores)
```

---

## ⚙️ **Backend/** (Spring Boot)

```
Backend/
├── pom.xml                   # Arquivo do Maven com dependências
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/brasfi/siteinstitucional/
    │   │       ├── config/          # Configurações (CORS, segurança, etc)
    │   │       ├── controller/      # Controladores REST
    │   │       ├── dto/             # DTOs para troca de dados
    │   │       ├── exception/       # Tratamento de erros personalizados
    │   │       ├── model/           # Entidades JPA (ex: Pagina.java)
    │   │       ├── repository/      # Interfaces JPA Repository
    │   │       ├── service/         # Lógica de negócio
    │   │       └── SiteInstitucionalApplication.java # Classe principal
    │   └── resources/
    │       ├── application.properties # Config do Spring Boot
    │       └── static/                # Arquivos estáticos, se houver
    └── test/
        └── java/
            └── com/brasfi/siteinstitucional/
                └── ... (testes unitários e de integração)
```

---

## 📁 Scripts Úteis

| Comando                     | Descrição                           |
|----------------------------|-------------------------------------|
| `./mvnw spring-boot:run`   | Inicia o backend Spring Boot        |
| `npm run dev`              | Inicia o frontend React             |
| `npm run build`            | Gera o build de produção do frontend |


---

## 👥 Autores

- Equipe de Desenvolvimento do **G2**
- Projeto da cadeira de projetos 3 da Cesar School

<div align="center">
  
### 📝 Licença

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

</div>

---

<div align="center">
  
### Feito com ❤️ por Studio RYB

</div>
