# Raízes do Nordeste - Backend

Projeto desenvolvido para a disciplina Projeto Multidisciplinar – Trilha Back-End da UNINTER.

## Tecnologias

- Java 23
- Spring Boot
- Maven
- MySQL
- Spring Data JPA
- Hibernate
- Postman

---

## Como executar

### 1. Clone o projeto

```bash
git clone https://github.com/adk008/raizes-do-nordeste-backend.git
```

### 2. Abra no Eclipse

Importe como projeto Maven.

### 3. Configure o banco

Crie um banco MySQL chamado:

```
raizesdonordeste
```

No arquivo:

```
application.properties
```

Configure:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/raizesdonordeste
spring.datasource.username=root
spring.datasource.password=suaSenha
```

### 4. Execute

Rode:

```
RaizesnordesteApplication.java
```

A API iniciará em:

```
http://localhost:8080
```

---

## Endpoints

### Clientes

GET

```
/clientes
```

POST

```
/clientes
```

PUT

```
/clientes/{id}
```

DELETE

```
/clientes/{id}
```

---

### Produtos

GET

```
/produtos
```

POST

```
/produtos
```

PUT

```
/produtos/{id}
```

DELETE

```
/produtos/{id}
```

---

### Unidades

GET

```
/unidades
```

POST

```
/unidades
```

PUT

```
/unidades/{id}
```

DELETE

```
/unidades/{id}
```

---

### Estoques

GET

```
/estoques
```

POST

```
/estoques
```

PUT

```
/estoques/{id}
```

DELETE

```
/estoques/{id}
```

---

## Testes

Os testes foram realizados utilizando o Postman.

---

## Autor

Thiago Nascimento Nery

