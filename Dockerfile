FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ENV MESSAGE hello_greg
COPY ${JAR_FILE} app.jar
ENTRYPOINT java -DMESSAGE=$MESSAGE -jar /app.jar
