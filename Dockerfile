FROM openjdk:16
EXPOSE 8080
COPY  spring-boot-docker.jar
ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]