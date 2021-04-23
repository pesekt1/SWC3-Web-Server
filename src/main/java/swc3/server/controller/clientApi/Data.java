package swc3.server.controller.clientApi;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class Data {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<User> data;
    private Support support;
}
