package musala.soft.drones.application.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record DroneBatteryLogDto(
    String droneSerial, Integer batteryPercentage, LocalDateTime created) {}
