package swc3.server.PrimaryDatasource.nativequeries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swc3.server.PrimaryDatasource.models.Order;

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
    @Query("SELECT o FROM Order o WHERE o.customerId = ?1 and o.shipperId = ?2")
    Collection<Order> findOrderByCustomerAndShipper(Integer customerId, Short shipperId);

    //named parameters
    @Query("SELECT o FROM Order o WHERE o.customerId = :customerId and o.shipperId = :shipperId")
    Collection<Order> findOrderByCustomerAndShipperNamedParams(
            @Param("customerId") Integer customerId,
            @Param("shipperId") Short shipperId);

    //TODO is this correct?
    //collection as a parameter
    @Query(value = "SELECT o FROM Order o JOIN Customer c WHERE c.lastName IN :names")
    Collection<Order> findOrdersByCustomerNameList(@Param("names") Collection<String> names);
}
