package swc3.server.PrimaryDatasource.controller.products;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.server.PrimaryDatasource.dto.ProductDto;
import swc3.server.PrimaryDatasource.models.Product;

import javax.validation.Valid;
import java.util.List;

public interface ProductOperations {
    @GetMapping
    List<Product> getAll();

    @GetMapping("/{id}")
    Product getById(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody ProductDto product);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable int id);

    //TODO Add update endpoint - provide an object with all the fields that could be updated. Then update all the fields that were filled. Validate the input.
    //possible updates: name, quantity, unit_price, image_path
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody ProductDto product, @PathVariable int id);
}
