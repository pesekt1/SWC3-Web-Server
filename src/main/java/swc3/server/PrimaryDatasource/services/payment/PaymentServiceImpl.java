package swc3.server.PrimaryDatasource.services.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swc3.server.PrimaryDatasource.models.Payment;
import swc3.server.PrimaryDatasource.dto.PaymentDto;
import swc3.server.PrimaryDatasource.repository.InvoiceRepository;
import swc3.server.PrimaryDatasource.repository.OrderRepository;
import swc3.server.PrimaryDatasource.repository.PaymentRepository;
import swc3.server.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{
    private String errorMessage(long id){
        return "Not found payment with id = " + id;
    }
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, InvoiceRepository invoiceRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }

    @Override
    public List<Payment> getByCustomerId(int id) {
        return paymentRepository.findByCustomerId(id);
    }

    @Override
    public void create(PaymentDto payment) {
        Payment newPayment = createPaymentFromPojo(payment);
        paymentRepository.save(newPayment);
    }

    @Override
    public void delete(int id) {
        if (!paymentRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));
        paymentRepository.deleteById(id);
    }

    private Payment createPaymentFromPojo(PaymentDto payment) {
        var newPayment = new Payment();
        newPayment.setAmount(payment.getAmount());
        newPayment.setInvoiceId(payment.getInvoiceId());
        newPayment.setPaymentMethod(payment.getPaymentMethod());
        newPayment.setDate(LocalDate.now());
        newPayment.setCustomerId(findCustomerId(payment.getInvoiceId()));
        return newPayment;
    }

    private int findCustomerId(int invoiceId){
        var invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new ResourceNotFoundException("invoice not found"));
        var order = orderRepository.findById(invoice.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("order not found"));
        return order.getCustomerId();
    }
}
