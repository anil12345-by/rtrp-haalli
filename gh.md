\#1
docker --version

docker info

docker pull nginx

docker images

docker run -d --name mynginx -p 8081:80 nginx

docker ps

\#2.

docker stop mynginx

docker rm mynginx

mkdir compose-lab

cd compose-lab

notepad docker-compose.yml



services:

&#x20; web:

&#x20;   image: nginx:latest

&#x20;   ports:

&#x20;     - "8082:80"



&#x20; db:

&#x20;   image: postgres:15

&#x20;   environment:

&#x20;     POSTGRES\_USER: demo

&#x20;     POSTGRES\_PASSWORD: demo

&#x20;     POSTGRES\_DB: demo\_db

docker compose up -d

docker compose ps

notepad docker-compose.yml

&#x20; redis:

&#x20;   image: redis:alpine

docker compose up -d

docker compose ps

docker compose down

