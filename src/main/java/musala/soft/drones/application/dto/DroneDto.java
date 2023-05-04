package musala.soft.drones.application.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class DroneDto {

  private final String serialNumber;

  private final String model;

  private final int weightLimit;

  private final int batteryCapacity;

  private final String state;

  private final List<MedicationDto> medications;

  public List<MedicationDto> getMedications() {
    return Objects.nonNull(medications) ? List.copyOf(medications) : Collections.emptyList();
  }
}
