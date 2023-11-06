FROM openjdk:8-jre-alpine
ARG VERSION
EXPOSE 8080
ARG JAR_FILE=target/achat-*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]