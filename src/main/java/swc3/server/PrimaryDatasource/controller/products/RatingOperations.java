package swc3.server.PrimaryDatasource.controller.products;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.server.PrimaryDatasource.models.ProductRating;
import swc3.server.PrimaryDatasource.models.ProductRatingPK;

import javax.validation.Valid;
import java.util.List;

public interface RatingOperations {
    @GetMapping
    List<ProductRating> getAll();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody ProductRating rating);

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestBody ProductRatingPK ratingPK);
}
