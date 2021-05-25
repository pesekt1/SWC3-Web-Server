package swc3.server.PrimaryDatasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.PrimaryDatasource.models.ProductRating;
import swc3.server.PrimaryDatasource.models.ProductRatingPK;

//TODO problem with Spring Data REST - it cannot figure out the composite id
public interface ProductRatingRepository extends JpaRepository<ProductRating, ProductRatingPK> {
}
