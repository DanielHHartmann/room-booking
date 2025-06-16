# Room Booking Microservices
Sistema de microserviços para reserva de salas, desenvolvido com Spring Boot.

### Feito por
Daniel Henrique Hartmann

## Visão Geral
Este projeto é composto por três microserviços:

- User Service – Gerencia usuários.

- Room Service – Gerencia as salas.

- Booking Service – Gerencia as reservas.

Além disso, o sistema conta com os seguintes componentes:

- Eureka Server – Registro e descoberta de serviços (Netflix Eureka).

- API Gateway – Encaminha requisições para os serviços correspondentes, aplicando política de retry.

- Comunicação via:

  - REST

  - Mensageria (mensagem assíncrona entre serviços)

## Acesso ao Swagger
Você pode acessar a interface do Swagger pelo link:

👉 <http://localhost:8080/swagger-ui/index.html>

> No canto superior esquerdo do Swagger, é possível alternar entre os diferentes microsserviços para explorar suas rotas.

## Observação Importante
Devido à política de retry do API Gateway, pode haver um pequeno atraso na conexão inicial com os serviços. Caso receba uma mensagem de que o serviço não foi encontrado, aguarde alguns segundos e tente novamente.
Num ambiente real eu não teria as portas dos serviços expostos.
