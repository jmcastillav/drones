package musala.soft.drones.application.mapper;

import java.util.List;
import java.util.Objects;
import musala.soft.drones.application.dto.DroneDto;
import musala.soft.drones.application.dto.MedicationDto;
import musala.soft.drones.domain.entity.DroneEntity;
import musala.soft.drones.domain.entity.DroneEntity.DroneModel;
import musala.soft.drones.domain.entity.DroneEntity.DroneState;

/** The type Drone mapper. */
public final class DroneMapper {

  /**
   * To entity drone.
   *
   * @param dto the dto
   * @return the drone entity
   */
  public static DroneEntity toEntity(final DroneDto dto) {
    final DroneModel model = DroneModel.getDroneModel(dto.getModel());
    return DroneEntity.builder()
        .serialNumber(dto.getSerialNumber())
        .model(model)
        .weightLimit(model.getWeightCapacity())
        .batteryCapacity(dto.getBatteryCapacity())
        .state(
            Objects.nonNull(dto.getState())
                ? DroneEntity.DroneState.getDroneState(dto.getState())
                : DroneState.IDLE)
        .build();
  }

  /**
   * To dto drone.
   *
   * @param entity the entity
   * @return the drone dto
   */
  public static DroneDto toDto(final DroneEntity entity) {
    final DroneModel model = entity.getModel();
    return DroneDto.builder()
        .serialNumber(entity.getSerialNumber())
        .model(model.getDescription())
        .weightLimit(model.getWeightCapacity())
        .batteryCapacity(entity.getBatteryCapacity())
        .state(entity.getState().getDescription())
        .build();
  }

  /**
   * To dto drone.
   *
   * @param entity the entity
   * @param medicationDtos the medication dtos
   * @return the drone dto
   */
  public static DroneDto toDto(final DroneEntity entity, final List<MedicationDto> medicationDtos) {
    final DroneModel model = entity.getModel();
    return DroneDto.builder()
        .serialNumber(entity.getSerialNumber())
        .model(model.getDescription())
        .weightLimit(model.getWeightCapacity())
        .batteryCapacity(entity.getBatteryCapacity())
        .state(entity.getState().getDescription())
        .medications(medicationDtos)
        .build();
  }
}
