# 🚀 Back Votes Challenge 📚

Este projeto trata-se de um desafio da South System, ele consiste basicamente em uma API REST para realizar alguns fluxos de cadastro e votação de Pautas em uma Sessão. As tecnologias usadas para isso foram Java, Spring Boot, Kafka, Flyway, Postgres e Swagger.

[![GitHub stars](https://img.shields.io/github/stars/matheuscarv69/back-votes-challenge?color=7159)](https://github.com/matheuscarv69/back-votes-challenge/stargazers)
![Maven Central with version prefix filter](https://img.shields.io/maven-central/v/org.apache.maven/apache-maven/3.6.3?color=7159)

## 🤔 O que é este projeto?

Esse projeto foi desenvolvido baseando-se no contexto de cooperativismo, onde cada associado possui um voto e as decisões são tomadas em assembleias, por meio de votações. A proposta da API aqui desenvolvida era solucionar o gerenciamento dessas sessões de votação.

## 📑 To-do do Projeto

- [x] Cadastrar uma nova pauta;
- [x] Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);
- [x] Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta);
- [x] Contabilizar os votos e dar o resultado da votação na pauta.

Durante o processo de desenvolvimento foram feitas algumas anotações no Notion, no link abaixo você pode ver algumas abstrações, solução técnica e alguns diagramas de classe e ER.

## 📃 [Notion - Anotações](https://superb-thing-758.notion.site/Solu-o-51a1655781fb4c358738e6aee459e9fe)

## 🚀 Executando a aplicação 👾
### 📍 Local

Se você quiser fazer alguma modificação no código precisará ter instalado em sua máquina as seguintes ferramentas:

- [Java JDK 11+](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html)
- [Maven 3.6.3](https://maven.apache.org/download.cgi)

### 🐳 Docker

Este projeto conta com um **docker-compose**, inclusive a própria imagem da aplicação já está configurada para ser
executada no docker.

Os requisitos para isso são:

- [Docker](https://www.docker.com/products/docker-desktop) - Baixe de acordo com o seu SO
- [Docker-compose](https://docs.docker.com/compose/install/) - Se estiver utilizando Windows, o Docker desktop já possui o docker-compose instalado

A imagem da aplicação também está disponível no Docker Hub:

- [matheuscarv69/back-votes-challenge](https://hub.docker.com/r/matheuscarv69/back-votes-challenge)

Caso queria executa-lá através dessa imagem, abaixo tem alguns comandos que podem lhe ajudar:

## ⬇ 1. Pull

```shell
docker pull matheuscarv69/back-votes-challenge
```
## 🏃‍♂️ 2. Running

O comando abaixo executa a imagem baixada em um container, essa aplicação possui algumas env vars, atenção para as que possuem o sufixo **HOST**, neste você deve colocar o ipv4 da sua máquina.

```shell
docker run -d -p 8081:8081 -e SERVER_PORT=8081:8081 -e DATABASE_URL="192.168.0.115:5432" -e DATABASE_DB="postgres" -e DATABASE_USER="postgres" -e DATABASE_PASSWORD="password" -e DB_SCHEMA="backvotes" -e DELAY_SCHELUDED_CHECK_SESSION="10000" -e KAFKA_HOST="192.168.0.115:29092" matheuscarv69/back-votes-challenge
```
## 🎲 Executando a API com o docker-compose

Com esse repositório já clonado em sua máquina e com todos os pré-requisitos atendidos.

1. Você deve ir até a raiz do projeto onde o arquivo **docker-compose.yml** está.
2. Deve abrir um terminal na raiz do projeto.
3. Agora certifique-se que o seu Docker já está em execução.
4. Execute o seguinte comando no terminal:

```bash
docker-compose up -d
```

5. Com isso sua aplicação já está em execução por padrão na porta local 8080

## 🔧 Variáveis de Ambiente da Aplicação

| ENV_VARS                      | Descrição                                                                                                                               |
| ----------------------------- | --------------------------------------------------------------------------------------------------------------------------------------- |
| SERVER_PORT                   | Porta que a aplicação utilizará em sua execução. (Default: 8080)                                                                        |
| DATABASE_URL                  | Url do banco de dados utilizado. (Default: localhost:5432; Postgres)                                                                    |
| DATABASE_DB                   | Banco de dados utilizado dentro. (Default: postgres)                                                                                    |
| DATABASE_USER                 | Usuário do banco de dados. (Default: postgres)                                                                                          |
| DATABASE_PASSWORD             | Senha do banco de dados. (Default: password)                                                                                            |
| DB_SCHEMA                     | Esquema do banco de dados. (Default: backvotes)                                                                                         |
| DELAY_SCHELUDED_CHECK_SESSION | Delay em milisegundos do Scheduled para verificar se sessão está fechada e buscar resultados de votação. (Default: 10000 = 10 segundos) |
| KAFKA_HOST                    | Url do Kafka. (Default: localhost: 29092)                                                                                               |

## 📝Fazendo requisições - Insomnia

Essa aplicação tem um workspace compartilhado com o projeto [Back Votes Kafka Consumer](https://github.com/matheuscarv69/back-votes-challenge-kafka-consumer) com todas as requisições disponíveis configurado no aplicativo **Insomnia**, clicando no botão abaixo você pode
baixar o workspace de requests utilizados nesse projeto.

A pasta com as Requests do Back Votes Kafka Consumer é chamada Kafka Consumer.

[![Run in Insomnia}](https://insomnia.rest/images/run.svg)](https://insomnia.rest/run/?label=Back%20Vote%20Challenge&uri=https%3A%2F%2Fgist.githubusercontent.com%2Fmatheuscarv69%2F07483d2d88c763e23b0405b34bb9909a%2Fraw%2F161df15967626e742678e4699e8657f86e8dfc9c%2Frequests-back-vote-challenge)

Além disso a mesma foi documentada usando o Swagger, por meio dele você também pode ter acesso as requisições e aos modelos de dados recebidos e enviados pela aplicação.
Com a aplicação sendo executada, você pode acessar a página do Swagger por meio da url abaixo.

```bash
http://localhost:8080/api/v1/backvotes/swagger-ui/
```

Você acessará essa tela:

<img src="https://i.imgur.com/O2gi942.png" min-width="400px" max-width="600px"  alt="swagger">

## 📡 Integrações externas 📶
Foi disponibilazada um API externa para verificar o CPF e a partir do response retornar autorizar ou não a votação do Associado.

```bash
https://user-info.herokuapp.com/users/%7Bcpf%7D
```

### 🚀 Back Votes Challenge Kafka Consumer 📚
Essa é uma API que foi desenvolvida para ser o Kafka Consumer para este Projeto, no link abaixo você pode acessar toda a documentação e a aplicação.

```bash
https://github.com/matheuscarv69/back-votes-challenge-kafka-consumer
```

## 🚀 Tecnologias 👩‍🚀

As seguintes tecnologias foram utilizadas no desenvolvimento do projeto.

- Java 11
- Spring Boot 2.5.4
  - Web
  - Data JPA
  - Feign
  - Validation
  - Profiles (dev)
- Swagger
- Flyway
- Postgres
- Kafka

## 👨🏻‍💻 Autor

<br>
<a href="https://github.com/matheuscarv69">
 <img style="border-radius: 35%;" src="https://avatars1.githubusercontent.com/u/55814214?s=460&u=ffb1e928527a55f53df6e0d323c2fd7ba92fe0c3&v=4" width="100px;" alt=""/>
 <br />
 <sub><b>Matheus Carvalho</b></sub></a> <a href="https://github.com/matheuscarv69" title="Matheus Carvalho">🚀</a>

Feito por Matheus Carvalho, entre em contato!✌🏻

 <p align="left">
    <a href="mailto:matheus9126@gmail.com" alt="Gmail" target="_blank">
      <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white&link=mailto:matheus9126@gmail.com"/></a>
    <a href="https://www.linkedin.com/in/matheus-carvalho69/" alt="Linkedin" target="_blank">
        <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white&link=https://www.linkedin.com/in/matheus-carvalho69/"/></a>  
    <a href="https://www.instagram.com/_mmcarvalho/" alt="Instagram" target="_blank">
      <img src="https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white&link=https://www.instagram.com/_mmcarvalho/"/></a>  
  </p>
