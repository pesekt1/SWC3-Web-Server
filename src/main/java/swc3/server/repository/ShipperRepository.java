package swc3.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import swc3.server.models.Shipper;

public interface ShipperRepository extends JpaRepository<Shipper, Integer> {

}
