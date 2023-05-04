package musala.soft.drones.application.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.application.dto.DroneDto;
import musala.soft.drones.application.service.DroneAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Drone controller. */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/drone")
public class DroneController {

  private final DroneAppService droneAppService;

  /**
   * Register drone.
   *
   * @param droneDto the drone dto
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<DroneDto> registerDrone(@RequestBody final DroneDto droneDto) {
    return ResponseEntity.ok(droneAppService.create(droneDto));
  }

  /**
   * Load drone.
   *
   * @param serialNumber the serial number
   * @param medicationCodes the medication codes
   * @return the response entity
   */
  @PostMapping("/{serialNumber}/load")
  public ResponseEntity<DroneDto> loadDrone(
      @PathVariable("serialNumber") final String serialNumber,
      @RequestBody final List<String> medicationCodes) {
    return ResponseEntity.ok(droneAppService.loadDrone(serialNumber, medicationCodes));
  }

  /**
   * Gets drone.
   *
   * @param serialNumber the serial number
   * @return the drone
   */
  @GetMapping("/{serialNumber}")
  public ResponseEntity<DroneDto> getDrone(
      @PathVariable("serialNumber") final String serialNumber) {
    return ResponseEntity.ok(droneAppService.getDrone(serialNumber));
  }

  /**
   * Gets available drones.
   *
   * @return the drone
   */
  @GetMapping("/available")
  public ResponseEntity<List<DroneDto>> getDrone() {
    return ResponseEntity.ok(droneAppService.getAvailableDrones());
  }
}
