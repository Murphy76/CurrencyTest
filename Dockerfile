FROM openjdk:8-jdk-alpine
RUN mkdir -p /usr/src/app/data/db
#RUN chown spring /usr/src/app/data/db
ARG JAR_FILE=target/currencyexchange-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]