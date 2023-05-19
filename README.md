# on-trust-server

Toy SNS distribution made with Spring Boot 3 framework With @Latest version Library

# Version Info

![](https://img.shields.io/badge/license-MIT-blue)
![](https://img.shields.io/badge/java-17-red)
![](https://img.shields.io/badge/spring%20boot-3.0.5-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=flat)

![JPA](https://img.shields.io/badge/JPA-4285F4?style=flat)
![AspectJ](https://img.shields.io/badge/AspectJ-4285F4?style=flat)

### Server
![](https://img.shields.io/badge/AWS-yellow)

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
create schema on_trust_test;

-- use schema
use on_trust;
use on_trust_test;
```

### IDE setting
[use devTools with intellij](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360003378800-How-to-configure-IDEA-for-Spring-Boot-DevTools)

### defualt setting
* port : 8000
* db
  * Default mysql port (3306)
  * schema : on_trust
  * hibernate : ddl-auto = update



### Production Package Structure

```textmate
src
├── main
│   ├── java
│   │   └── com
│   │       └── ontrustserver
│   │           └── guide
│   │               ├── (C)OnTrustServerApplication : Conatain psvm
│   │               ├── domain
│   │               │   ├── post : Post Domain Package
│   │               │   │   ├── controller : @RestController (C)
│   │               │   │   ├── serice : @Service (C)
│   │               │   │   ├── dao : extends JpaRepository with Query Dsl (I)(C) 
│   │               │   │   ├── dto : response / request (R)
│   │               │   │   ├── util : utils belonging to Domain (C)
│   │               │   │   └── exception : Exception belonging to Domain (C)
│   │               │   └── model : Entity / Entity Builder (C)(R)
│   │               ├── global
│   │               │   ├── aspect : Cross-cutting Aspect
│   │               │   │   ├── badword : abuse text filter Domain
│   │               │   │   │   ├── domain :Domain with Inner Enum (I)(C->e)
│   │               │   │   │   ├── exception : BadWord Exception Domain (C)
│   │               │   │   │   ├── (I)BadwordInterface : AOP Annotation @Badword
│   │               │   │   │   ├── (C)BadWordCheckAspect : @Aspect
│   │               │   │   │   └── exception : Exception belonging to Domain (C)
│   │               │   │   ├── global : global Aspect
│   │               │   │   │   ├── domain :Global Aspect Domain
│   │               │   │   │   └── (C)RunningTimeAspect : Global @Aspect
│   │               │   ├── common
│   │               │   │   ├── request : Global Request (R)
│   │               │   │   └── response : Global Response (R)
│   │               │   ├── config
│   │               │   │   ├── (C)AspectConfig : Contain Bean
│   │               │   │   └── (C)QueryDslConfig : @PersistenceContext
│   │               │   ├── error : @ControllerAdvice Class whit Global Error massage (C)(R) 
│   │               │   └── util
│   └── resources
└──       └── application.yaml
``` 

### build Dockerfile
To use Docker, you first need to install Docker.
https://www.docker.com/get-started
```bash
# if you use docker id deed1515 and set @latest Tag
docker build -t deed1515/on-trust-server:latest .
``` 

```dockerfile
FROM gradle:7.6.0-jdk17-alpine AS builder

RUN mkdir /app
WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .
COPY src src

RUN apk update && apk add findutils
RUN ./gradlew clean build --no-daemon

FROM openjdk:17.0-jdk
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8000
CMD ["java", "-jar", "app.jar"]

```