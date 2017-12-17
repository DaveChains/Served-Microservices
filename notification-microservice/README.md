**updated 9/12/2015**

USER-SERVICE 
==================
This micro services is in charge of managing everything related with notifications


Version 1.0
-----------

Technical info
--------------
* Service port: 8087
* Database engine: MongoDB


Requirements
------------
* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)
* [MongoDB 3.x](https://www.mongodb.org/)

Quick start
-----------
1. `mvn clean install`
2. `cd target`
3. `java -jar order-service-1.0.jar --spring.profiles.active=[ dev | staging | live ]`
