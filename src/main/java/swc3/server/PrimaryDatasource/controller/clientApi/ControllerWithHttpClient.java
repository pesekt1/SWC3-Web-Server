package swc3.server.PrimaryDatasource.controller.clientApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swc3.server.PrimaryDatasource.controller.clientApi.models.*;

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

    //endpoints for public API reques.in

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

    //endpoints for local json-server running on port 3000

    @GetMapping("json-server/posts")
    public ResponseEntity<List<Post>> getPosts() throws IOException, InterruptedException {
        return clientApiService.getPosts();
    }

    @PostMapping("/json-server/posts")
    public ResponseEntity<Post> createTutorial(@RequestBody Post post) throws IOException, InterruptedException {
        return clientApiService.createPost(post);
    }

    @PostMapping("/json-server/users")
    public ResponseEntity<User> createTutorial(@RequestBody User user) throws IOException, InterruptedException {
        return clientApiService.createUser(user);
    }

    @GetMapping("jsonplaceholder/albums")
    public ResponseEntity<List<Album>> getAlbums() throws IOException, InterruptedException {
        return clientApiService.getAlbums();
    }

    @PostMapping("jsonplaceholder/albums")
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) throws IOException, InterruptedException {
        return clientApiService.createAlbum(album);
    }

    //REST controller done differently
    //We dont need to use ResponseEntity - we can annotate @Response status and return the object
    @RequestMapping(value = "jsonplaceholder/photos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Photo createPhoto(@RequestBody Photo photo) throws IOException, InterruptedException {
        return clientApiService.createPhoto(photo);
    }
}
