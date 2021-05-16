package swc3.server.PrimaryDatasource.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class OrderItemPK implements Serializable {

    @Column(name = "order_id", nullable = false)
    @Id
    private int orderId;

    @Column(name = "product_id", nullable = false)
    @Id
    private int productId;
}
