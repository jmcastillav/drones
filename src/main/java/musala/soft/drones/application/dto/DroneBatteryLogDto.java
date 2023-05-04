package musala.soft.drones.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class DroneBatteryLogDto {

  private final Long droneId;
  private final Integer batteryPercentage;
  private final LocalDateTime created;
}
