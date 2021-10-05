FROM openjdk:16
EXPOSE 8080

ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]