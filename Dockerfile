FROM openjdk:16
EXPOSE 8080
ADD build/libs/*.jar spring-boot-docker.jar
ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]