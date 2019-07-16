# EV Charging Sessions Store
This project is intended to manage EV charging session entities using in-memory data structures.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

```
Java version: 11.0.3 (openjdk version "11.0.3" 2019-04-16)
```
Please refer to: https://openjdk.java.net/install/

### Install Maven

#### Apache Maven 3.3.9 or higher (execute command below from project root folder)
##### Linux
```
./mvnw -version
```
##### Windows
```
mvnw.bat -version
```

### Test project
#### Linux
```
./mvnw test
```
#### Windows
```
mvnw.bat test
```
### Run project
#### Run Web Server
##### Linux (Change to -Dspring.profiles.active=dev when you want to see DEBUG log level)
```
./mvnw clean package && java -jar -Dspring.profiles.active=prod target/ev-box-challenge-0.0.1.jar
```
##### Windows (Change to -Dspring.profiles.active=dev when you want to see DEBUG log level)
```
mvnw.bat clean package 
mvnw.bat java -jar -Dspring.profiles.active=prod target/ev-box-challenge-0.0.1.jar
```
#### Run Web Server in Docker version 18.09.7 (or higher)
Generate JAR file on Linux
```
./mvnw clean install
```
Generate JAR file on Windows
```
mvnw.bat clean install
```
Build image
```
docker build --build-arg JAR_FILE=target/*.jar -t ev-box/challenge .
```
Run container (xxxx:8080) --> Change xxxx to run web server in an alternative port
```
docker run -p 8080:8080 ev-box/challenge
```

## Test case results for EV Charging Sessions project
```
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 6.672 s
[INFO] Finished at: 2019-07-16T19:27:53-03:00
[INFO] Final Memory: 21M/77M
[INFO] ------------------------------------------------------------------------
```

## Features available in 0.0.1 version
### Submit new charging session to the store
* POST /chargingSessions
##### Request body
```
{
	"startedAt": "2019-07-16T20:51:40.560"
}
```
##### Response body
```
{
    "id": "fe147d77-5e3b-48b0-8985-776f697a2194",
    "startedAt": "2019-07-16T20:51:40.560",
    "status": "STARTED"
}
```
### Suspend charging session
* PUT /chargingSessions/{id}
##### Request body
```
{
	"suspendedAt": "2019-07-16T21:58:40.560"
}
```
##### Response body
```
{
    "id": "6ec833b3-a7c5-47b5-8780-3410aa4ae763",
    "startedAt": "2019-07-16T19:51:40.560",
    "suspendedAt": "2019-07-16T21:58:40.560",
    "status": "SUSPENDED"
}
```
### Retrieves a specific charging session from the store
* GET /chargingSessions/{id}
##### Response body
```
{
    "id": "adf60dcd-d3a0-4809-ae97-1093bcc070d2",
    "startedAt": "2019-07-16T20:51:40.560",
    "suspendedAt": "2019-07-16T21:58:40.560",
    "status": "SUSPENDED"
}
```
### Returns the number of charging sessions that were started / suspended in the previous minute
* GET /chargingSummary
##### Response body
```
{
    "startedCount": 1,
    "suspendedCount": 2
}
```
### (Experimental, this is present only for testing purposes and should be removed in production if not tested) Returns all charging sessions
* GET /chargingSessions
```
[
    {
        "id": "7f19fb94-ce20-4ca0-9adf-5192e312ac38",
        "startedAt": "2019-07-16T21:51:40.560",
        "suspendedAt": null,
        "status": "STARTED"
    },
    {
        "id": "19295ba3-433f-4132-b36c-f766d8c351d0",
        "startedAt": "2019-07-16T19:51:40.560",
        "suspendedAt": "2019-07-16T21:58:40.560",
        "status": "SUSPENDED"
    }
]
```

## FAQ
### Why is there a resource called GET /chargingSessions?
* It's an experimental feature commonly used for development purposes, use cases like this should be designed with pagination in order to prevent excessive memory usage

### How can I test different inputs to the endpoints available in this application?
* Please download [Postman](https://www.getpostman.com/downloads/) and import the collection in the root folder of this project

## Built With

* [Maven](https://maven.apache.org/) - Apache Maven is a software project management and comprehension tool.
* [OpenJDK](https://openjdk.java.net/) - The place to collaborate on an open-source implementation of the Java Platform, Standard Edition, and related projects.
* [Spring Boot](https://spring.io/projects/spring-boot) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

## Authors
* **Nataniel Carvalho** - (https://github.com/natanielmendes)