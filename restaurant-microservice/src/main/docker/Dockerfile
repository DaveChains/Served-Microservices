FROM java:8
MAINTAINER david.i.cadenas@gmail.com
VOLUME /tmp
ADD restaurant-microservice-0.0.1.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8088
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
