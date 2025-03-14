# Desafio Imposto
      
  ## :desktop_computer:Propósito do Trabalho
  O projeto **Desafio Imposto** tem como objetivo o cálculo de três tipos de impostos: **ICMS**, **ISS** e **IPI**. A aplicação permite:
  - Cadastro de usuários.
  - Login de usuários.
  - Cadastro de três tipos de impostos.
  - Exibição individual de impostos por ID.
  - Exclusão de impostos por ID.
  - Exibição de uma lista de impostos cadastrados.
  - Cálculo de cada imposto com base na alíquota cadastrada e no salário base enviado.
    
---
    
  ## :computer:Tecnologias Necessárias
  Para rodar a aplicação, é necessário ter as seguintes tecnologias instaladas:
  - **Java** (versão compatível com SpringBoot).
  - **IntelliJ IDEA** (ou outra IDE de sua preferência).
  - **PostgreSQL** (com um banco de dados chamado `imposto`).
    
  ---
    
  ## :abacus: Como Rodar a Aplicação no IntelliJ
  1. Faça o download do arquivo ZIP do repositório no GitHub ou use o comando `git clone` para clonar o repositório.
  2. Abra o projeto no IntelliJ IDEA.
  3. Certifique-se de que todas as dependências foram baixadas corretamente. Caso contrário, execute o comando:
      ``` bash
        mvn clean install
      ```
  4. Faça o build do projeto novamente.
  5. Configure o banco de dados PostgreSQL com o nome `imposto` antes de iniciar a aplicação.
  6. Execute a aplicação.
   
  ---
  
  ## :books: Dependências Usadas
  As seguintes dependências foram utilizadas no projeto:
  - **Spring Data JPA**
  - **Spring Security**
  - **JWT (JSON Web Token)**
  - **PostgreSQL Driver**
  - **Lombok**
  - **JJWT Jackson**
  - **JUnit**
  - **Mockito**
  - **Swagger 3 (OpenAPI)**
  - **Jacoco**
  
  ---
   
  ## :bookmark_tabs: Configuração do `application.properties`
  Certifique-se de que o banco de dados PostgreSQL está configurado corretamente com o nome `imposto`. Também adicione as variáveis de ambiente para **usuário** e **senha** para o banco de dados:
  ```bash
        spring.datasource.username=${DB_USERNAME}
        spring.datasource.password=${DB_PASSWORD}
  ```
  
  ---
  
  ## :office_worker: Roles de Usuário para Cada API
  As permissões para cada API são as seguintes:
  
  ### **UsuarioController**
  - **POST /user/register**: Acesso público (não requer autenticação).
  - **POST /user/login**: Acesso público (não requer autenticação).
  
  ### **TiposController**
  - **GET /tipos**: Requer autenticação.
  - **POST /tipos**: Requer autenticação com a role `ADMIN`.
  - **GET /tipos/{id}**: Requer autenticação.
  - **DELETE /tipos/{id}**: Requer autenticação com a role `ADMIN`.
  
  ### **CalculoController**
  - **POST /calculo**: Requer autenticação.
