package swc3.server.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products", schema = "swc3_springboot")
public class Product {
    private int productId;
    private String name;
    private int quantityInStock;
    private BigDecimal unitPrice;

    @Id
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
}
