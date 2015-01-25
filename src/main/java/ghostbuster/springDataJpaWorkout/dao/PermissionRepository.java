package ghostbuster.springDataJpaWorkout.dao;

import ghostbuster.springDataJpaWorkout.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
