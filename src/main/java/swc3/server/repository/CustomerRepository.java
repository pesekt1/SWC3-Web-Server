package swc3.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
