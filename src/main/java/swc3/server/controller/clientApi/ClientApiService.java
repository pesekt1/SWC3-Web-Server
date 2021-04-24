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

    private static final String API = "https://reqres.in/api/users"; //public api sending some data as a JSON object
    private HttpClient client;

    public ClientApiService() {
        this.client = HttpClient.newHttpClient();
    }

    private HttpRequest buildRequest(String api){
        return HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(api))
                .build();
    }

    //API with a string response
    public ResponseEntity<String> getDataAsString() throws IOException, InterruptedException {

        HttpRequest request = buildRequest(API);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return new ResponseEntity<>(response.body(), HttpStatus.OK);
    }

    // API returning the Data object
    public ResponseEntity<Data> getDataAsJson() throws IOException, InterruptedException {

        HttpRequest request = buildRequest(API);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //Mapping response.body to Data class
        ObjectMapper mapper = new ObjectMapper();
        Data data = mapper.readValue(response.body(), new TypeReference<Data>() {});

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    //API returning the list of users
    //we get a response as a string
    //we mapp it to a Data object which fits to the JSON structure
    //we pick the list of users from the Data object
    public ResponseEntity<List<User>> getUsersAsJson() throws IOException, InterruptedException {

        HttpRequest request = buildRequest(API);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        Data data = mapper.readValue(response.body(), new TypeReference<Data>() {});

        //extracting the list of users from data instance
        List<User> users = data.getData();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //working with local json-server that runs on port 3000
    //in the folder json-server
    //it uses posts.json to generate an api with the data
    public ResponseEntity<List<Post>> getPosts() throws IOException, InterruptedException {

        HttpRequest request = buildRequest("http://localhost:3000/posts");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        List<Post> posts = mapper.readValue(response.body(), new TypeReference<>() {});

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
