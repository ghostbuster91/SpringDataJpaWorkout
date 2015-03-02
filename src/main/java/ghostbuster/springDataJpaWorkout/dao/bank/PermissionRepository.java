package ghostbuster.springDataJpaWorkout.dao.bank;

import ghostbuster.springDataJpaWorkout.model.bank.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
