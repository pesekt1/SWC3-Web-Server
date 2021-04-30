# Spring Boot Web Server

### App structure
![app structure](src/main/resources/static/appStructure.png)

### Features
- REST APIs for http communication like GET, POST, PUT, DELETE - for client-side rendering.
- Custom Exception Handler
- Pagination, Filtering, Sorting
- spring-boot-starter-data-rest to automatically generate the REST APIs: 
    - https://spring.io/guides/gs/accessing-data-rest/
- Multiple data sources
- Security - registration / login
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

### Database (MySQL)
- Maven Dependencies:
    - spring-boot-starter-data-jpa
    - mysql-connector-java 
- The self-contained dump file for the database is in sql folder: swc3_springboot_dump.sql
- Create that database and make sure it is used as a data source by the web server - via application.properties.
- Generate the model classes as shown above.
- Database EER:
![database EER](src/main/resources/static/databaseEER.png)

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

### Maven - [Getting Started](https://maven.apache.org/guides/getting-started/)
This is a Maven project:

![Maven](src/main/resources/static/maven.png)

- The Maven setting is in the file pom.xml:
    - dependencies
    - plugins
    - profiles
    - ...

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

### Thymeleaf [docs](https://www.thymeleaf.org/)
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


### gitHub CI action
- .github/workflows/maven.yml: 

This allows us to create Java continuous integration with Maven,
after pushing to gitHub, the CI action gets executed (tests), if successful, the app will be automatically deployed to heroku cloud:
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
        SECRET_KEY: ${{secrets.SECRET_KEY}}
```

### CORS
- configured in the file WebConfig: [Spring CORS](https://spring.io/guides/gs/rest-service-cors/)
    - allowedMethods 
    - allowedOrigins
    - allowCredentials
    - maxAge
    - allowedHeaders
    - ExposedHeaders

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

### local database server time zone error
If you get an error because of the timezone, run the following command in MySQL Workbench:
```mysql
    SET @@global.time_zone = '+00:00';
```

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

### Http requests [IntelliJ documentation](https://www.jetbrains.com/help/idea/exploring-http-syntax.html)
httpRequests.http file:

- registration: 
    - POST <http://localhost:5557/api/auth/signup>, provide username, password, email, (array of roles)
- login (getting JWT) 
    - POST <http://localhost:5557/api/auth/signin>, provide username, password 
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

### spring-boot-starter-data-rest
- <https://spring.io/guides/gs/accessing-data-rest/>
- Spring Data REST takes the features of Spring [HATEOAS](https://spring.io/projects/spring-hateoas) and Spring Data JPA and automatically combines them together.

### Changing the data source
- change the connection string in application.properties
- change the Hibernate dialect: [hibernate.dialect](https://docs.jboss.org/hibernate/orm/5.2/javadocs/org/hibernate/dialect/package-summary.html)
```java
    spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.MySQL5InnoDBDialect
    spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.SQLServerDialect
    spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQL10Dialect
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
Our code will be cleaner.

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

### Json-server [json-server npm](https://www.npmjs.com/package/json-server)
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

### system.properties
    java.runtime.version=11
This is for Heroku cloud - it tells is to use java 11 buildpack.

### Rest API documentation [springdoc](https://springdoc.org)
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

### JDBC example - db connection without the ORM, just using POJOs
![jdbc](src/main/resources/static/jdbc.png)
- Model Course is a POJO - not a mapping class of a table.
- DAO interface - a generic interface defining how an implementing class should look like
- CourseDAO - A class implementing DAO interface contains CRUD methods which use sql queries to query from courses table.
- CourseRowMapper is used to build the Course object from the result set.

### SQL injection
- One endpoint in CourseDAO is vulnerable to SQL injections (it uses string concatenation):
```java
    public List<Course> listVulnerable(String filter) {
        String sql = "SELECT course_id,title,description,link from courses WHERE link =" + filter;
        return jdbcTemplate.query(sql, new CourseRowMapper());
    }
```

- Example of SQL injections are in httpRequests.http:
    - .../courses/vulnerable?filter="http://google.com" OR 1 = 1
    - .../courses/vulnerable?filter="https://courses.danvega.dev/p/jhipster"; DELETE from courses
    - .../courses/vulnerable?filter="https://courses.danvega.dev/p/jhipster"; DROP table courses

- For this to work, the database must allow multiple queries. In this project it is done by adding this to the database connection string:
        
        ?allowMultiQueries=true

- [SQL injection demo website1](https://www.hacksplaining.com/exercises/sql-injection#/start)
- [SQL injection demo website2](https://www.codingame.com/playgrounds/154/sql-injection-demo/sql-injection)

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
        basePackages = {"swc3.server.repository", "swc3.server.nativequeries"})
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
        return builder.dataSource(dataSource).packages("swc3.server.models", "swc3.server.nativequeries").persistenceUnit("db1")
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
import swc3.server.Db5.models.Tutorial_mongo;

import java.util.List;

public interface TutorialRepository_mongo extends MongoRepository<Tutorial_mongo, String> {
    List<Tutorial_mongo> findByPublished(boolean published);
    List<Tutorial_mongo> findByTitleContaining(String title);
}
```

- Controller: The same as the other controllers.

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

### API tests

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

- We can set up a deployment pipeline:
    - Push code to GitHub repository
    - GitHub will run an Action (Java CI with Maven)
    - If it succeeds it will deploy to Heroku


### Docker
- Docker allows us to containerize our application - We will have a docker image or our app.
- This is useful for scaling - We can use Kubernetes orchestration tool to run our containerized app in the cloud.
- more info coming soon....
