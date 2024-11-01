FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080

USER root
RUN addgroup -S nguser && adduser -S nguser -G nguser && mkdir /logs && chown nguser:nguser /logs

USER nguser
WORKDIR /

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
