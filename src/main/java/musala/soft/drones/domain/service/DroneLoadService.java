package musala.soft.drones.domain.service;

import java.util.List;
import musala.soft.drones.domain.entity.DroneLoadEntity;

/** The interface Drone load service. */
public interface DroneLoadService {

  /**
   * Gets drone load by id.
   *
   * @param droneId the drone id
   * @return the drone load by id
   */
  List<DroneLoadEntity> getDroneLoadById(long droneId);

  /**
   * Add drone load.
   *
   * @param droneId the drone id
   * @param medicamentIds the medicament ids
   */
  void addDroneLoad(long droneId, List<Long> medicamentIds);
}
