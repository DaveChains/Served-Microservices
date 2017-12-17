FROM java:8
MAINTAINER david.i.cadenas@gmail.com
VOLUME /tmp
ADD order-microservice-0.0.1.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8089
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
