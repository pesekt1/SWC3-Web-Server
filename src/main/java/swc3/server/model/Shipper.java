package swc3.server.model;

import javax.persistence.*;

@Entity
@Table(name = "shippers", schema = "swc3_springboot")
public class Shipper {
    private short shipperId;
    private String name;

    @Id
    @Column(name = "shipper_id", nullable = false)
    public short getShipperId() {
        return shipperId;
    }

    public void setShipperId(short shipperId) {
        this.shipperId = shipperId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shipper shipper = (Shipper) o;

        if (shipperId != shipper.shipperId) return false;
        if (name != null ? !name.equals(shipper.name) : shipper.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) shipperId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
