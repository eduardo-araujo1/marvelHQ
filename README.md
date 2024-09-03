# MarvelHqShop

## Tecnologias Utilizadas

- **Java 17:**
- **Spring Boot 3.2.2:**
- **Maven 3.9.6:**
- **MySQL 8.0:** 
- **Flyway:**
- **Spring Security:** 
- **Java JWT:** 
- **Springdoc OpenAPI:** 

## Rodando o Projeto com Docker

1. **Certifique-se de ter o Docker instalado em sua máquina.**

2. **Construa e inicie os containers:**

   Execute o seguinte comando no diretório onde está localizado o arquivo `docker-compose.yml`:

   ```bash
   docker-compose up -d

**Este comando irá:**

- Construir e iniciar o container MySQL.
- Construir e iniciar o container da API, que estará configurado para usar o MySQL.

### Acesse a documentação da API

Depois que os containers estiverem rodando, você pode acessar a interface Swagger UI para testar os endpoints da API em:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Estrutura do Projeto

- **População do Banco de Dados:** A população do banco é realizada pelo Flyway, que adiciona alguns produtos ao banco de dados quando a aplicação é iniciada.

- **Dockerfile:** Utiliza o Maven para construir a aplicação e o OpenJDK Alpine para executar a aplicação.

- **docker-compose.yml:** Define dois serviços:
    - `mysql`: Fornece o banco de dados.
    - `apihqshop`: A aplicação Spring Boot.

## Parar e Remover Containers

Para parar e remover os containers quando não precisar mais deles, execute o seguinte comando no diretório onde está localizado o arquivo `docker-compose.yml`:

```bash
docker-compose down