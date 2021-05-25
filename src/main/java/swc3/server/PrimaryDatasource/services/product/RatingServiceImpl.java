package swc3.server.PrimaryDatasource.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.ProductRating;
import swc3.server.PrimaryDatasource.models.ProductRatingPK;
import swc3.server.PrimaryDatasource.repository.ProductRatingRepository;
import swc3.server.PrimaryDatasource.repository.ProductRepository;
import swc3.server.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService{

    ProductRatingRepository ratingRepository;
    ProductRepository productRepository;

    @Autowired
    public RatingServiceImpl(ProductRatingRepository ratingRepository, ProductRepository productRepository){
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
    }

    private String errorMessage(int id){
        return "Not found product with id = " + id;
    }

    @Override
    public List<ProductRating> getAll() {
        return ratingRepository.findAll();
    }

    @Override
    public void save(ProductRating rating) {
        var action = "new";
        int ratingDifference = 0;
        var data = ratingRepository.findById(rating.getProductRatingId()); //using composite PK
        if (data.isPresent()){
            action = "update";
            var existingRating = data.get();
            ratingDifference = rating.getRating() - existingRating.getRating();
        }

        ratingRepository.save(rating);
        var productId = rating.getProductRatingId().getProductId();
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(productId)));

        product.updateRating(rating.getRating(), ratingDifference, action);
        productRepository.save(product);
    }

    @Override
    public void delete(ProductRatingPK ratingPK) {
        var existingRating = ratingRepository.findById(ratingPK)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found"));

        ratingRepository.deleteById(ratingPK);

        var productId = ratingPK.getProductId();
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(productId)));

        //TODO updateRating could be overloaded - we only need the difference and action here
        product.updateRating(0, existingRating.getRating(), "delete");
        productRepository.save(product);

    }
}
