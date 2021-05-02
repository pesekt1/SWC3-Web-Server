package swc3.server.PrimaryDatasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swc3.server.PrimaryDatasource.models.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    //stored procedure in native query
    @Query(value = "CALL get_all_customers()", nativeQuery = true)
    List<Customer> findAllCustomersSP();

    //stored procedure in native query
    //with IN parameter
    @Query(value = "CALL get_customer_by_id(:id)", nativeQuery = true)
    Customer findCustomerByIdSP(int id);

    //this does not work:
    //procedure reference
//    @Procedure(name = "get_all_customers()")
//    List<Customer> get_all_customers();
}
