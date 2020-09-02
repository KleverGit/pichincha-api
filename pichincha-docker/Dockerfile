FROM openjdk:8-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} pichincha-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/pichincha-api.jar"]
