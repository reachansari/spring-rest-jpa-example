# Spring Data REST and JPA Example
This project depicts the Spring Boot Example with Spring Data REST and JPA Example

## Description
This Project shows the list of Employees which are stored in the In-Memory H2 Database. 

Using the following endpoints, different operations can be achieved:

- POST -> `http://localhost:8080/employees/load` - Add new employees using the Employees model. 
`Eg:{ "name": "Virat", "teamName": "Testing","email":"v@gmail.com","mobile":"987654321","salary": 100 }`


- GET -> `http://localhost:8080/employees/all` - This returns the list of employees in the Employee table which is created in H2

- GET -> `http://localhost:8080/employees/{name}` - This returns the details of the employee passed in URL

## Libraries used
- Spring Boot
- Spring Configuration
- Spring REST Controller
- Spring JPA
- H2
- Development Tools

## Junit
- This app has junit for the 3 endpoints and also a negative test case

## Compilation Command
- `mvn clean install` - Plain maven clean and install
