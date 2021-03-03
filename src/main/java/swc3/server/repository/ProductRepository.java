package swc3.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import swc3.server.models.Product;

@RestResource
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
