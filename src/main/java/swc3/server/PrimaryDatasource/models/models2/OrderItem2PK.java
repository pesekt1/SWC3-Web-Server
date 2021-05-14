package swc3.server.PrimaryDatasource.models.models2;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class OrderItem2PK implements Serializable {
    @Column(name = "order_id", nullable = false)@Id
    private int orderId;

    @Column(name = "product_id", nullable = false)@Id
    private int productId;
}
