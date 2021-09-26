package swc3.server.PrimaryDatasource.dto;

import lombok.Getter;
import lombok.Setter;
import swc3.server.PrimaryDatasource.models.OrderItem;

import java.util.Collection;

//DTO - Data Transfer Object

@Setter
@Getter
public class OrderDto {
    private String comments;
    private Collection<OrderItem> orderItems;
    private int customerId;
}