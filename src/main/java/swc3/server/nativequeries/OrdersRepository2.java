package swc3.server.nativequeries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swc3.server.models.Order;

import java.util.Collection;

public interface OrdersRepository2 extends JpaRepository<Order, Integer>{

    //JPQL - Java Persistence Query Language
    @Query("SELECT o FROM Order o WHERE o.shippedDate IS NOT NULL ")
    Collection<Order> findAllShippedOrders();

    @Query(
            value = "SELECT * FROM Orders o WHERE o.shipped_date IS NOT NULL",
            nativeQuery = true) // this enables native SQL
    Collection<Order> findAllShippedOrdersNative();

    @Query(value = "SELECT o FROM Order o ORDER BY o.orderId")
    Page<Order> findAllOrdersWithPagination(Pageable pageable);

    //indexed parameters in JPQL:
    @Query("SELECT o FROM Order o WHERE o.customerByCustomerId.customerId = ?1 and o.shippersByShipperId.shipperId = ?2")
    Collection<Order> findOrderByCustomerAndShipper(Integer customerId, Short shipperId);

    //named parameters
    @Query("SELECT o FROM Order o WHERE o.customerByCustomerId.customerId = :customerId and o.shippersByShipperId.shipperId = :shipperId")
    Collection<Order> findOrderByCustomerAndShipperNamedParams(
            @Param("customerId") Integer customerId,
            @Param("shipperId") Short shipperId);

    //collection as a parameter
    @Query(value = "SELECT o FROM Order o WHERE o.customerByCustomerId.lastName IN :names")
    Collection<Order> findOrdersByCustomerNameList(@Param("names") Collection<String> names);
}
