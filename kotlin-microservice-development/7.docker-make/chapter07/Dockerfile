FROM openjdk:11-jdk

ADD build/libs/*.jar microservice.jar

ENTRYPOINT ["java", "-jar", "microservice.jar"]