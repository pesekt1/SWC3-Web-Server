package swc3.server.PrimaryDatasource.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.dto.ProductDto;
import swc3.server.PrimaryDatasource.models.Product;
import swc3.server.PrimaryDatasource.repository.ProductRepository;
import swc3.server.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    private String errorMessage(int id){
        return "Not found product with id = " + id;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }

    @Override
    public void create(ProductDto productDto) {
        var newProduct = createProductFromDto(productDto);
        productRepository.save(newProduct);
    }

    @Override
    public void update(ProductDto productUpdate, int id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
        product.setImagePath(productUpdate.getImagePath());
        product.setName(productUpdate.getName());
        product.setQuantityInStock(productUpdate.getQuantityInStock());
        product.setUnitPrice(productUpdate.getUnitPrice());
        productRepository.save(product);
    }

    @Override
    public void deleteById(int id) {
        if (!productRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));
        productRepository.deleteById(id);
    }




    private Product createProductFromDto(ProductDto productDto) {
        var newProduct = new Product();
        newProduct.setImagePath(productDto.getImagePath());
        newProduct.setName(productDto.getName());
        newProduct.setUnitPrice(productDto.getUnitPrice());
        return newProduct;
    }
}
