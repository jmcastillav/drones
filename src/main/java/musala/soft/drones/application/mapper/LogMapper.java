package musala.soft.drones.application.mapper;

import musala.soft.drones.application.dto.DroneBatteryLogDto;
import musala.soft.drones.domain.entity.DroneBatteryLogEntity;

/** The type Log mapper. */
public final class LogMapper {

  /**
   * To dto drone battery log.
   *
   * @param droneBatteryLogEntity the drone battery log entity
   * @return the drone battery log dto
   */
  public static DroneBatteryLogDto toDto(final DroneBatteryLogEntity droneBatteryLogEntity) {
    return DroneBatteryLogDto.builder()
        .droneId(droneBatteryLogEntity.getDroneId())
        .batteryPercentage(droneBatteryLogEntity.getBatteryPercentage())
        .created(droneBatteryLogEntity.getCreated())
        .build();
  }
}
