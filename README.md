# on-trust-server

Toy SNS distribution made with Spring Boot 3 framework

# Version Info

![](https://img.shields.io/badge/license-MIT-blue)
![](https://img.shields.io/badge/java-17-red)
![](https://img.shields.io/badge/spring%20boot-3.0.5-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=flat)
![JPA](https://img.shields.io/badge/JPA-Supported-4285F4?style=flat)




### Server 
![](https://img.shields.io/badge/AWS%20Shield-Standard-blue)


## install
[Download Open JDK 17](https://jdk.java.net/java-se-ri/17)
and set path **JAVA_HOME**

## Initialize Spring boot
if you start new project [Download Spring Boot packaging](https://start.spring.io/)

## gradle build
```bash
cd <project-folder path>

# gradle build
gradle clean build 

# if build with test 
gradle clean build test
```
Query DSL Q Class path is created as a result of build Project
`/generated/com/ontrustserver/domain/post/QPost.java`

## Start Project
### Set Mysql (options)
[Download MySql](https://dev.mysql.com/downloads/installer/)
```SQL
-- create scheme
create schema on_trust;

-- use schema
use on_trust;
```

### IDE setting 
[use devTools with intellij](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360003378800-How-to-configure-IDEA-for-Spring-Boot-DevTools)

### defualt setting
* port : 8000
* db
  * Default mysql port (3306)
  * schema : on_trust
  * hibernate : ddl-auto = update

### build Dockerfile
```bash
# if you use docker id deed1515 and set @latest Tag
docker build -t deed1515/on-trust-server:latest .
``` 
