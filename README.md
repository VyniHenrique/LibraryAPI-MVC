# Library API

## Requisitos para executar a aplicação:
- Java 21
- Maven 3.9.9
- PostgreSQL 17.5

## Clone o repositório com o comando:

```
git clone https://github.com/VyniHenrique/LibraryAPI-MVC
```

## Sobre o application.yml
> Nesse arquivo você pode alterar algumas das configurações gerais do projeto, como alterar a URL, o username e a senha do banco de dados por exemplo:

```
spring:
  application:
    name: LibraryAPI
  output:
    ansi:
      enabled: ALWAYS
  datasource:
    url: ${DATASOURCE_URL:jdbc:postgresql://localhost:5432/library}
    username: ${DATASOURCE_USERNAME:postgres}
    password: ${DATASOURCE_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver
```

>Essa aplicação utiliza o sistema de login com o Google, então é necessário que você instancie o ClientID e o ClientSecret no arquivo `application.yml`.

> Aqui está o passo a passo para você obter o ClientID e o ClientSecret: https://developers.google.com/identity/gsi/web/guides/get-google-api-clientid

## Acesse o diretório em que você clonou o repositório e execute o projeto com:

```
mvn spring-boot:run
```

## Inicialize a aplicação e acesse a documentação com Swagger/OpenAPI copiando a seguinte URL:

```
localhost:8080/swagger-ui/index.html
```