package musala.soft.drones.domain.scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import musala.soft.drones.domain.entity.DroneBatteryLogEntity;
import musala.soft.drones.domain.service.DroneBatteryLogService;
import musala.soft.drones.domain.service.DroneService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** The type Drone battery scheduler. */
@Component
@AllArgsConstructor
public class DroneBatteryScheduler {

  private final DroneBatteryLogService droneBatteryLogService;
  private final DroneService droneService;

  /** Schedule the logs every minute the drones battery capacity. */
  @Scheduled(fixedRate = 36000)
  public void scheduleFixedRateTask() {
    final List<DroneBatteryLogEntity> droneBatteryLogEntities = new ArrayList<>();
    droneService
        .getAllDrones()
        .forEach(
            droneEntity -> {
              final DroneBatteryLogEntity droneBatteryLogEntity =
                  DroneBatteryLogEntity.builder()
                      .droneSerial(droneEntity.getSerialNumber())
                      .batteryPercentage(droneEntity.getBatteryCapacity())
                      .created(LocalDateTime.now())
                      .build();
              droneBatteryLogEntities.add(droneBatteryLogEntity);
            });
    droneBatteryLogService.saveLog(droneBatteryLogEntities);
  }
}
