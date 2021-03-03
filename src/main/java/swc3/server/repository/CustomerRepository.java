package swc3.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swc3.server.models.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    //native query
    @Query(value = "CALL get_all_customers()", nativeQuery = true)
    List<Customer> findAllCustomers();
}
