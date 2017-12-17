FROM java:8
MAINTAINER david.i.cadenas@gmail.com
VOLUME /tmp
ADD user-microservice-0.0.1.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8085
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
