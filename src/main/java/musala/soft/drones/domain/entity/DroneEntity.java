package musala.soft.drones.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import musala.soft.drones.domain.exception.DroneException;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drone")
public class DroneEntity {

  public static final int MAX_SERIAL_NUMBER_LENGTH = 100;
  public static final int MAX_WEIGHT = 500;
  public static final int MINIMUM_BATTERY_PERCENTAGE = 25;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String serialNumber;

  private DroneModel model;

  @Builder.Default private Integer weightLimit = MAX_WEIGHT;

  @Builder.Default private Integer batteryCapacity = 100;

  private DroneState state;

  public enum DroneModel {
    LIGHTWEIGHT("Lightweight", 125),
    MIDDLEWEIGHT("Middleweight", 250),
    CRUISERWEIGHT("Cruiserweight", 375),
    HEAVYWEIGHT("Heavyweight", 500);

    private final String description;

    private final int weightCapacity;

    DroneModel(final String description, final int weightCapacity) {
      this.description = description;
      this.weightCapacity = weightCapacity;
    }

    public static DroneModel getDroneModel(final String description) {
      for (final DroneModel droneModel : DroneModel.values()) {
        if (droneModel.getDescription().equalsIgnoreCase(description)) {
          return droneModel;
        }
      }
      throw new DroneException(
          HttpStatus.BAD_REQUEST, "No matching DroneModel for description: " + description);
    }

    public String getDescription() {
      return this.description;
    }

    public int getWeightCapacity() {
      return this.weightCapacity;
    }
  }

  public enum DroneState {
    IDLE("Idle"),
    LOADING("Loading"),
    LOADED("Loaded"),
    DELIVERING("Delivering"),
    DELIVERED("Delivered"),
    RETURNING("Returning");

    private final String description;

    DroneState(final String description) {
      this.description = description;
    }

    public static DroneState getDroneState(final String description) {
      for (final DroneState droneState : DroneState.values()) {
        if (droneState.getDescription().equalsIgnoreCase(description)) {
          return droneState;
        }
      }
      throw new DroneException(
          HttpStatus.BAD_REQUEST, "No matching DroneState for description: " + description);
    }

    public String getDescription() {
      return this.description;
    }
  }
}
