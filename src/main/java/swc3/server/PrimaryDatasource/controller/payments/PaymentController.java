package swc3.server.PrimaryDatasource.controller.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.server.PrimaryDatasource.models.Payment;
import swc3.server.PrimaryDatasource.dto.PaymentDto;
import swc3.server.PrimaryDatasource.services.payment.PaymentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController implements PaymentOperations{
    private final PaymentService paymentService; //interface

    @Autowired
    // @Qualifier - select implementation of the interface
    public PaymentController(@Qualifier("paymentServiceImpl") PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @Override
    public List<Payment> getAll() {
        return paymentService.getAll();
    }

    @Override
    public Payment getById(int id) {
        return paymentService.getById(id);
    }

    @Override
    public List<Payment> getByCustomerId(int id) {
        return paymentService.getByCustomerId(id);
    }

    @Override
    public void create(@Valid PaymentDto payment) {
        paymentService.create(payment);
    }

    @Override
    public void delete(int id) {
        paymentService.delete(id);
    }
}
