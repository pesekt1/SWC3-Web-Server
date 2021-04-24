package swc3.server.controller.clientApi.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Post {
    private int id;
    private int userId;
    private String title;
    private String body;

}