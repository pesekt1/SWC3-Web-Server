package swc3.server.models;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Date;

//View
@Entity
@Immutable
@Table(name = "shipped_orders", schema = "swc3_springboot")
public class ShippedOrderView {

    @Id
    private int orderId;
    private Date shippedDate;
    private String customer;
    private String shipper;

    @Basic
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "shipped_date", nullable = true)
    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    @Basic
    @Column(name = "customer", nullable = true, length = 101)
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Basic
    @Column(name = "shipper", nullable = false, length = 50)
    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShippedOrderView that = (ShippedOrderView) o;

        if (orderId != that.orderId) return false;
        if (shippedDate != null ? !shippedDate.equals(that.shippedDate) : that.shippedDate != null) return false;
        if (customer != null ? !customer.equals(that.customer) : that.customer != null) return false;
        if (shipper != null ? !shipper.equals(that.shipper) : that.shipper != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (shippedDate != null ? shippedDate.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (shipper != null ? shipper.hashCode() : 0);
        return result;
    }
}
