FROM openjdk:11-jdk-slim 
VOLUME /tmp 
COPY target/*.jar PhotoAppApiUsers.jar 
ENTRYPOINT ["java","-jar","PhotoAppApiUsers.jar"]