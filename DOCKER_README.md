# Сборка и запуск докер контейнера
```
git clone https://github.com/Smartslame/hackathon-2023.git
cd hackathon-2023
mvn clean package
sudo docker build -t spring-boot-docker:spring-docker .
docker run -d -p 8080:8080 spring-boot-docker:spring-docker .
```