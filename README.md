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
git clone https://github.com/Thinery/raizes_do_nordeste.git
```

### 2. Abra o projeto

Abra o projeto em sua IDE como um projeto **Maven** e aguarde o carregamento das dependências.

### 3. Configure o banco de dados

Crie um banco MySQL chamado:

```sql
CREATE DATABASE raizes_nordeste;
```

As credenciais do banco e o segredo do JWT **não ficam no código** — são lidos de variáveis de ambiente, para não expor a senha no repositório público. Copie o arquivo de exemplo e preencha com os seus dados:

```bash
cp .env.example .env
```

```properties
DB_NAME=raizes_nordeste
DB_USERNAME=root
DB_PASSWORD=sua_senha_local_do_mysql
JWT_SECRET=troque-por-uma-string-secreta-bem-grande-e-aleatoria
```

O arquivo `.env` está no `.gitignore` e nunca deve ser commitado. Se estiver rodando pela IDE (Eclipse/IntelliJ), configure essas mesmas variáveis em *Run Configurations → Environment Variables* em vez de usar o `.env`.

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

## Dados de teste (seed automático)

Ao subir a aplicação pela primeira vez, ela já cria automaticamente unidades, produtos e um usuário de cada perfil, para facilitar os testes:

| Perfil    | E-mail                           | Senha        |
| --------- | -------------------------------- | ------------ |
| ADMIN     | admin@raizesdonordeste.com       | admin123     |
| GERENTE   | gerente@raizesdonordeste.com     | gerente123   |
| CLIENTE   | cliente@raizesdonordeste.com     | cliente123   |

Use essas credenciais em `POST /auth/login` para obter o token JWT.

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

`page` é 1-based (page=1 é a primeira página). Acesso: **público** = qualquer pessoa; **logado** = precisa de token válido, qualquer perfil; **ADMIN/GERENTE** e **ADMIN** = precisa do perfil indicado.

| Método | Endpoint                     | Descrição                                   | Acesso        |
| ------ | ----------------------------- | -------------------------------------------- | ------------- |
| POST   | `/auth/login`                 | Realiza login, devolve o token JWT            | público       |
| POST   | `/usuarios`                   | Cadastra usuário                              | público       |
| GET    | `/clientes`                   | Lista clientes                                | logado        |
| GET    | `/clientes/{id}`              | Consulta cliente                              | logado        |
| POST   | `/clientes`                   | Cadastra cliente (exige consentimento LGPD)   | logado        |
| GET    | `/produtos?page=1&limit=10`   | Lista produtos paginados                      | público       |
| GET    | `/produtos/{id}`              | Consulta produto                              | público       |
| POST   | `/produtos`                   | Cadastra produto                              | logado        |
| PUT    | `/produtos/{id}`              | Atualiza produto                              | logado        |
| DELETE | `/produtos/{id}`              | Exclui produto                                | logado        |
| GET    | `/unidades`                   | Lista unidades                                | público       |
| GET    | `/unidades/{id}`              | Consulta unidade                              | público       |
| GET    | `/unidades/{id}/estoque`      | Saldo de estoque da unidade                   | público       |
| POST   | `/unidades`                   | Cadastra unidade                              | logado        |
| PUT    | `/unidades/{id}`              | Atualiza unidade                              | logado        |
| DELETE | `/unidades/{id}`              | Exclui unidade                                | logado        |
| GET    | `/estoques`                   | Lista o saldo de todos os produtos/unidades   | público       |
| GET    | `/estoques/{id}`              | Consulta um registro de saldo                 | público       |
| POST   | `/estoques/movimentacoes`     | Registra entrada/saída (409 se saldo insuficiente) | ADMIN/GERENTE |
| GET    | `/auditoria`                  | Consulta registros de auditoria               | ADMIN         |

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

## Formato padrão de erro

Toda resposta de erro segue o mesmo formato:

```json
{
  "error": "ESTOQUE_INSUFICIENTE",
  "message": "Nao ha quantidade suficiente em estoque para esta unidade. Disponivel: 5",
  "details": [],
  "timestamp": "2026-09-29T10:15:30Z",
  "path": "/estoques/movimentacoes",
  "requestId": "b3f1..."
}
```

| Situação                              | HTTP |
| -------------------------------------- | ---- |
| Dado inválido (ex.: preço negativo)    | 422  |
| Sem token / token inválido             | 401  |
| Perfil sem permissão para a ação       | 403  |
| Recurso não encontrado                 | 404  |
| Regra de negócio violada (ex.: estoque insuficiente) | 409 |

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
