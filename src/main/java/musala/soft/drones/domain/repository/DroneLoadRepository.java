package musala.soft.drones.domain.repository;

import java.util.List;
import musala.soft.drones.domain.entity.DroneLoadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneLoadRepository extends JpaRepository<DroneLoadEntity, Long> {

  List<DroneLoadEntity> findAllByDroneId(final Long droneId);
}
