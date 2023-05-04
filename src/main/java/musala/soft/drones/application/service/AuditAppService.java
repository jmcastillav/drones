package musala.soft.drones.application.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.NonNull;
import musala.soft.drones.application.dto.DroneBatteryLogDto;

/** The interface Audit app service. */
public interface AuditAppService {

  /**
   * Gets drone battery by date range.
   *
   * @param startDate the start date
   * @param endDate the end date
   * @return the drone battery by date range
   */
  List<DroneBatteryLogDto> getDroneBatteryByDateRange(
      @NonNull LocalDateTime startDate, @NonNull LocalDateTime endDate);
}
