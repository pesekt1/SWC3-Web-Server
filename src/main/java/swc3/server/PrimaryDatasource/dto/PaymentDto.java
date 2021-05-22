package swc3.server.PrimaryDatasource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
    private int invoiceId;
    private long amount;
    private byte paymentMethod;
}
