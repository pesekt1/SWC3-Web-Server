package swc3.server.PrimaryDatasource.nativequeries;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swc3.server.PrimaryDatasource.models.Customer;

public interface CustomersRepository2 extends JpaRepository<Customer, Integer> {

    //modifying queries
    @Modifying
    @Query("update Customer c set c.points = c.points + :points where c.customerId = :customerId")
    int addCustomerPoints(@Param("customerId") Integer customerId,
                          @Param("points") Integer points);

    //INSERT - native query
    @Modifying
    @Query(value = "insert into Customers (first_name, last_name, address, state, city, points) " +
            "values (:firstName, :lastName, :address, :state, :city, :points)",
            nativeQuery = true)
    void insertCustomer(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("address") String address,
            @Param("state") String state,
            @Param("city") String city,
            @Param("points") Integer points
    );

}
