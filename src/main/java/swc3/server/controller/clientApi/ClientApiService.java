package swc3.server.controller.clientApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swc3.server.controller.clientApi.models.Data;
import swc3.server.controller.clientApi.models.Post;
import swc3.server.controller.clientApi.models.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

//using HttpClient: Getting data from a public API and sending them as a response
//With this architecture, we can build microservices that communicate to each other via APIs.
@Service
public class ClientApiService {

    private static final String API_REGRES = "https://reqres.in/api/users"; //public api sending some data as a JSON object
    private static final String API_JSON_SERVER = "http://localhost:3000/";

    private final HttpClient client;

    public ClientApiService() {
        this.client = HttpClient.newHttpClient();
    }

    private HttpRequest buildGetRequest(String api){

        return HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(api))
                .build();
    }

    private HttpRequest buildPostRequest(String api, String requestBody){

        return HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .uri(URI.create(api))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
    }

    //API with a string response
    public ResponseEntity<String> getDataAsString() throws IOException, InterruptedException {

        HttpRequest request = buildGetRequest(API_REGRES);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return new ResponseEntity<>(response.body(), HttpStatus.OK);
    }

    // API returning the Data object
    public ResponseEntity<Data> getDataAsJson() throws IOException, InterruptedException {

        HttpRequest request = buildGetRequest(API_REGRES);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Mapping response.body to Data class
        Data data = new ObjectMapper().readValue(response.body(), new TypeReference<Data>() {});

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    //API returning the list of users
    //we get a response as a string
    //we map it to a Data object which fits to the JSON structure
    //we pick the list of users from the Data object
    public ResponseEntity<List<User>> getUsersAsJson() throws IOException, InterruptedException {

        HttpRequest request = buildGetRequest(API_REGRES);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Data data = new ObjectMapper().readValue(response.body(), new TypeReference<Data>() {});

        List<User> users = data.getData(); //extracting the list of users from data instance

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //working with local json-server that runs on port 3000
    //in the folder json-server
    //it uses posts.json to generate an api with the data
    public ResponseEntity<List<Post>> getPosts() throws IOException, InterruptedException {

        HttpRequest request = buildGetRequest(API_JSON_SERVER + "posts");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Post> posts = new ObjectMapper().readValue(response.body(), new TypeReference<List<Post>>() {});

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //sending POST request to the json-server running on port 3000
    //data will be saved in data.json in posts array
    public ResponseEntity<Post> createPost(Post post) throws IOException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(post);

        HttpRequest request = buildPostRequest(API_JSON_SERVER + "posts", requestBody);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Post _post = new ObjectMapper().readValue(response.body(), new TypeReference<Post>() {});
        return new ResponseEntity<>(_post, HttpStatus.CREATED);
    }

    //sending POST request to the json-server running on port 3000
    //data will be saved in data.json in users array
    public ResponseEntity<User> createUser(User user) throws IOException, InterruptedException {

        String requestBody = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(user);

        HttpRequest request = buildPostRequest(API_JSON_SERVER + "users", requestBody);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        User _user = new ObjectMapper().readValue(response.body(), new TypeReference<User>() {});
        return new ResponseEntity<>(_user, HttpStatus.CREATED);
    }
}
