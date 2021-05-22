package swc3.server.PrimaryDatasource.services.payment;

import swc3.server.PrimaryDatasource.models.Payment;
import swc3.server.PrimaryDatasource.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    List<Payment> getAll();
    Payment getById(int id);
    List<Payment> getByCustomerId(int id);
    void create(PaymentDto payment);
    void delete(int id);
}
