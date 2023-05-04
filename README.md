# Musala Soft Practical Task

### Prerequisites

This Practical task was builded using the following technologies

* Java SDK 17
* Spring Boot 3.0.6
* Gradle 7.6.1

Having the JDK 17 is mandatory to be able to build and run the App.

### Running

how to build and run the app:

* Clone the repository
* Navigate to the root directory of the project
* Execute the command `./gradlew clean build`
* The JAR file will be located in the `./build/libs` folder. There will be two `.jar` files:
    * `drones-0.0.1.jar` stand alone `jar` with all dependencies included.
    * `drones-0.0.1-plain.jar` skim `jar` without dependencies, used as external dependency.
* To run the JAR, use the following command in a console `java -jar .\drones-0.0.1.jar`.

### Usage

* The app will deploy by default in the `port:8080` so please make sure to have it free or change it
  before compilation inside the `application.yml` config file.
* Also, the app will deploy with the context path `/musala`.
* The app has `SwaggerUI` configured for testing you can access the swagger in the
  route: http://localhost:8080/musala/swagger-ui/index.html 
  * be aware of the port in case you change it in the steps above.
* The App has an in memory Data base with some example data already loaded.
  * The example data can be found in the `data.sql` file in the path: `./src/main/resources`
  * Please be aware that any changes you make will be reset if you redeploy the app.

