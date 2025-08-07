# Como rodar o projeto na versão atual


- Baixar a imagem do K6 para executar os testes via docker container(Fiz isso para não instalar diretamente na máquina)
    - ```$ docker pull grafana/k6 ```
- Dentro do projeto executar para 'matar' os container para evitar de existir cache e não subir a versão mais nova do código
  - ```$ docker-compose down -v --remove-orphans```
- Caso tenha uma imagem do projeto, se não mudar a versão da imagem, remova a existente para evitar de rodar a mesma versão
  antiga quando houver alterações.
  - ```$ docker image rm rinha2025-api:latest```
- Buildar o projeto para gerar o .jar
  - ```$ mvn clean package -DskipTests ```
- Gerar a imagem da aplicação com no docker(Existem comandos e Dockerfile que podem fazer tudo de uma vez, mas escolhi fazer separado)
  - ```$ docker build -t rinha2025-api:latest .```
- Subir os containers do projeto(API´s, Nginx e o Mongo)
  - ```$ docker-compose up -d```
- Esse é basicamente o comando para executar os testes no K6 via docker
  - ``` $ docker run --rm -i --network payment-processor -v "C:\projetos\rinha2025\scripts:/scripts" grafana/k6 run /scripts/rinha.js ```
### Mais informações só visitar o repositório oficial da rinha

[Link da Rinha](https://github.com/zanfranceschi/rinha-de-backend-2025)