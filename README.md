# Pichincha - API

Este proyecto fue desarrollado en el lenguaje JAVA 8 sobre el framwork Springboot y maven 3.x.

## Development server

Correr local apache dentro de springboot. Navigate to `http://localhost:8080/`. 

## Production server

Este ejemplo se encuentra desplegado sobre kubernetes en GCloud.
Se encuentra desplegado los siguientes endpoints sobre un servicio LoadBalancer

    - /auth/login 

curl --location --request POST 'http://35.232.86.222/auth/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username" : "admin",
    "password" : "123"
}'

    - /DevOps 

curl --location --request POST 'http://35.232.86.222/DevOps' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6IltdIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE1OTkxNjE0NTAsImV4cCI6MTU5OTE2NTA1MH0.E2fuKskKdJtSNJEUaC6fGG_giny9K9LJeyvaUNtpYx0' \
--header 'JSESSIONID: 7ba6f6d6-54c0-4c60-9c1f-ce9735a772a6' \
--header 'Content-Type: application/json' \
--data-raw '{
    "message": "This is a test",
    "to": "Juan Perez",
    "from": "Rita Asturia",
    "timeToLifeSec": 45
}'

## Test

Los test fueron desarrollados sobre mock java.
Se puede correr `mvn test`. Por ejemplo, o cada test por separado


## JAVA - SPRINGBOOT

Para el desarrollo de la api en Springboot se realiza lo siguiente:
    
    - Se desarrolla endpoint con JWT sobre la API KEY de spring security 
    - Sobre este endpoint se aplican filtros de authenticacion, por ejemplo (path : /auth/login) , (successLogin) , (unsiccess login)
    - Desarrollo de endpoint /Devops

## CIRCLECI

Para el ejemplo se desarrollo sobre la herramienta de CI CircleCI.
En circle CI se encuentra la configuracion para la integracion continua y despliegue continuo

        - Se desarrolla workflow de los jobs que seran ejecutados.
        - Se desarrolla los jobs build y deploy 
        - En los jobs se detalla el proceso de compilacion del Dockerfile

## DOCKER - KUBERNETES - GCLOUD

Para el ejemplo  de implemento lo siguiente: 

    - Se crea un cluster en gcloud con tres nodos
    - En el proyecto se crea Dockerfile para build de la imagen 
    - La imagen construida se sube a la nube de gloud
    - Se crea el yaml de kubernetes para un servicio LoadBalancer y un Deployment de la imagen
    - Se encuentran desplegadas 3 replicas del POD
 