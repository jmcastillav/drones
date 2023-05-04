package musala.soft.drones.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import musala.soft.drones.domain.entity.DroneBatteryLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneBatteryLogRepository extends JpaRepository<DroneBatteryLogEntity, Long> {

  List<DroneBatteryLogEntity> findAllByCreatedBetween(
      LocalDateTime startDate, LocalDateTime endDate);
}
