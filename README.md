# Online Bookstore

**RESTful API** para a gestão de uma livraria online.

### Features

- [X] Cadastro de autor
- [x] Listagem de autores
- [x] Atualizar autor
- [x] Remover autor
- [x] Detalhar autor
- [x] Cadastro de livro
- [x] Listagem de livros
- [x] Atualizar livro
- [x] Remover livro
- [x] Detalhar livro
- [x] Listar usuários
- [x] Cadastrar usuário
- [x] Relatório de quantidade de livros publicados por autor
- [x] Autenticação e autorização

### Endpoints

Path | HTTP Method | Description
---|---|---
/autores     | GET    | Lista autores
/autores     | POST   | Cadastra autor
/autores/id  | PUT    | Atualiza autor
/autores/id  | DELETE | Remove autor
/autores/id  | GET    | Detalha autor
/livros      | GET    | Lista livros
/livros      | POST   | Cadastra livro
/livros/id   | PUT    | Atualiza livro
/livros/id   | DELETE | Remove livro
/livros/id   | GET    | Detalha livro
/usuarios    | GET    | Lista usuários
/usuarios    | POST   | Cadastra usuário
/relatorios  | GET    | Exibe relatório

### Tecnologias

As seguintes ferramentas foram usadas no desenvolvimento do projeto:

- [Java 11](https://www.oracle.com/java/technologies/downloads/#java11)
- [Git](https://git-scm.com)
- [Spring Initializr](https://start.spring.io/)
- [Postman](https://www.postman.com/downloads/)
- [Spring Boot DevTools (Dependencies)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
- [Spring Web (Dependencies)](https://mvnrepository.com/artifact/org.springframework/spring-web)
- [Bean Validation API (Dependencies)](https://mvnrepository.com/artifact/javax.validation/validation-api)
- [Lombok (Dependencies)](https://projectlombok.org/setup/maven)
- [ModelMapper (Dependencies)](http://modelmapper.org/)
- [Flyway Core](https://mvnrepository.com/artifact/org.flywaydb/flyway-core)
- [Spring Boot Starter Data JPA](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
- [SpringFox Swagger2](https://mvnrepository.com/artifact/io.springfox/springfox-swagger2)
- [SpringFox Boot Starter](https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter/3.0.0)
- [SpringFox Swagger UI](https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui)
- [Spring Boot Starter Test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test)
- [Spring Boot Starter Security](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)
- [JSON Web Token Support For The JVM](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt)
