# Spring Boot Web Server

Implemented: RestController + Model + Repository

The self-contained dump file for the database is in sql folder:
swc3_springboot_dump.sql
Create that database and make sure it is used as a data source by the web server.

###Features:
- Implemented REST APIs for http communication like GET, POST, PUT, DELETE - this is for client-side rendering.
- Testing: Integration tests for the database (done directly on the production database)
- Custom Exception Handler
- Pagination
- Filtering
- Sorting
- Thymeleaf server-side rendering: in TutorialControllerForThymeleaf
- spring-boot-starter-data-rest to automatically generate the REST APIs: 
    - https://spring.io/guides/gs/accessing-data-rest/
- Spring security: 
    - configuration: in the file WebSecurityConfig
    - Authentication: registration / login
    - Authorization: APIs are accessible to different roles - example: @PreAuthorize("hasRole('ADMIN')")
    - https://spring.io/guides/topicals/spring-security-architecture/
    - https://spring.io/guides/gs/securing-web/ 
- github CI action: .github/workflows/maven.yml, this allows us to create a continuous integration,
after pushing to gitHub, the CI action is executed (tests), if successful, the app will be automatically deployed to heroku cloud:
    - https://docs.github.com/en/actions/guides/building-and-testing-java-with-maven
CORS:


### local database server time zone error:
If you get an error because of the timezone, run the following command in MySQL Workbench:
- SET @@global.time_zone = '+00:00';

### some of the endpoints:
- http://localhost:5557/api/tutorials
- http://localhost:5557/api4/tutorials-all-sorted?sort=id,desc&sort=title,asc
- http://localhost:5557/thymeleaf/tutorialsAdvanced
- http://localhost:5557/api/orders
- http://localhost:5557/api/ordersWithIDs
etc...

### http requests:
httpRequests.http file contains some http requests:
- registration: POST http://localhost:5557/api/auth/signup, provide username, password, email
- login (getting JWT), provide username, password
- authorized request (using acquired JWT)
- tests

![image](SSR.png)

<img src="pics/1.jpg"
     alt="Markdown Monster icon"
     style="float: left; margin-right: 10px;" />



[image_ref_a32ff4ads]: data:pics/1.jpg;

![][image_ref_a32ff4ads]