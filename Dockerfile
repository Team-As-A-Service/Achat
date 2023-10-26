FROM openjdk:8-jre-alpine
EXPOSE 8080
ARG JAR_FILE=target/achat-1.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]