package swc3.server.PrimaryDatasource.nativequeries;

import org.springframework.stereotype.Repository;
import swc3.server.PrimaryDatasource.models.Customer;

import java.util.List;
import java.util.Set;

@Repository
public interface CustomCustomerRepository2 {
    List<Customer> findCustomersByCities(Set<String> emails);
}
