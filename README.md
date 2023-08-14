# Auto Atendimento API - Bootcamp TQI Kotlin 2023

Este projeto foi criado com a proposta de fornecer  para uma API Rest para solucionar a finalização de vendas de um comércio em um auto-atendimento.

## Tecnologias
* JAVA 17
* Kotlin
* Gradlew
* Spring boot
* Spring JPA
* PostgreSQL
* Flywaydb

## Banco de dados
* Arquivo docker-compose na raiz do projeto configurado para subir o PostgreSQL.
* Flyway para rodas os scrips de inalização na subida do projeto.

## Projeto
Seguir os seguintes comandos para subir a aplicação
* ./docker-compose up -d
* ./gradlew clean build
* ./gradlew bootRuun

## Testes
* Para realizar os testes unitários, rodar comando:
  * ./gradlew test
* contém o arquivo [collections-postman](jumarket-auto-atendimento.postman_collection.json) para realizar testes manuais.




## Funcionalidades do Projeto

A API de auto-atendimento foi criada com as funcionalidades abaixo para controle interno dos administradores da mercearia:

  * Fazendo a requisição da categoria de produtos temos a busca, a criação com as informações de ID e nome, alteração e exclusão da categoria. 
Os produtos só poderão ser cadastrados após a criação da categoria para a associação correspondente. 
  * Para a requisição dos produtos temos a busca de todos já inseridos no sistema, a criação de novos com
as informações de ID, nome, preço de custo, preço de venda, código SKU, unidade de medida e a categoria correspondente.

Para uso externo dos futuros clientes utilizando o auto-atendimento da mercearia nos temos:

  * A requisição para criar um novo carrinho de compras se inicia após aproximar o primeiro 
código SKU do produto no terminal totem de auto-atendimento, como resposta temos um novo carrinho criado com ID, 
data e hora de criação, valor total do carrinho e com o produto adicionado com quantidade, valor venda e valor total do carrinho. 
  * Ao aproximar um próximo produto faz a requisição de adicionar produto ao carrinho, se o produto já existe é alterado apenas a
quantidade e valor, e para novos produtos é realizado uma nova adição, as informações como lista de produtos, quantidade, 
preço unitário e valor total são atualizados conforme os produtos são acrescentados no carrinho de compras.
  * Para a finalização do carrinho de compras, ao clicar em pagamento será realizado uma requisição com o ID do carrinho criado, retornando 
os dados de tipos de pagamento possíveis e valor total. Ao escolher a forma de pagamento, realizar o pagamento no auto 
atendimento e chamar a requisição de finalizar o pedido, passando o tipo de pagamento escolhido.