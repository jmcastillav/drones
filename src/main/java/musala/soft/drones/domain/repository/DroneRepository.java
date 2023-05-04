package musala.soft.drones.domain.repository;

import java.util.List;
import java.util.Optional;
import musala.soft.drones.domain.entity.DroneEntity;
import musala.soft.drones.domain.entity.DroneEntity.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<DroneEntity, Long> {

  Optional<DroneEntity> findBySerialNumber(String serialNumber);

  List<DroneEntity> findAllByStateIs(DroneState state);
}
