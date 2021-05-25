package swc3.server.PrimaryDatasource.services.product;

import swc3.server.PrimaryDatasource.dto.ProductDto;
import swc3.server.PrimaryDatasource.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product getById(int id);
    void create(ProductDto product);
    void update(ProductDto productUpdate, int id);
    void deleteById(int id);
}
