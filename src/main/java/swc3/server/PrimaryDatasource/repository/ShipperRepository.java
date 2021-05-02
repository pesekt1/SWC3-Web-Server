package swc3.server.PrimaryDatasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.server.PrimaryDatasource.models.Shipper;

public interface ShipperRepository extends JpaRepository<Shipper, Integer> {

}
