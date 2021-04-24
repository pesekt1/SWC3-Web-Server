package swc3.server.controller.clientApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.controller.clientApi.models.Data;
import swc3.server.controller.clientApi.models.Post;
import swc3.server.controller.clientApi.models.User;

import java.io.IOException;
import java.util.List;

//using HttpClient:
// - Getting data from a public API and sending them as a response
// - Getting data from a local json-server and sending them as a response

//With this architecture, we can build microservices that communicate to each other via APIs.
@RestController
@RequestMapping("/api")
public class ControllerWithHttpClient {

    ClientApiService clientApiService;

    public ControllerWithHttpClient(ClientApiService clientApiService) {
        this.clientApiService = clientApiService;
    }

    @GetMapping("/reqres.in/clientApiString")
    public ResponseEntity<String> getDataAsString() throws IOException, InterruptedException {
        return clientApiService.getDataAsString();
    }

    @GetMapping("/reqres.in/clientApiJson")
    public ResponseEntity<Data> getDataAsJson() throws IOException, InterruptedException {
        return clientApiService.getDataAsJson();
    }

    @GetMapping("/reqres.in/clientApiUsers")
    public ResponseEntity<List<User>> getUsersAsJson() throws IOException, InterruptedException {
        return clientApiService.getUsersAsJson();
    }

    @GetMapping("json-server/posts")
    public ResponseEntity<List<Post>> getPosts() throws IOException, InterruptedException {
        return clientApiService.getPosts();
    }
}
