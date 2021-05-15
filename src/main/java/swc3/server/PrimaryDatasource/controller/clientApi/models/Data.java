package swc3.server.PrimaryDatasource.controller.clientApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// we are mapping data from this public api:

// https://reqres.in/api/users

//{
//        "page": 1,
//        "per_page": 6,
//        "total": 12,
//        "total_pages": 2,
//        "data": [
//        {
//        "id": 1,
//        "email": "george.bluth@reqres.in",
//        "first_name": "George",
//        "last_name": "Bluth",
//        "avatar": "https://reqres.in/img/faces/1-image.jpg"
//        },

@Setter
@Getter
public class Data {
    private int page;

    @JsonProperty("per_page")
    private int perPage;

    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("data")
    private List<User> users;

    private Support support;
}
