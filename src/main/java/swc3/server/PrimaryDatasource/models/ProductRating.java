package swc3.server.PrimaryDatasource.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "product_ratings", schema = "swc3_springboot")
public class ProductRating {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride( name = "customerId", column = @Column(name = "customer_id")),
            @AttributeOverride( name = "productId", column = @Column(name = "product_id"))
    })
    ProductRatingPK productRatingId;

    @Basic
    @Column(name = "rating", nullable = false)
    private int rating;

    @Basic
    @Column(name = "review", nullable = true, length = -1) //length -1 ???
    private String review;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductRating that = (ProductRating) o;

        return productRatingId != null && productRatingId.equals(that.productRatingId);
    }

    @Override
    public int hashCode() {
        return productRatingId != null ? productRatingId.hashCode() : 0;
    }
}
