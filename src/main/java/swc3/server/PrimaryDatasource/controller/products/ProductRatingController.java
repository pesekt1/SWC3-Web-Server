package swc3.server.PrimaryDatasource.controller.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.PrimaryDatasource.models.ProductRating;
import swc3.server.PrimaryDatasource.models.ProductRatingPK;
import swc3.server.PrimaryDatasource.services.product.RatingServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class ProductRatingController implements RatingOperations{
    RatingServiceImpl ratingService;

    @Autowired
    public ProductRatingController(@Qualifier("ratingServiceImpl") RatingServiceImpl ratingService){
        this.ratingService = ratingService;
    }


    @Override
    public List<ProductRating> getAll() {
        return ratingService.getAll();
    }

    @Override
    public void create(ProductRating rating) {
        ratingService.save(rating);
    }

    @Override
    public void delete(ProductRatingPK ratingPK) {
        ratingService.delete(ratingPK);
    }
}
