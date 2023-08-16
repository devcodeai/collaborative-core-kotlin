# Collaborative Core API (NodeJS)
> The **Collaborative Core API** is supported with **NodeJS** and contains the following services:
> * `Company Services`
> * `Campus Services`
> * `Talent Services`
> * `Community Services`

## Table of Contents
* [Requirements](#requirements)
* [Package Dependencies](#package-dependencies)
* [Run Program](#run-program)
* [Unit Testing](#unit-testing)
* [Submission to Devcode](#submission-to-devcode)
* [Development Guide](#development-guide)
* [Project Status](#project-status)
* [Author](#author)

## Requirements
* [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* [JDK-17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* Docker
    * [On Windows](https://docs.docker.com/desktop/install/windows-install/)
    * [On Mac](https://docs.docker.com/desktop/install/mac-install/)
    * [On Linux](https://docs.docker.com/desktop/install/linux-install/)

## Package Dependencies
* Spring Boot
* Gradle
* Hibernate
* Mysql

## Run Program
* Using Local Machine (Windows)
  * Create new database (on MYSQL) as `<database_name>`
  *Update the application.properties file in src/main/resources with your database configuration:
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/[database-name]?useSSL=false
    spring.datasource.username=[your-username]
    spring.datasource.password=[your-password]
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    ``` 

  * Build the application
    
    ```
    ./gradlew clean build -x test
    ```
  * Start the program
    
    ```
    java -jar ./build/libs/core-0.0.1-SNAPSHOT.jar
    ```
  * Open the path on your local machine
    
    ```
    http://localhost:3030/api/
    ```

* Using Docker 
  * Copy `.env.example` to `.env` 
  * Build the Backend API Service docker image. If you don't specify the `<tag>`, it will be tagged as `latest` by default

    ```
    docker build -t <image_name>:<tag> .
    ```
  * Configure `docker-compose.yaml`, adjust the script below according to your built docker image

    ```
    ...
    backend-api-service: 
      image: <image_name>:<tag>
      restart: always
      ports:
        - 8080:3030
    ...
    ```
  * Run `docker-compose.yaml` file, it may take a few minutes and re-attempts. It works fine, solely wait for the `Server run on http://0.0.0.0:3030` comes out

    ```
    docker-compose -f docker-compose.yaml up
    ```
  * Open the path on your local machine
      
    ```
    http://localhost:8080/api/
    ```

## Unit Testing
* _TODO HERE_

## Submission to Devcode

### Build Docker Image

Run the following command to build a Docker image

```
docker build . -t {image_name}
```

### Push projek to Docker Hub

Make sure you have a Docker Hub account, and log in to your Docker account locally using the command `docker login`.

After that, run the following command to push the local Docker image to Docker Hub.

```
docker tag {image_name} {username docker}/{image_name}
docker push {username docker}/{image_name}
```

Next, submit the Docker image to Devcode.

```
{username docker}/{image_name}
```

## Project Status
* Project is: _in progress_

## Author
<table>
    <tr>
      <td><b>Name</b></td>
      <td><b>GitHub</b></td>
    </tr>
    <tr>
      <td>Rava Naufal Attar</td>
      <td><a href="https://github.com/sivaren">sivaren</a></td>
    </tr>
    <tr>
      <td>Suryanto Tan</td>
      <td><a href="https://github.com/SurTan02">SurTan02</a></td>
    </tr>
    <tr>
      <td>Steven Alexander Wen</td>
      <td><a href="https://github.com/loopfree">loopfree</a></td>
    </tr>
</table>
