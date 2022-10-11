FROM openjdk:11-jre-slim-stretch
ARG JAR_FILE=build/libs/simple-poll-v1.war
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]