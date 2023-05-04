package musala.soft.drones.domain.service;

import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import musala.soft.drones.domain.entity.DroneEntity;

/** The interface Drone service. */
public interface DroneService {

  /**
   * Save drone drone entity.
   *
   * @param droneEntity the drone entity
   * @return the drone entity
   */
  DroneEntity saveDrone(DroneEntity droneEntity);

  /**
   * Find by serial number optional.
   *
   * @param serialNumber the serial number
   * @return the optional
   */
  Optional<DroneEntity> findBySerialNumber(@NonNull String serialNumber);

  /**
   * Update drone drone entity.
   *
   * @param droneEntity the drone entity
   * @return the drone entity
   */
  DroneEntity updateDrone(DroneEntity droneEntity);

  /**
   * Gets available drones.
   *
   * @return the available drones
   */
  List<DroneEntity> getAvailableDrones();

  /**
   * Gets all drones.
   *
   * @return the all drones
   */
  List<DroneEntity> getAllDrones();
}
