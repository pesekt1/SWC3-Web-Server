package swc3.server.PrimaryDatasource.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TutorialDto {
    private String description;
    private Boolean published;
    private String title;
}

//new way - using record:
//public record TutorialDto (String name, String address) {}