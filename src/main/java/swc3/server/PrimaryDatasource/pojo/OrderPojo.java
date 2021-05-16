package swc3.server.PrimaryDatasource.pojo;

import lombok.Getter;
import swc3.server.PrimaryDatasource.models.Customer;
import swc3.server.PrimaryDatasource.models.OrderItem;

import java.util.Collection;

@Getter
public class OrderPojo {
    private String comments;
    private Collection<OrderItem> orderItems;
    private int customerId;
}
