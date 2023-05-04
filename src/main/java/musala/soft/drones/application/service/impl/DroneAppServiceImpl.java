package musala.soft.drones.application.service.impl;

import static musala.soft.drones.application.mapper.DroneMapper.toDto;
import static musala.soft.drones.application.mapper.DroneMapper.toEntity;
import static musala.soft.drones.domain.entity.DroneEntity.MINIMUM_BATTERY_PERCENTAGE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.application.dto.DroneDto;
import musala.soft.drones.application.mapper.DroneMapper;
import musala.soft.drones.application.mapper.MedicationMapper;
import musala.soft.drones.application.service.DroneAppService;
import musala.soft.drones.domain.entity.DroneEntity;
import musala.soft.drones.domain.entity.DroneEntity.DroneState;
import musala.soft.drones.domain.entity.DroneLoadEntity;
import musala.soft.drones.domain.entity.MedicationEntity;
import musala.soft.drones.domain.exception.DroneException;
import musala.soft.drones.domain.exception.MedicationException;
import musala.soft.drones.domain.service.DroneLoadService;
import musala.soft.drones.domain.service.DroneService;
import musala.soft.drones.domain.service.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DroneAppServiceImpl implements DroneAppService {

  private final DroneService droneService;
  private final MedicationService medicationService;
  private final DroneLoadService droneLoadService;

  @Override
  public DroneDto create(final DroneDto droneDto) {
    return toDto(droneService.saveDrone(toEntity(droneDto)));
  }

  @Override
  public DroneDto loadDrone(final String serialNumber, final List<String> medicationCodes) {

    log.info("Loading Drone {} with Medicament's {}", serialNumber, medicationCodes);

    final DroneEntity drone = getDroneEntityBySerialNumber(serialNumber);

    if (drone.getBatteryCapacity() < MINIMUM_BATTERY_PERCENTAGE) {
      throw new DroneException(
          HttpStatus.CONFLICT,
          String.format("Drone Battery %s is sufficient for loading", drone.getBatteryCapacity()));
    }
    drone.setState(DroneState.LOADING);
    droneService.updateDrone(drone);

    final List<MedicationEntity> newLoadMedications =
        medicationService.findByCodes(medicationCodes);

    missingMedications(medicationCodes, newLoadMedications);

    final List<MedicationEntity> currentLoadMedications = getDroneLoadMedication(drone);

    final List<MedicationEntity> totalLoadMedication = new ArrayList<>(currentLoadMedications);
    totalLoadMedication.addAll(newLoadMedications);

    validateDroneWeight(drone, totalLoadMedication);

    droneLoadService.addDroneLoad(
        drone.getId(), newLoadMedications.stream().map(MedicationEntity::getId).toList());

    drone.setState(DroneState.LOADED);
    droneService.updateDrone(drone);

    return toDto(
        droneService.updateDrone(drone),
        totalLoadMedication.stream().map(MedicationMapper::toDto).toList());
  }

  @Override
  public DroneDto getDrone(@NonNull final String serialNumber) {
    final DroneEntity drone = getDroneEntityBySerialNumber(serialNumber);

    final List<MedicationEntity> loadMedications = getDroneLoadMedication(drone);

    return toDto(drone, loadMedications.stream().map(MedicationMapper::toDto).toList());
  }

  @Override
  public List<DroneDto> getAvailableDrones() {
    return droneService.getAvailableDrones().stream()
        .map(DroneMapper::toDto)
        .collect(Collectors.toList());
  }

  private DroneEntity getDroneEntityBySerialNumber(final String serialNumber) {
    return droneService
        .findBySerialNumber(serialNumber)
        .orElseThrow(
            () ->
                new DroneException(
                    HttpStatus.BAD_REQUEST,
                    String.format(
                        "A Drone with the serialNumber %s does not exists.", serialNumber)));
  }

  private List<MedicationEntity> getDroneLoadMedication(final DroneEntity drone) {
    final List<DroneLoadEntity> droneLoad = droneLoadService.getDroneLoadById(drone.getId());
    return droneLoad.stream()
        .map(
            droneLoadEntity ->
                medicationService
                    .findById(droneLoadEntity.getMedicationId())
                    .orElseThrow(
                        () ->
                            new MedicationException(
                                HttpStatus.INTERNAL_SERVER_ERROR, "Could not find medication")))
        .toList();
  }

  private void missingMedications(
      final List<String> medicationCodes, final List<MedicationEntity> medicationEntities) {

    final List<String> medicationEntitiesCodes =
        medicationEntities.stream().map(MedicationEntity::getCode).toList();

    final List<String> missingMedications =
        medicationCodes.stream()
            .filter(medicationDto -> !medicationEntitiesCodes.contains(medicationDto))
            .toList();

    if (!missingMedications.isEmpty()) {
      throw new DroneException(
          HttpStatus.BAD_REQUEST,
          String.format(
              "The following Medications are not registered in the system: [%s].",
              missingMedications));
    }
  }

  private void validateDroneWeight(
      final DroneEntity drone, final List<MedicationEntity> medications) {
    final int totalWeight = medications.stream().mapToInt(MedicationEntity::getWeight).sum();
    if (totalWeight > drone.getWeightLimit()) {
      throw new DroneException(
          HttpStatus.CONFLICT,
          String.format(
              "The medications weight %s surpasses the Drone limit %s",
              totalWeight, drone.getWeightLimit()));
    }
  }
}
