package swc3.server.PrimaryDatasource.controller.clientApi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Photo {
    private int albumId;
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
