package swc3.server.PrimaryDatasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import swc3.server.PrimaryDatasource.models.Product;

@RestResource
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
