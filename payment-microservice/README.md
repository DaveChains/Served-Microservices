PAYMENT-SERVICE 
==================
This micro services is in charge of managing everything related with payments: stripe


Version 1.0
-----------

Technical info
--------------
* Database engine: TBD
* Service port: 8088

Requirements
------------
* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)

Quick start
-----------
1. `mvn clean install`
2. `cd target`
3. `java -jar payment-service-1.0.jar --spring.profiles.active=[ dev | staging | live ]`
