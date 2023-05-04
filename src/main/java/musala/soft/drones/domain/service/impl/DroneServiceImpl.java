package musala.soft.drones.domain.service.impl;

import static musala.soft.drones.domain.entity.DroneEntity.MAX_SERIAL_NUMBER_LENGTH;
import static musala.soft.drones.domain.entity.DroneEntity.MAX_WEIGHT;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.domain.entity.DroneEntity;
import musala.soft.drones.domain.entity.DroneEntity.DroneState;
import musala.soft.drones.domain.exception.DroneException;
import musala.soft.drones.domain.repository.DroneRepository;
import musala.soft.drones.domain.service.DroneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {

  private final DroneRepository droneRepository;

  @Override
  public DroneEntity saveDrone(@NonNull final DroneEntity drone) {
    validateDroneInput(drone);
    if (findBySerialNumber(drone.getSerialNumber()).isPresent()) {
      throw new DroneException(
          HttpStatus.CONFLICT,
          String.format(
              "A drone with the serial number [%s] already exists.", drone.getSerialNumber()));
    }
    drone.setState(DroneState.IDLE);
    return droneRepository.save(drone);
  }

  @Override
  public Optional<DroneEntity> findBySerialNumber(@NonNull final String serialNumber) {
    return droneRepository.findBySerialNumber(serialNumber);
  }

  @Override
  public DroneEntity updateDrone(@NonNull final DroneEntity droneEntity) {
    if (droneRepository.findById(droneEntity.getId()).isEmpty()) {
      throw new DroneException(
          HttpStatus.BAD_REQUEST,
          String.format("A drone with id [%s] does not exists.", droneEntity.getId()));
    }
    return droneRepository.save(droneEntity);
  }

  @Override
  public List<DroneEntity> getAvailableDrones() {
    return droneRepository.findAllByStateIs(DroneState.IDLE);
  }

  @Override
  public List<DroneEntity> getAllDrones() {
    return droneRepository.findAll();
  }

  private void validateDroneInput(final DroneEntity drone) {
    if (Objects.isNull(drone.getSerialNumber()) || drone.getSerialNumber().isBlank()) {
      throw new DroneException(HttpStatus.BAD_REQUEST, "Serial number is mandatory");
    }
    if (drone.getSerialNumber().length() > MAX_SERIAL_NUMBER_LENGTH) {
      throw new DroneException(
          HttpStatus.BAD_REQUEST,
          String.format("Serial number is larger than %s characters", MAX_SERIAL_NUMBER_LENGTH));
    }
    if (drone.getWeightLimit() > MAX_WEIGHT) {
      throw new DroneException(
          HttpStatus.BAD_REQUEST, String.format("Max supported weight limit is %s g", MAX_WEIGHT));
    }
  }
}
