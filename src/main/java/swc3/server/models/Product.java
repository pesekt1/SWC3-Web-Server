package swc3.server.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "products")
public class Product {
    private int productId;
    private String name;
    private int quantityInStock;
    private BigDecimal unitPrice;
    private Collection<OrderItem> orderItemsByProductId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "quantity_in_stock", nullable = false)
    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Basic
    @Column(name = "unit_price", nullable = false, precision = 2)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productId != product.productId) return false;
        if (quantityInStock != product.quantityInStock) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (unitPrice != null ? !unitPrice.equals(product.unitPrice) : product.unitPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + quantityInStock;
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        return result;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "productsByProductId")
    public Collection<OrderItem> getOrderItemsByProductId() {
        return orderItemsByProductId;
    }

    public void setOrderItemsByProductId(Collection<OrderItem> orderItemsByProductId) {
        this.orderItemsByProductId = orderItemsByProductId;
    }
}
