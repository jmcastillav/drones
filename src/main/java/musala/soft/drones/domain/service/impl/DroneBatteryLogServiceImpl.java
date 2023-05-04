package musala.soft.drones.domain.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.domain.entity.DroneBatteryLogEntity;
import musala.soft.drones.domain.exception.LogException;
import musala.soft.drones.domain.repository.DroneBatteryLogRepository;
import musala.soft.drones.domain.service.DroneBatteryLogService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DroneBatteryLogServiceImpl implements DroneBatteryLogService {

  private final DroneBatteryLogRepository droneBatteryLogRepository;

  @Override
  public void saveLog(final List<DroneBatteryLogEntity> droneBatteryLogEntities) {
    droneBatteryLogRepository.saveAll(droneBatteryLogEntities);
  }

  @Override
  public List<DroneBatteryLogEntity> getLog(
      @NonNull final LocalDateTime startDate, @NonNull final LocalDateTime endDate) {
    log.info("Received request to get drone battery log between {} and {}", startDate, endDate);
    if (startDate.isAfter(endDate)) {
      throw new LogException(HttpStatus.BAD_REQUEST, "StartDate cannot be after the EndDate.");
    }
    return droneBatteryLogRepository.findAllByCreatedBetween(startDate, endDate);
  }
}
