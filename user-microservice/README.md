**updated 15/11/2015**

USER-MICROSERVICE 
=================
This micro services is in charge of managing everything related with user: profiles, authentications, sessions and searches


Version 0.0.1
-------------

Technical info
--------------
* Database engine: Mysql
* Database schema : serveme_users
* Service port: 8084


Requirements
------------
* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)

Quick start
-----------
1. `mvn clean install`
2. `cd target`
3. `java -jar user-microservice-0.0.1.jar --spring.profiles.active=[ dev | staging | live ]`
