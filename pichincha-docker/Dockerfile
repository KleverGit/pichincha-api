# the first stage of our build will use a maven 3.6.1 parent image
FROM maven:3-openjdk-8 AS MAVEN_BUILD
# copy the pom and src code to the container
WORKDIR /opt/app
COPY .mvn /opt/app/.mvn
COPY pom.xml /opt/app/
COPY src /opt/app/src
# package our application code
RUN mvn clean package
# the second stage of our build will use open jdk 8 on alpine 3.9
FROM openjdk:8-jdk
MAINTAINER kleverhidalgo 
# where Spring Boot will store temporary files
VOLUME /tmp
# Refer to Maven build -> finalName
ARG JAR_FILE=target/pichincha-api-0.0.1-SNAPSHOT.jar
# cd /opt/app
WORKDIR /opt/app
# cp target/pichincha-api-0.0.1-SNAPSHOT.jar /opt/app/app.jar
COPY --from=MAVEN_BUILD ${JAR_FILE} /opt/app/pichincha-api.jar
EXPOSE 8080
# java -jar /opt/app/pichincha-api.jar
ENTRYPOINT ["java","-jar","/opt/app/pichincha-api.jar"]


#FROM openjdk:8-jdk
#MAINTAINER kleverhidalgo
# where Spring Boot will store temporary files
#VOLUME /tmp
# Refer to Maven build -> finalName
#ARG JAR_FILE=target/pichincha-api-0.0.1-SNAPSHOT.jar
# cd /opt/app
#WORKDIR /opt/app
# cp target/pichincha-api-0.0.1-SNAPSHOT.jar /opt/app/app.jar
#COPY ${JAR_FILE} /opt/app/pichincha-api.jar
#EXPOSE 8080
# java -jar /opt/app/pichincha-api.jar
#ENTRYPOINT ["java","-jar","/opt/app/pichincha-api.jar"]