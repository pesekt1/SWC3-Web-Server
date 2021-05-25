package swc3.server.PrimaryDatasource.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "products", schema = "swc3_springboot")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Basic
    @Column(name = "quantity_in_stock", nullable = false)
    private int quantityInStock;

    @Basic
    @Column(name = "unit_price", nullable = false, precision = 2)
    private double unitPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var product = (Product) o;

        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return 2042274511;
    }
}
