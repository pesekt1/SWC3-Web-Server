package swc3.server.PrimaryDatasource.services.product;

import swc3.server.PrimaryDatasource.models.ProductRating;
import swc3.server.PrimaryDatasource.models.ProductRatingPK;

import java.util.List;

public interface RatingService {
    List<ProductRating> getAll();

    void save(ProductRating rating);

    void delete(ProductRatingPK ratingPK);
}
