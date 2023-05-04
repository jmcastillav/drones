package musala.soft.drones.application.dto;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class MedicationDto {

  private final String name;
  private final Integer weight;
  private final String code;
  private final URL imageUrl;
}
