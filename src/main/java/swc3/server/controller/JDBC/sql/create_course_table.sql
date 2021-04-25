CREATE TABLE courses (
                        course_id int NOT NULL AUTO_INCREMENT,
                        title varchar(80) NOT NULL,         -- Course Title
                        description varchar(500) NOT NULL,  -- Course Description
                        link varchar(255) NOT NULL,         -- Course landing page link
                        PRIMARY KEY (`course_id`)
);

INSERT INTO courses(title, description, link) values ('Vue.js for Beginners: Up and Running with Vue','A beginner''s guide to learn the basics of the JavaScript Framework Vue.js.','https://www.udemy.com/course/vue-intro/?referralCode=E9DECFF78CA706D7A68A');
INSERT INTO courses(title, description, link) values ('The Complete Apache Groovy Developer Course','Everything you need to know to get started with the Groovy Programming Language','https://courses.danvega.dev/p/the-complete-apache-groovy-developer-course');
INSERT INTO courses(title, description, link) values ('Getting Started with Spring Boot 2','Learn how to build a real application using Spring Framework 5 & Spring Boot 2','https://courses.danvega.dev/p/spring-boot-2');
INSERT INTO courses(title, description, link) values ('Learn Spring Boot','Spring Boot gives you all the power of the Spring Framework without all of the complexity.','https://courses.danvega.dev/p/spring-boot-intro');
INSERT INTO courses(title, description, link) values ('Angular 4 Java Developers','Learn how to build Spring Boot & Angular apps by building a real world application with JHipster.','https://courses.danvega.dev/p/jhipster');
