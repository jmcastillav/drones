package musala.soft.drones.application.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.application.dto.DroneBatteryLogDto;
import musala.soft.drones.application.mapper.LogMapper;
import musala.soft.drones.application.service.AuditAppService;
import musala.soft.drones.domain.service.DroneBatteryLogService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuditAppServiceImpl implements AuditAppService {

  private final DroneBatteryLogService droneBatteryLogService;

  @Override
  public List<DroneBatteryLogDto> getDroneBatteryByDateRange(
      @NonNull final LocalDateTime startDate, @NonNull final LocalDateTime endDate) {
    return droneBatteryLogService.getLog(startDate, endDate).stream()
        .map(LogMapper::toDto)
        .toList();
  }
}
