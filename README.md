- [Spring Boot Web Server](#spring-boot-web-server)
  * [App structure](#app-structure)
    + [Features](#features)
    + [Model Classes (Domain):](#model-classes--domain--)
    + [Repository pattern](#repository-pattern)
    + [application.properties](#applicationproperties)
    + [Profiles](#profiles)
    + [Logging](#logging)
    + [Lombok](#lombok)
  * [Databases](#databases)
    + [Database (MySQL)](#database--mysql-)
    + [local database server time zone error](#local-database-server-time-zone-error)
    + [Multiple data sources](#multiple-data-sources)
    + [Working with MongoDB - Document database](#working-with-mongodb---document-database)
    + [Working with SQLite](#working-with-sqlite)
    + [Changing the data source](#changing-the-data-source)
    + [Spring Data](#spring-data)
  * [APIs](#apis)
    + [Thymeleaf](#thymeleaf)
    + [Rest Controllers: REST APIs (Endpoints providing data in JSON format)](#rest-controllers--rest-apis--endpoints-providing-data-in-json-format-)
    + [Service Layer](#service-layer)
    + [CORS](#cors)
    + [spring-boot-starter-data-rest](#spring-boot-starter-data-rest)
    + [Rest API documentation](#rest-api-documentation)
  * [Maven](#maven)
    + [Maven project:](#maven-project-)
    + [Configuration](#configuration)
    + [Maven Profiles](#maven-profiles)
  * [Security](#security)
    + [Spring security](#spring-security)
    + [JDBC example - db connection without the ORM, just using POJOs](#jdbc-example---db-connection-without-the-orm--just-using-pojos)
    + [SQL injection](#sql-injection)
  * [Http communication](#http-communication)
    + [HttpClient](#httpclient)
    + [Json-server](#json-server)
    + [Http requests](#http-requests)
  * [Testing](#testing)
    + [Integration tests](#integration-tests)
    + [Unit tests](#unit-tests)
    + [API tests](#api-tests)
    + [gitHub CI action](#github-ci-action)
  * [Cloud Deployment](#cloud-deployment)
    + [Git and gitHub](#git-and-github)
    + [Deployment to Heroku Cloud](#deployment-to-heroku-cloud)
    + [system.properties](#systemproperties)
    + [Docker](#docker)
  * [Web client app](#web-client-app)
  * [DAO - Data Access Object](#dao---data-access-object)
  * [Multiple implementation classes](#multiple-implementation-classes)
  * [JDBC - Java Database Connectivity](#jdbc---java-database-connectivity)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>


# Spring Boot Web Server

## Versions and Technologies

- Spring Boot 2
- Spring Framework 5
- Java 11

## App structure
![app structure](src/main/resources/static/appStructure.png)

### Features
- REST APIs for http communication (for client-side rendering) - GET, POST, PUT, DELETE.
- Thymeleaf engine to generate html pages 
- Custom Exception Handler
- Pagination, Filtering, Sorting
- spring-boot-starter-data-rest to automatically generate the REST APIs
- Multiple data sources: MySQL, Postgres, Azure SQL Database, MongoDB
- Security - registration / login, Showing SQL injection
- Using ORM mapping tool (Hibernate) to map the tables in the database by java classes.
- Using JDBC driver to communicate with the database directly - without ORM.

### Model Classes (Domain):
- Using javax.persistence, model classes were generated from the existing database)
![Import mapping](src/main/resources/static/importMapping.png)

- Example of a model class mapping tutorials table:
- There are different ways to auto-generate the id by setting the GenerationType.
```java
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "tutorials", schema = "swc3_springboot")
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", nullable = false)
    private long id;

    @Basic@Column(name = "description")
    private String description;

    @Basic@Column(name = "published")
    private Boolean published;

    @Basic@Column(name = "title", nullable = false)
    private String title;

    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
```

- using annotation @JsonBackReference to avoid a JSON loops. 
- Example: This collection of orders will be omitted in the JSON response.

```java
    //part of the model class
    @JsonBackReference
    @OneToMany(mappedBy = "customerByCustomerId")
    public Collection<Order> getOrdersByCustomerId() {
        return ordersByCustomerId;
    }
```

### Repository pattern
- Dependency: spring-boot-starter-data-jpa: [JpaRepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
- Example: Implementation class is plugged-in by Spring Data JPA automatically - we can call the methods directly on the interface.
- We can also use native queries - note that this goes against the original idea of the interfaces which says that they should not contain the method implementation.

```java
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
	//The implementation is plugged in by Spring Data JPA automatically.
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);

	//native query
	@Query(value = "SELECT * FROM tutorials t WHERE t.id = ?1", nativeQuery = true)
	Tutorial findTutorialById(long id);
}
```

Now we have access to data from tutorials table which is mapped by Tutorial class. 
JpaRepository works for relational databases - the mapping class should have @Entity annotation.


### application.properties
- using environment variables: ${ENV_VARIABLE}
- Now for the development we set the DATABASE_URL for our local db server.
- For the production in the cloud, we set the DATABASE_URL to the cloud db resource.
- When we deploy, the production app in the cloud will use the cloud db resource.

```java
    server.port=5557
    spring.datasource.url=${DATABASE_URL}
    spring.datasource.hikari.maximum-pool-size=3
    spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect
```

![Environment Variables](src/main/resources/static/env.png)

### Profiles
- Profiles are defined by Maven (pom.xml):
```xml     
    <profiles>
            <profile>
                <id>dev</id>...
```

- Profiles are accessed by the application.properties using naming conventions:

![Profiles](src/main/resources/static/profiles.png)
- Like this we get dev, prod, test profiles.
- The active profile is checked by the default application.properties:
```java
     spring.profiles.active=@spring.profiles.active@
```

- We can work with the dev profile and push to the gitHub with prod profile - done via .github/workflows/maven.yml:
```yaml
    run: 
        - mvn -B package -P prod --file pom.xml 
```

### Logging
- in application.properties:
```java   
    logging.level.org.springframework = INFO
    logging.level.sql = debug (we will see all the sql queries in the console)
```

### Lombok
- dependency: lombok [lombok maven](https://projectlombok.org/setup/maven)
- We can use annotations like these:
```java
    @EqualsAndHashCode
    @Setter
    @Getter
    @NoArgsConstructor
```
Our code will be cleaner - We do not see getters, setters etc in the code.


## Databases

### Database (MySQL)
- Maven Dependencies:
    - spring-boot-starter-data-jpa
    - mysql-connector-java 
- The self-contained dump file for the database is in sql folder: swc3_springboot_dump.sql
- Create that database and make sure it is used as a data source by the web server - via application.properties.
- Generate the model classes as shown above.
- Database EER:
![database EER](src/main/resources/static/databaseEER.png)

### local database server time zone error
If you get an error because of the timezone, run the following command in MySQL Workbench:
```mysql
    SET @@global.time_zone = '+00:00';
```

### Multiple data sources

Adding different database technologies as data sources to our project.
- First we need to connect to the database servers from IntelliJ:

    ![data sources](src/main/resources/static/dataSources.png)

- Set up the connection settings:

    ![connection settings](src/main/resources/static/connectionSettings.png)

- Create the configurations for persistence units for each datasource:

    ![db config](src/main/resources/static/dbConfig.png)

Example: Primary data source uses @Primary annotations:
```java
@Primary
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = {"swc3.server.PrimaryDatasource.repository", "swc3.server.PrimaryDatasource.nativequeries"})
public class Db1Config {

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "db1.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("dataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("swc3.server.PrimaryDatasource.models", "swc3.server.PrimaryDatasource.nativequeries").persistenceUnit("db1")
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
```

- From this configuration we will get persistence units:

    ![persistence](src/main/resources/static/persistence.png)

- Assign data sources to the persistence units:

    ![assign data sources](src/main/resources/static/assignDataSources.png)

Now our application is working with multiple data sources, we can create services and rest controllers for each data source.

### Working with MongoDB - Document database

```java
############ MongoDB data source ###############
spring.data.mongodb.uri=mongodb://localhost:27017/swc3_db
```

- Model: @Document(collection = "tutorials") - Assign to the data source

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "tutorials")
public class Tutorial_mongo {

    @Id
    private String id;
    private String title;
    private String description;
    private boolean published;

    public Tutorial_mongo(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
```

- Repository: MongoRepository

```java
import org.springframework.data.mongodb.repository.MongoRepository;
import swc3.server.Datasources.Db5.models.TutorialMongo;

import java.util.List;

public interface TutorialRepository_mongo extends MongoRepository<Tutorial_mongo, String> {
    List<Tutorial_mongo> findByPublished(boolean published);
    List<Tutorial_mongo> findByTitleContaining(String title);
}
```

- Controller: The same as the other controllers.

### Working with SQLite

- SQLite is a relational, self-contained, serverless, zero-configuration, transactional database.
- There is no database server - just a file containing the database.
- Hibernate does not come with the dialect for SQLite, so we need to do some extra configuration.
    - SQLiteDialect.java:
    
```java
import org.hibernate.dialect.Dialect;
import java.sql.Types;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        registerColumnType(Types.BIT, "integer");
        registerColumnType(Types.TINYINT, "tinyint");
        registerColumnType(Types.SMALLINT, "smallint");
        ...
```

- application.properties: We refer to our custom dialect class:

```java
db6.datasource.jdbc-url= ${DB6_URL}
#hibernate dialect must be set up programmatically because there is none for sqlite
db6.jpa.properties.hibernate.dialect= swc3.server.Datasources.Db6.Dialect.SQLiteDialect
```

- Path to the database file:
    - In our case the path from the project root folder to the database file is sql/sqlite.
    - Connection string using the relative path looks like this: jdbc:sqlite:sql/sqlite/swc3_sqlite.db
    - We will use the same string for the production - because there is no production database, we will use the same db file.



### Changing the data source
- change the connection string in application.properties
- change the Hibernate dialect: [hibernate.dialect](https://docs.jboss.org/hibernate/orm/5.2/javadocs/org/hibernate/dialect/package-summary.html)
```java
    spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect
    spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.SQLServerDialect
    spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQL10Dialect
```

### Spring Data
- [Spring Data docs](https://spring.io/projects/spring-data)
- Spring Data provide models for different data stores:
    - Spring Data JPA: Relational databases
    - Spring Data MongoDB: MongoDB document database
    - Spring Data Neo4j - Neo4j graph database
    - Spring Data Redis - Redis key-value database

- Spring data uses the repository pattern: - repository + model.
- Examples:

```java
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {...

@Entity
@Table(name = "tutorials")
public class Tutorial {
    @Id@GeneratedValue(...)
    private long id;
    ...

public interface TutorialRepository extends MongoRepository<Tutorial, String> {...

@Document(collection = "tutorials")
public class Tutorial {
    @Id
    private String id;
    ...

public interface MovieRepositoryNeo4j extends Neo4jRepository<Movie, String> {...

@Node
public class Movie {
    @Id
    private final String title;
    @Property("tagline")
    private final String description;
    @Relationship(type = "ACTED_IN", direction = Direction.INCOMING)
    private List<Actor> actors = new ArrayList<>();
    ...

public interface ProductRepository extends CrudRepository<Product, String> {}

@RedisHash("products") productRepository.findAll()
public class Product {
@Id
private String id;
private String description;
private BigDecimal price;
private String imageUrl;
```

## Hibernate
At its core, Hibernate is an object-relational mapping tool that provides an implementation of JPA (Java Persistence API).

### @Id
Three different strategies for generation an Id:

- IDENTITY
- SEQUENCE
- TABLE
- AUTO

This will pick TABLE:
```java
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
```

This will pick IDENTITY:
```java
@Id
@GeneratedValue(strategy= GenerationType.AUTO, generator="native")
@GenericGenerator(name = "native", strategy = "native")
```




## APIs

### Thymeleaf 
- [docs](https://www.thymeleaf.org/)
- server-side template engine: in TutorialControllerForThymeleaf
- multi-page web application, the web app sends the whole html page as a response.
- This is different from the Rest Controllers which send data in JSON format to the client.

![page structure](src/main/resources/static/MPA.png)

- Controllers - Responsible for sending an html page as a response:
- Example: Returning listAdvanced.html with the data from tutorial table (using the repository)

```java
@Controller
@RequestMapping("/thymeleaf")
public class TutorialControllerForThymeleaf {
    //...
    //html - advanced web page with update and delete
    @RequestMapping("/tutorialsAdvanced")
    public String getTutorialsAdvanced(Model model){
        model.addAttribute("tutorials", tutorialRepository.findAll());
        return "tutorials/listAdvanced";
    }
```

listAdvanced.html: we can access the data provided by the controller:
```html
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Title</th>
        <th scope="col">Description</th>
        <th scope="col">Published</th>
        <th scope="col">Update</th>
        <th scope="col">Delete</th>
        <th scope="col">Like</th>
        <th scope="col">Photo</th>
    </tr>
    </thead>
    <tbody>
    <tr th:each = "tutorial : ${tutorials}">
        <td th:text = "${tutorial.title}"></td>
        <td th:text = "${tutorial.description}"></td>
        <td th:text="${tutorial.published} ? 'yes' : 'no'"></td>
        <td><a th:href="@{'/thymeleaf/' + ${tutorial.id} + '/update'}">Update</a></td>
        <td><a th:href="@{/thymeleaf/delete/{id}(id=${tutorial.id})}">Delete</a></td>
```

- Result:

![Thymeleaf](src/main/resources/static/thymeleaf.png)

### Rest Controllers: REST APIs (Endpoints providing data in JSON format)
- <http://localhost:5557/api/tutorials>
- <http://localhost:5557/api4/tutorials-all-sorted?sort=id,desc&sort=title,asc>
- <http://localhost:5557/thymeleaf/tutorialsAdvanced>
- <http://localhost:5557/api/orders>
- <http://localhost:5557/api/ordersWithIDs>
- etc...

- Example: endpoint at .../api3/tutorials
    - Using a service layer to have clean Rest Controllers without the data source dependency.
    - Rest Controllers use ResponseEntity class.
    - The service is injected via constructor.
```java
@RestController
@RequestMapping("/api3")
public class TutorialController {
	TutorialService tutorialService;

	@Autowired
	public TutorialController(TutorialService tutorialService){
		this.tutorialService = tutorialService;
	}

	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
		return tutorialService.getAllTutorials(title);
	}
```

### Service Layer
- Business logic, working with the repository pattern - constructor dependency injection.
- Example: Notice that we can call the methods directly on the interface - the implementation class is generated automatically.

```java
@Service
public class TutorialService {
    TutorialRepository tutorialRepository;

    @Autowired
    public TutorialService(TutorialRepository tutorialRepository){
        this.tutorialRepository = tutorialRepository;
    }

    public ResponseEntity<List<Tutorial>> getAllTutorials(String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if (title == null)
            tutorials.addAll(tutorialRepository.findAll());
        else
            tutorials.addAll(tutorialRepository.findByTitleContaining(title));

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }
```

- Http response in JSON format (Response body):
```json
[
  {
    "id": 1011,
    "description": "d1",
    "published": false,
    "title": "t1"
  },
  {
    "id": 1012,
    "description": "d2",
    "published": false,
    "title": "t2"
  }
]
```

### CORS
- configured in the file WebConfig: [Spring CORS](https://spring.io/guides/gs/rest-service-cors/)
    - allowedMethods 
    - allowedOrigins
    - allowCredentials
    - maxAge
    - allowedHeaders
    - ExposedHeaders

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8081",
                        "http://localhost:5000",
                        "https://swc3-react-frontend.herokuapp.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //.allowedHeaders("header1", "header2", "header3")
                //.exposedHeaders("header1", "header2")
                .allowCredentials(true)
                .maxAge(3600); //1 hour
    }
```

### spring-boot-starter-data-rest
- <https://spring.io/guides/gs/accessing-data-rest/>
- [Tutorial](https://www.baeldung.com/spring-data-rest-intro)
- Spring Data REST takes the features of Spring [HATEOAS](https://spring.io/projects/spring-hateoas) and Spring Data JPA and automatically combines them together.
- We get autogenerated endpoints:

```json
{
  "_links": {
    "products": {
      "href": "http://localhost:5557/products{?page,size,sort}",
      "templated": true
    },
    "orderItems": {
      "href": "http://localhost:5557/orderItems{?page,size,sort}",
      "templated": true
    },
```

- When Spring Data REST detects (by classpath scanning) interfaces/classes that extend/implement 
[Repository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/Repository.html) 
it will assume that those classes should be served as RESTful resources. 
- RestRepository allows you to customize this behavior by changing the endpoint 
or not serving the resource at all:

```java
@RepositoryRestResource(exported = true, path = "tutorialsCustom")
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
```

### Rest API documentation
- [springdoc](https://springdoc.org)
- Dependencies:
    - springdoc-openapi-data-rest
    - springdoc-openapi-ui

In application properties:
```java
    springdoc.swagger-ui.path=/swagger-ui-custom.html
    springdoc.swagger-ui.operationsSorter=method
    springdoc.api-docs.path=/api-docs
```

Access the documentation: (app running on port 5557)
- <http://localhost:5557/api-docs> (api-docs in JSON format)
- <http://localhost:5557/swagger-ui-custom.html> (Swagger)

![swagger](src/main/resources/static/swagger.png)
![swagger](src/main/resources/static/swagger2.png)

## Maven

### Maven project: 

[Getting Started](https://maven.apache.org/guides/getting-started/)

![Maven](src/main/resources/static/maven.png)

- The Maven setting is in the file pom.xml:
    - dependencies
    - plugins
    - profiles
    - ...

### Configuration
- Maven keeps separate configurations:

![Configuration](src/main/resources/static/runDebugConfig.png)

- This allows us to set up different setting for each configuration: We can use different sets of environment variables.
    - We can use local database URIs for running the app in development.
    - We can set up production database URIs for running integration tests.
    - We can set up database URIs for tests

### Maven Profiles
- We can set up different profiles with different configurations:
    - dev
    - prod
    - test...

## Security

### Spring security
![Security](src/main/resources/static/security.png) 
![app structure](src/main/resources/static/securityController.png)
- dependency: spring-boot-starter-security [Spring Security reference](https://docs.spring.io/spring-security/site/docs/5.4.6/reference/html5/)
- configuration: in the file WebSecurityConfig
- Authentication: registration / login
- Authorization: APIs are accessible to different roles - example: @PreAuthorize("hasRole('ADMIN')")
- <https://spring.io/guides/topicals/spring-security-architecture>
- <https://spring.io/guides/gs/securing-web> 

To disable the Spring security, go to WebSecurityConfig and use permitAll() on all endpoints:
```java
	protected void configure(HttpSecurity http) throws Exception {
        ...
        .antMatchers("/**").permitAll() //disabling the spring authentication
        ...
```

- During the registration the password is encrypted. During the login, it is decrypted.
```java
    org.springframework.security.crypto.password.PasswordEncoder;
    PasswordEncoder encoder.encode("mypassword");
```

- Authentication is using a JWT - JSON Web Token which is given to the client by the login endpoint if the credentials are correct.
- Authorization is using roles. A user can have multiple roles. Each endpoint can be set up for specific roles:
```java
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {...
```

- registration endpoint: 
    - POST <http://localhost:5557/api/auth/signup>, provide username, password, email, (array of roles)
- login endpoint (providing the JWT to the client) 
    - POST <http://localhost:5557/api/auth/signin>, provide username, password 

After successful registration or login, the client gets a JWT:

```json
{
  "id": 1,
  "username": "pesekt",
  "email": "pesekt@gmail.com",
  "roles": [
    "ROLE_ADMIN"
  ],
  "tokenType": "Bearer",
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwZXNla3QiLCJqdGkiOiIxIiwiaWF0IjoxNjE5ODkwNjE5LCJleHAiOjE2MTk5NzcwMTl9.C69pAPHEekfuAuYjjVCC7lYvUcg3OW8CGEgYY0GP2GoKWtZpFYdhl02XwNnSIorXFJnV1MJSWMHJaFMLE8SnMg"
}
```

- A web client app can save the token and use it to access different endpoints.
    - See the React.js web client app for this server here on gitHub: [SWC3-React-Frontend](https://github.com/pesekt1/SWC3-React-Frontend)
- We can use Postman as well:
    - Create POST http request - request body will contain user credentials
    - Create an Environment in Postman with authToken variable
    - Create Tests script - this script will run after the request is executed:
```javascript
var token = pm.response.json().accessToken;
pm.environment.set("authToken", token);
```

- JWT token will be stored in the Environment variable in the Postman, so we can reuse it in all other requests.:
- Each request just needs Authorization key value pair: Token : {{authToken}}
- It will read the value from the Environment variable.
- [Postman variables docs](https://learning.postman.com/docs/sending-requests/variables/)

If authorization or authentication fails, AccessDeniedException will be raised, 
and the server will respond with http status 403 - forbidden. 
This is handled in our exception handler:

```java
  @ExceptionHandler({ AccessDeniedException.class })
  public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
            HttpStatus.FORBIDDEN.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
  }
```

Our exception handler uses our custom ErrorMessage:

```java
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
...
```

The response will look like this:

```json
{
    "statusCode": 403,
    "timestamp": "2021-05-01T20:48:36.269+00:00",
    "message": "Access is denied",
    "description": "uri=/api/customers"
}
```

### SQL injection when using JDBC
- One endpoint in CourseDAO is vulnerable to SQL injections (it uses string concatenation):
```java
    public List<Course> listVulnerable(String filter) {
        String sql = "SELECT course_id,title,description,link from courses WHERE link =" + filter;
        return jdbcTemplate.query(sql, new CourseRowMapper());
    }
```

- Example of SQL injections are in httpRequests.http:
    - .../courses/vulnerable?filter="http://google.com" OR 1 = 1
    - .../courses/vulnerable?filter="http://google.com"; DELETE from courses
    - .../courses/vulnerable?filter="http://google.com"; DROP table courses

- For this to work, the database must allow multiple queries. In this project it is done by adding this to the database connection string:
        
        ?allowMultiQueries=true

- [SQL injection demo website1](https://www.hacksplaining.com/exercises/sql-injection#/start)
- [SQL injection demo website2](https://www.codingame.com/playgrounds/154/sql-injection-demo/sql-injection)


## Http communication

### HttpClient
- Showing how to send an http request and how to handle the response
- library: java.net.http.HttpClient
- using a public API to get some data: [regres.in](https://reqres.in/api/users)
- using a public API [jsonplaceholder](https://jsonplaceholder.typicode.com/)
- dependency: jackson-databind: class ObjectMapper:
    - Mapping response string into an object: 
```java
        Data data = mapper.readValue(response.body(), new TypeReference<Data>() {});
```

### Json-server
- [json-server npm](https://www.npmjs.com/package/json-server)

![json-server](src/main/resources/static/json-server.png)
- Fake REST API generated from a json file.
- Install json-server (npm install json-server) ... this will install the dependencies in node_modules
- package.json:
```json
      "scripts": {
        "start": "json-server --watch ./posts.json"
      },
```     

- Run json-server: **npm start**: <http://localhost:3000/posts>
- Or run: **npx json-server data.json**
- Generate data for json-server: (Install packages faker and lodash)
```bash
    npm install faker lodash
```
- [npm faker](https://www.npmjs.com/package/faker)
- [npm lodash](https://www.npmjs.com/package/lodash)

- Create js function generate.js:
```javascript
    module.exports = function () {
        let faker = require("faker");
        let _ = require("lodash");
    
        return {
            users: _.times(100, function(n){
                return{
                    id: n,
                    name: faker.name.findName(),
                    avatar: faker.internet.avatar()
                }
            })
        }
    }    
```
- run json-server with this function:
```bash
    npx json-server generate.js
```

- Now we have an API people with 100 json objects which were generated by that function.

![json-server](src/main/resources/static/json-server2.png)
- We have all the CRUD features. If we POST new data, we can save it by executing "s" command. 
It will create a snapshot json file.

[json-server github](https://github.com/typicode/json-server)

- There are many features like sorting, pagination, etc.:

    <http://localhost:3000/users?_sort=first_name&_order=desc>

- Add custom routes:
    - Create routes.json
```json
        {
          "/resources/:year": "/resources?year=:year"
        }
```

Run the server with routes: **npx json-server data.json --routes routes.json**

Now We have an extra route: <http://localhost:3000/resources/2005>

- We can also deploy our json-server app on Heroku, Azure etc. and have our public fake api running.


### Http requests
- [IntelliJ documentation](https://www.jetbrains.com/help/idea/exploring-http-syntax.html)

httpRequests.http file:

- registration: 
    - POST <http://localhost:5557/api/auth/signup>, provide user name, password, email, (array of roles)
- login (getting JWT) 
    - POST <http://localhost:5557/api/auth/signin>, provide user name, password 
- authorized request (using acquired JWT)
- tests:

```html
        > {%
        client.test("Request executed successfully", function() {
          client.assert(response.status === 200, "Response status is not 200");
        });
        %}
```

- **HttpRequests in HTTP format:**:
```http request
        POST http://localhost:5557/api/json-server/posts HTTP/1.1
        Content-Type: application/json
        
        {
        "userId": 10,
        "title": "test",
        "body": "bla bla"
        }
```

- **HttpRequests in PowerShell:**:
```bash
    $headers = New-Object "System.Collections.Generic.Dictionary[[String],[String]]"
    $headers.Add("Content-Type", "application/json")
    
    $body = "    {
    `n      `"userId`": 10,
    `n      `"title`": `"test`",
    `n      `"body`": `"bla bla`"
    `n    }"

    $response = Invoke-RestMethod 'http://localhost:5557/api/json-server/posts' -Method 'POST' -Headers $headers -Body $body
    $response | ConvertTo-Json
```

- **HttpRequests in Postman:**:

![Postman](src/main/resources/static/httpReqPostman.png)


## Testing

### Integration tests
- dependency: spring-boot-starter-test
- Integration tests for the database (done directly on the production database)
- We use @AutoConfigureDataJpa + @ContextConfiguration because we only want to use the context for the primary datasource.
```java
@SpringBootTest
@AutoConfigureDataJpa
@ContextConfiguration (classes = {Db1Config.class, TutorialRepository.class})
@Transactional("transactionManager")
class PrimaryDatasourceIntegrationTests {

    //...autowire the repositories

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Transactional("transactionManager")
    @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Tutorial> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }
```

### Unit tests

- Dependencies:
    - junit-jupiter
    - assertj-core
    - mockito-all
- Excluded dependencies:
    - junit-vintage-engine
    - junit

Test class structure:
```java
class TutorialServiceTest {
    @Mock private TutorialRepository tutorialRepository;
    private TutorialService tutorialService;

    @BeforeAll
    static void beforeAll() { }

    @AfterAll
    static void afterAll() { }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); //we need to initialize mocks
        tutorialService = new TutorialService(tutorialRepository); //using tutorialRepository mock
    }

    @AfterEach
    void tearDown() { }
```

Nesting of the test classes:

```java
class TutorialServiceTest {

    @Nested //grouping tests together
    class GetAllTutorialsTest{
        @Test
        @DisplayName("should get all tutorials")
        void should_get_all_tutorials_if_title_not_provided() {...}

        @Test
        @DisplayName("should respond with status NOT_FOUND if there are no tutorials")
        void status_not_found_if_no_tutorials_found(){...}
    }

    @Nested
    class GetTutorialByIdTest{
        @Test
        @DisplayName("should get tutorial with given id")
        void should_get_tutorial_by_Id() {...}

        @Test
        @DisplayName("should throw ResourceNotFoundException if tutorial with given id not exists")
        void should_throw_ResourceNotFoundException_if_no_tutorial_with_given_id(){...}
    }
```

Unit test structure: given - when - then

```java
@Test
@DisplayName("should respond with status NOT_FOUND if there are no tutorials")
void status_not_found_if_no_tutorials_found(){
    //given
    Mockito.when(tutorialRepository.findAll()).thenReturn(new ArrayList<>()); //empty list

    //when
    var response = tutorialService.getAllTutorials(null);

    //then
    Assertions.assertThat(response).isEqualTo(new ResponseEntity<>(HttpStatus.NO_CONTENT));
}
```

Testing features:
```java
//private final TutorialRepository tutorialRepository = Mockito.mock(TutorialRepository.class);
@Mock private TutorialRepository tutorialRepository; //mock the dependency

MockitoAnnotations.initMocks(this); //initialize mocks

//mock the behavior of methods
Mockito.when(tutorialRepository.findByTitleContaining(title1)).thenReturn(listFilteredTutorials);
```

Assertions:

```java
Assertions.assertThat(response.getBody()).isEqualTo(listFilteredTutorials);

Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

//throws an exception
Assertions.assertThatThrownBy(
        () -> tutorialService.getTutorialById(FAKE_TUTORIAL_ID) //when
).isInstanceOf(ResourceNotFoundException.class);

//does not throw an exception
org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> tutorialService.deleteTutorial(FAKE_TUTORIAL_ID));

Assertions.assertThat(response.getBody()).isNull(); // .isEqualTo(null)

//verify that deleteById method was called once
Mockito.verify(tutorialRepository, Mockito.times(1)).deleteById(FAKE_TUTORIAL_ID);
```

How to compare objects in the tests:

- isEqualTo() is by default comparing the object addresses - not the content!
    - To use it, we must override equals() for the compared object class.
- Alternatively, we can use Objects.equals(o1,o2)
- We can also iterate over the fields and compare them one by one - not a good solution

```java
        @Test
        void should_send_response_with_updated_tutorial() {
            var response = tutorialMongoService.update(FAKE_TUTORIAL_ID, updatedTutorial); //when

            //if we have overridden equals method for that class
            Assertions.assertThat(response.getBody()).isEqualTo(updatedTutorial);

            // if we dont have overridden equals method for that class
            Assertions.assertThat(Objects.equals(response.getBody(), updatedTutorial));
            
            // if we want to iterate over the fields and check them one by one - better to use Objects.equals(o1,o2)
            for(int i = 0; i < 3; i++){
                Assertions.assertThat(
                        Objects.requireNonNull(response.getBody()).getClass().getDeclaredFields()[i])
                        .isEqualTo(updatedTutorial.getClass().getDeclaredFields()[i]);
            }
        }
```

### API tests


### gitHub CI action

![Github action](src/main/resources/static/githubAction.png)

- .github/workflows/maven.yml: 

This allows us to create Java continuous integration with Maven,
after pushing to gitHub, the CI action gets executed (tests), if successful, 
the app will be automatically deployed to Heroku cloud (this has to be set up on Heroku).

[github actions with maven](https://docs.github.com/en/actions/guides/building-and-testing-java-with-maven)
- Specify Java JDK and java-version:
```yaml
        steps:
        - uses: actions/checkout@v2
        - name: Set up JDK 11
          uses: actions/setup-java@v1
          with:
            java-version: 11
```

- User environment variables on github:
```yaml
      env:
        DATABASE_URL: ${{secrets.DATABASE_URL}}
        DB2_URL: ${{secrets.DB2_URL}}
        DB3_URL: ${{secrets.DB3_URL}}
        DB4_URL: ${{secrets.DB4_URL}}
        MONGO_URL: ${{secrets.MONGO_URL}}
        SECRET_KEY: ${{secrets.SECRET_KEY}}
```

- Specify the task:
```yaml
    run: 
        - mvn -B package -P prod --file pom.xml 
```


## Cloud Deployment

### Git and gitHub
- Set up Git version control in IntelliJ.
- Set up remote repository on gitHub where you want to have your code.
- Commit changes, push to the remote repository.


### Deployment to Heroku Cloud
- We need to create a new app on Heroku and deploy our code from GitHub.
- We need to provision database resources for our data sources - for the development we were using local servers.
- Either we can provision databases on Heroku or elsewhere.
- We can get MySQL, Postgres and SQL Server on Heroku: <https://elements.heroku.com/addons>

- We can get MongoDB on MongoDB Atlas and Azure SQL Database on Azure:
    - <https://www.mongodb.com/cloud/atlas>
    - <https://azure.microsoft.com/en-us/services/sql-database/>

- Now we need to set up the configuration variables (config vars) on Heroku for all data sources: 
After deployment the app will connect to these data sources:

![config vars](src/main/resources/static/configvars.png)

Note: Our development is done with environment variables in IntelliJ, after deploying the app Heroku takes over, 
and the application will use the config vars.

- Max connections limit: The free database resources have usually small connections limit which can be easily exceeded 
by our spring boot app. Solution: Limit the thread pool size for each data source.

```java
db1.datasource.hikari.maximum-pool-size=5
```

- We can set up automatic deployment in Heroku:
    - Connect heroku app to gitHub repository
    - Enable automatic deployments
    - Check the option "Wait for CI to pass before deploy"

- Now we have following deployment workflow:
    - Push code to GitHub repository
    - GitHub will run an Action (Java CI with Maven)
    - If it succeeds it will deploy to Heroku


### system.properties
    java.runtime.version=11
This is for Heroku cloud - it tells is to use java 11 buildpack.

### Docker
- Docker allows us to containerize our application - We will have a docker image or our app.
- This is useful for scaling - We can use Kubernetes orchestration tool to run our containerized app in the cloud.
- more info coming soon....

## Web client app

- See the React.js web client app for this server here on gitHub: [SWC3-React-Frontend](https://github.com/pesekt1/SWC3-React-Frontend)
- It has limited features:
    - Login / Logout
    - Show tutorials (from MySQL DB tutorials table)
    - Show customers (from MySQL DB customers table) - only for users with ADMIN role
- Communication is done using axios library.

## DAO - Data Access Object

The Data Access Object (DAO) pattern is a structural pattern that allows us to isolate the application/business layer 
from the persistence layer (database) using an abstract API.

The functionality of this API is to hide from the application all the complexities involved in performing CRUD operations 
in the underlying storage mechanism. This allows both layers to evolve separately without knowing anything about each other.

## Multiple implementation classes

If we have multiple implementing classes we need to specify which implementation should be injected. 
Example:

Interface:
```java
public interface DAO<T> {
    List<T> getAll();
    ...
}
```

Implementing classes:

```java
@Component
public class CourseDAO implements DAO<Course> {...

@Component
public class CourseDAO2 implements DAO<Course> {...
```

We use @Qualifier annotation to select the implementation class:

```java
@RestController
@RequestMapping("/api/courses")
public class CourseController implements CourseOperations{

    private final DAO<Course> dao;

    @Autowired
    public CourseController(@Qualifier("courseDAO") DAO<Course> dao) {
        this.dao = dao;
    }
```

## JDBC - Java Database Connectivity, db connection without the ORM, just using POJOs

[tutorial](https://www.baeldung.com/spring-jdbc-jdbctemplate)

![jdbc](src/main/resources/static/jdbc.png)
- Model Course is a POJO - not a mapping class of a table.
- DAO interface - a generic interface defining how an implementing class should look like
- CourseDAO - A class implementing DAO interface contains CRUD methods which use sql queries to query from courses table.
- CourseRowMapper is used to build the Course object from the result set.

Example: If we have multiple data sources, we need to specify which jdbcTemplate we want to use. 
If not specified, the primary data source will be used.

```java
@Component
public class CourseDAO implements DAO<Course> {

    private static final Logger log = LoggerFactory.getLogger(CourseDAO.class);

    @Qualifier("jdbcTemplateDb2")
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT course_id,title,description,link from courses";
        return jdbcTemplate.query(sql, new CourseRowMapper());
    }
```

jdbcTemplate is defined in a config file with the data source:

```java
    @Bean(name = "jdbcTemplateDb2")
    public JdbcTemplate jdbcTemplateDb2(@Qualifier("dataSourceDb2") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
```

## Git

Removing a file from git repo:

.gitignore will not work if a file was already committed.
```git
git rm --cached -f <FILE_PATH>
```

## Optimization

- Bulk / Batch inserts and updates:

Instead of running separate inserts or updates in separate transactions we want to run them in batches - this is faster.
