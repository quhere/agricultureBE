# Spring Boot Server
To run this project, first, clone the project.
Then, create a .env file inside the src/main/resources/ directory and declare the values for the variables as follows:
MYSQL_DATABASE=<path to the database>
MYSQL_USER=<database username>
MYSQL_PASSWORD=<password>
After that, refresh the project, build it with the command:
mvn clean install
Finally, run the project with the command:
mvn spring-boot:run
