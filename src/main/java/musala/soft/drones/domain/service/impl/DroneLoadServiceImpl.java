package musala.soft.drones.domain.service.impl;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.domain.entity.DroneLoadEntity;
import musala.soft.drones.domain.repository.DroneLoadRepository;
import musala.soft.drones.domain.service.DroneLoadService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DroneLoadServiceImpl implements DroneLoadService {

  private final DroneLoadRepository droneLoadRepository;

  @Override
  public List<DroneLoadEntity> getDroneLoadById(final long droneId) {
    return droneLoadRepository.findAllByDroneId(droneId);
  }

  @Override
  public void addDroneLoad(final long droneId, final List<Long> medicamentIds) {
    medicamentIds.forEach(
        medicamentId -> {
          final DroneLoadEntity toAdd =
              DroneLoadEntity.builder().droneId(droneId).medicationId(medicamentId).build();
          droneLoadRepository.save(toAdd);
        });
  }
}
