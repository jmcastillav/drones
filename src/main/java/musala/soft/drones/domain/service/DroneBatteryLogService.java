package musala.soft.drones.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import musala.soft.drones.domain.entity.DroneBatteryLogEntity;

/** The interface Drone battery log service. */
public interface DroneBatteryLogService {

  /**
   * Save log.
   *
   * @param droneBatteryLogEntities the drone battery log entities
   */
  void saveLog(List<DroneBatteryLogEntity> droneBatteryLogEntities);

  /**
   * Gets log.
   *
   * @param startDate the start date
   * @param endDate the end date
   * @return the log
   */
  List<DroneBatteryLogEntity> getLog(LocalDateTime startDate, LocalDateTime endDate);
}
