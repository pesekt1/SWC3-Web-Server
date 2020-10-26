# Spring Boot Web Server

Implemented: RestController + Model + Repository

The self contained dump file for the database is in sql folder:
swc3_springboot_dump.sql
Create that database and make sure it is used as a data source by the web server.

Features:
- Implemented REST aAPIs for http communication like GET, POST, PUT, DELETE .. this is for client-side rendering
- Testing: Integration tests for the databse
- Custom Exception Handler
- Pagination
- Filtering
- Sorting
- Thymeleaf server-side rendering: in TutorialControllerForThymeleaf

If you get an error because of the timezone, run the following command in MySQL Workbench:
SET @@global.time_zone = '+00:00';
