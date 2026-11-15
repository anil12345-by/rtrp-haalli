# Docker Practical Experiments 1 \& 2

Commands tested with Docker Desktop on Windows. Run PowerShell or CMD
with Docker Desktop open and running.

## Experiment 1 --- Run a single Nginx container

### 1\. Check Docker

``` powershell
docker --version
docker info
```

### 2\. Download the Nginx image

``` powershell
docker pull nginx:latest
```

### 3\. Run the container

We used host port **8081** because port 8080 was already occupied by
Jenkins.

``` powershell
docker run -d --name mynginx -p 8081:80 nginx
```

### 4\. Check the running container

``` powershell
docker ps
docker ps -a
```

### 5\. Open the website

Open this address in your browser:

``` text
http://localhost:8081
```

You should see the Nginx welcome page.

### 6\. View container logs

``` powershell
docker logs mynginx
```

### 7\. Stop, start, and remove the container

``` powershell
docker stop mynginx
docker start mynginx
docker rm -f mynginx
```

The last command removes the container. Run it only when you want to
clean up Experiment 1.

\---

## Experiment 2 --- Docker Compose with Nginx, PostgreSQL, and Redis

### 1\. Create the project folder

``` powershell
mkdir C:\\\\Users\\\\LENOVO\\\\compose-lab
cd C:\\\\Users\\\\LENOVO\\\\compose-lab
```

### 2\. Create the Compose YAML file

Create a file named **docker-compose.yml** in this folder.

notepad docker-compose.yml

Paste this exact content:

``` yaml
services:
  web:
    image: nginx:latest
    ports:
      - "8082:80"

  db:
    image: postgres:15
    environment:
      POSTGRES\\\_USER: demo
      POSTGRES\\\_PASSWORD: demo
      POSTGRES\\\_DB: demo\\\_db

  redis:
    image: redis:alpine
```

Save the file as `docker-compose.yml` (not `docker-compose.yml.txt`).

### 3\. Start all services

Run these commands from the `compose-lab` folder:

``` powershell
docker compose up -d
```

### 4\. Check the services

``` powershell
docker compose ps
docker ps
```

You should see three containers: web, db, and redis.

### 5\. Open the Nginx website

Open this address in your browser:

``` text
http://localhost:8082
```

### 6\. View logs

``` powershell
docker compose logs
docker compose logs web
docker compose logs db
docker compose logs redis
```

### 7\. Stop the services

``` powershell
docker compose stop
```

Start them again:

``` powershell
docker compose start
```

### 8\. Stop and remove the Compose containers and network

``` powershell
docker compose down
```

This removes the Compose containers and network. It does not remove
named volumes unless you add `-v`.

\---

## Quick command reference

Task                                Command

\---

Check Docker version                `docker --version`
List running containers             `docker ps`
List all containers                 `docker ps -a`
Pull Nginx                          `docker pull nginx:latest`
Run Experiment 1                    `docker run -d --name mynginx -p 8081:80 nginx`
View Experiment 1 logs              `docker logs mynginx`
Start Compose                       `docker compose up -d`
Check Compose services              `docker compose ps`
View Compose logs                   `docker compose logs`
Stop Compose services               `docker compose stop`
Start stopped Compose services      `docker compose start`
Remove Compose containers/network   `docker compose down`

## Upload this project to GitHub

From inside `C:\\\\Users\\\\LENOVO\\\\compose-lab`, initialize Git and commit the
YAML file:

``` powershell
git init
git add docker-compose.yml
git commit -m "Add Docker Compose experiment 2"
```

Create an empty repository on GitHub, then replace the URL below with
your own repository URL:

``` powershell
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPOSITORY.git
git push -u origin main
```

You can also save this document in the repository as `README.md`.

## Notes

* Experiment 1 uses host port `8081` → container port `80`.
* Experiment 2 uses host port `8082` → container port `80`.
* If a port is already in use, choose another unused host port and
update the browser URL accordingly.
* The PostgreSQL username, password, and database name here are
demonstration values only. Do not use these credentials for real
applications.
* Keep Docker Desktop running while executing these commands.


``` powershell
docker --version
docker info
* docker pull nginx:latest
* docker run -d --name mynginx -p 8081:80 nginx
* docker ps
docker ps -a
* docker logs mynginx
* docker stop mynginx
* docker start mynginx
* docker rm -f mynginx
* \#2

mkdir compose-lab

cd compose-lab

notepad docker-compose.yml

.



``` yaml

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



&#x20; redis:

&#x20;   image: redis:alpine





docker compose up -d

docker compose ps

notepad docker-compose.yml

&#x20; redis:

&#x20;   image: redis:alpine

docker compose up -d

docker compose ps



docker compose logs

docker compose logs web

docker compose logs db

docker compose logs redis

docker compose stop

docker compose start

docker compose down



