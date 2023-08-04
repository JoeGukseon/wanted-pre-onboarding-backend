FROM openjdk:11-jdk

COPY src/main/resources/application.yml /application.yml

ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} /app.jar

COPY scripts/server-start.sh /server-start.sh

RUN chmod +x /server-start.sh

ENTRYPOINT ["/server-start.sh"]