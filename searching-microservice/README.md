SEARCHING-ENGINE-SERVICE
=================================
Searching Engine Service(SES)

Requirements
------------
* [Java Platform (JDK) 7](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)

Quick start
-----------
1. `mvn clean install`
2. `cd target`
3. `java -jar searching-engine-service-1.0 --spring.profiles.active=[ dev | staging | live ]`