# Assignment
Assignment for importing a CSV and performing operations on users data.

# Requirements
- Java - 11
- Maven - 3.6.3 or later

# Database
It uses a mySQL database to store CSV user records. It is configured in the application.properties
```yaml
spring.datasource.url= jdbc:mysql://localhost:3306/db?useSSL=false
```

# Steps to Setup
 - ### Clone the application

       git clone https://github.com/yanepc88/assignment.git
 - ### Create Mysql database

       create database db
 - ### Change mysql username and password as per your installation

       open src/main/resources/application.properties

       change spring.datasource.username and spring.datasource.password as per your mysql installation

 - ### Build and run the app using maven

       mvn package
       java -jar target/assignment-0.0.1-SNAPSHOT.jar
       Alternatively, you can run the app without packaging it using -

       mvn spring-boot:run
       The app will start running at http://localhost:8082.

# Explore Rest APIs
The app defines following APIs.

POST /api/users/import

GET /api/users?id=*someId*

GET /api/users/filter

You can test them using postman or any other rest client.
