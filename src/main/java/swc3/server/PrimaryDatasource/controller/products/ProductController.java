package swc3.server.PrimaryDatasource.controller.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.PrimaryDatasource.dto.ProductDto;
import swc3.server.PrimaryDatasource.models.Product;
import swc3.server.PrimaryDatasource.services.product.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController implements ProductOperations{
    ProductService productService;

    @Autowired
    public ProductController(@Qualifier("productServiceImpl") ProductService productService){
        this.productService = productService;
    }

    @Override
    public List<Product> getAll() {
        return productService.getAll();
    }

    @Override
    public Product getById(int id) {
        return productService.getById(id);
    }

    @Override
    public void create(ProductDto product) {
        productService.create(product);
    }

    @Override
    public void deleteById(int id) {
        productService.deleteById(id);
    }

    @Override
    public void update(ProductDto productUpdate, int id) {
        productService.update(productUpdate, id);
    }
}
