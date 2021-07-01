package swc3.server.PrimaryDatasource.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TutorialDto {
    //private long id;
    private String description;
    private Boolean published;
    private String title;
}