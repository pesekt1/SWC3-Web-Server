package swc3.server.PrimaryDatasource.pojo;

import lombok.Getter;

@Getter
public class PaymentPojo {
    private int invoiceId;
    private long amount;
    private byte paymentMethod;

}
