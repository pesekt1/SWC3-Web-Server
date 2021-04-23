package swc3.server.controller.clientApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

//using HttpClient: Getting data from a public API and sending them as a response
//With this architecture, we can build microservices that communicate to each other via APIs.
@RestController
@RequestMapping("/api")
public class ControllerWithHttpClient {

    ClientApiService clientApiService;

    public ControllerWithHttpClient(ClientApiService clientApiService) {
        this.clientApiService = clientApiService;
    }

    @GetMapping("/clientApiString")
    public ResponseEntity<String> getDataAsString() throws IOException, InterruptedException {
        return clientApiService.getDataAsString();
    }

    @GetMapping("/clientApiJson")
    public ResponseEntity<Data> getDataAsJson() throws IOException, InterruptedException {
        return clientApiService.getDataAsJson();
    }

    @GetMapping("/clientApiUsers")
    public ResponseEntity<List<User>> getUsersAsJson() throws IOException, InterruptedException {
        return clientApiService.getUsersAsJson();
    }
}
