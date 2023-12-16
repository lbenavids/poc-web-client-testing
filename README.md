# Web Client Testing Demo

This project serves as an example project for developing web services using WebClient and conducting unit tests with MockWebServer.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

1. Java 11 installed on your local development machine
2. IDEA IntelliJ for code editing and running the app
3. Maven for dependencies and running the build

### Installing

To clone and run this application, you’ll need Git and Maven installed on your computer. From your command line:
## How to run the service locally

After cloning the project and installing the dependencies, you can run the application by the following command in terminal:

```bash

mvn spring-boot:run

```

then you can run this curl command to test the service:

```bash

curl http://localhost:8080/ability/1

```


## Testing

We use MockWebServer for unit testing. To run tests, in the terminal run:


```bash

mvn test
```

## Code Explanation for Beginners

### Service Using WebClient

In simple terms, WebClient is used for creating non-blocking, reactive web applications. It's used to handle HTTP requests and responses with less resources, providing better scalability. In our project, it is used to create the service to handle requests.

### Unit Testing with MockWebServer

MockWebServer is a scriptable web server for testing HTTP clients. It allows you to test your service in a controlled environment. In general, it helps to simulate the HTTP backend for our service to test against.


## Authors

Add list of [contributors](contributors.md) who participated in this project.
