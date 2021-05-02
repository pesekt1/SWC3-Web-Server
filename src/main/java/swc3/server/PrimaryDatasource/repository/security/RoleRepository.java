package swc3.server.PrimaryDatasource.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swc3.server.PrimaryDatasource.models.security.ERole;
import swc3.server.PrimaryDatasource.models.security.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
