package swc3.server.nativequeries;

import org.springframework.stereotype.Service;
import swc3.server.models.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class CustomCustomerRepository2Impl implements CustomCustomerRepository2{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> findCustomersByCities(Set<String> cities)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> customer = query.from(Customer.class);

        Path<String> cityPath = customer.get("city");

        List<Predicate> predicates = new ArrayList<>();
        for (String city : cities) {
            predicates.add(cb.like(cityPath, city));
        }
        query.select(customer)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        return entityManager.createQuery(query).getResultList();
    }
}
