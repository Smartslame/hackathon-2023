FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/spring-boot-docker.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]