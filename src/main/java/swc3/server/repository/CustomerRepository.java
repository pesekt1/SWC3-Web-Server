package swc3.server.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import swc3.server.models.Customer;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    //stored procedure in native query
    @Query(value = "CALL get_all_customers()", nativeQuery = true)
    List<Customer> findAllCustomers();

    //stored procedure in native query
    //with IN parameter
    @Query(value = "CALL get_customer_by_id(:id)", nativeQuery = true)
    Customer findCustomerById(int id);

    //this does not work:
    //procedure reference
//    @Procedure(name = "get_all_customers()")
//    List<Customer> get_all_customers();
}
