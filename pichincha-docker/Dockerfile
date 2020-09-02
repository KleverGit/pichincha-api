FROM openjdk:8-jdk
MAINTAINER kleverhidalgo
# Refer to Maven build -> finalName
ARG JAR_FILE=../target/pichincha-api-0.0.1-SNAPSHOT.jar.jar
# cd /opt/app
WORKDIR /opt/app
# cp target/pichincha-api-0.0.1-SNAPSHOT.jar.jar /opt/app/app.jar
COPY ${JAR_FILE} pichincha-api.jar
EXPOSE 8080
# java -jar /opt/app/pichincha-api.jar
ENTRYPOINT ["java","-jar","pichincha-api.jar"]