package musala.soft.drones.application.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Builder;

@Builder
public record DroneDto(
    String serialNumber,
    String model,
    int weightLimit,
    int batteryCapacity,
    String state,
    List<MedicationDto> medications) {

  @Override
  public List<MedicationDto> medications() {
    return Objects.nonNull(medications) ? List.copyOf(medications) : Collections.emptyList();
  }
}
