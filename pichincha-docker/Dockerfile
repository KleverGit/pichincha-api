FROM openjdk:8-jdk
VOLUME /tmp
EXPOSE 8080
ADD ./target/pichincha-api-0.0.1-SNAPSHOT.jar pichincha-api.jar
ENTRYPOINT ["java","-jar","/pichincha-api.jar"]