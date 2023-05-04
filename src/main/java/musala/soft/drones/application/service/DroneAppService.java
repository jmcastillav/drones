package musala.soft.drones.application.service;

import java.util.List;
import musala.soft.drones.application.dto.DroneDto;

/** The interface Drone app service. */
public interface DroneAppService {

  /**
   * Create drone dto.
   *
   * @param droneDto the drone dto
   * @return the drone dto
   */
  DroneDto create(DroneDto droneDto);

  /**
   * Load drone drone dto.
   *
   * @param serialNumber the serial number
   * @param medicationCodes the medication codes
   * @return the drone dto
   */
  DroneDto loadDrone(String serialNumber, List<String> medicationCodes);

  /**
   * Gets drone.
   *
   * @param serialNumber the serial number
   * @return the drone
   */
  DroneDto getDrone(String serialNumber);

  /**
   * Gets available drones.
   *
   * @return the available drones
   */
  List<DroneDto> getAvailableDrones();
}
