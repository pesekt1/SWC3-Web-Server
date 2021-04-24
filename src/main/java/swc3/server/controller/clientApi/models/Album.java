package swc3.server.controller.clientApi.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Album {
    private int userId;
    private int id;
    private String title;
}
