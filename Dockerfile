FROM openjdk:21-oracle
EXPOSE 8080

WORKDIR /api

COPY target/*.jar todo-api.jar

COPY script.sh /usr/local/bin/script.sh
RUN chmod +x /usr/local/bin/script.sh

CMD /usr/local/bin/script.sh

