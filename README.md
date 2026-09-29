# Raízes do Nordeste - Backend

API REST desenvolvida para a disciplina **Projeto Multidisciplinar – Trilha Back-End da UNINTER**.

O sistema permite o gerenciamento de **usuários, clientes, produtos, unidades e estoques**, além de possuir autenticação de usuários e controle de acesso.

---

## Tecnologias utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT
* Hibernate
* MySQL
* Maven
* Swagger
* Postman

---

## Pré-requisitos

Para executar o projeto, é necessário ter instalado:

* Java 17
* Maven
* MySQL
* IDE como Eclipse ou IntelliJ
* Postman (opcional)

---

## Como executar

### 1. Clone o projeto

```bash
git clone https://github.com/adk008/raizes-do-nordeste-backend.git
```

### 2. Abra o projeto

Abra o projeto em sua IDE como um projeto **Maven** e aguarde o carregamento das dependências.

### 3. Configure o banco de dados

Crie um banco MySQL chamado:

```sql
CREATE DATABASE raizes_nordeste;
```

Depois, configure as informações do banco no arquivo:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### 4. Execute a aplicação

Execute a classe:

```text
RaizesnordesteApplication.java
```

A aplicação será iniciada, por padrão, em:

```text
http://localhost:8090
```

---

## Swagger

A API possui documentação através do Swagger.

Com a aplicação em execução, acesse:

```text
http://localhost:8090/swagger-ui.html
```

O Swagger permite visualizar e testar os endpoints disponíveis.

---

## Principais endpoints

| Método | Endpoint                  | Descrição                        |
| ------ | ------------------------- | -------------------------------- |
| POST   | `/auth/login`             | Realiza login                    |
| POST   | `/usuarios`               | Cadastra usuário                 |
| GET    | `/clientes`               | Lista clientes                   |
| GET    | `/clientes/{id}`          | Consulta cliente                 |
| POST   | `/clientes`               | Cadastra cliente                 |
| GET    | `/produtos`               | Lista produtos                   |
| POST   | `/produtos`               | Cadastra produto                 |
| PUT    | `/produtos/{id}`          | Atualiza produto                 |
| DELETE | `/produtos/{id}`          | Exclui produto                   |
| GET    | `/unidades`               | Lista unidades                   |
| POST   | `/unidades`               | Cadastra unidade                 |
| PUT    | `/unidades/{id}`          | Atualiza unidade                 |
| DELETE | `/unidades/{id}`          | Exclui unidade                   |
| GET    | `/estoques`               | Consulta estoques                |
| POST   | `/estoques/movimentacoes` | Registra movimentação de estoque |
| GET    | `/auditoria`              | Consulta registros de auditoria  |

---

## Autenticação

O sistema utiliza **JWT** para autenticação.

Para acessar os endpoints protegidos, primeiro é necessário realizar o login:

```http
POST /auth/login
```

Após o login, o token recebido deve ser enviado nas requisições utilizando:

```http
Authorization: Bearer SEU_TOKEN
```

O sistema possui os seguintes perfis de usuário:

* `ADMIN`
* `GERENTE`
* `CLIENTE`

---

## Testes

Os endpoints podem ser testados utilizando o **Postman** ou diretamente através do **Swagger**.

Para realizar os testes, primeiro execute a aplicação e depois utilize o endereço:

```text
http://localhost:8090
```

---

## Autor

**Thiago Nascimento Nery**

Projeto desenvolvido para a disciplina **Projeto Multidisciplinar – Trilha Back-End — UNINTER**.
