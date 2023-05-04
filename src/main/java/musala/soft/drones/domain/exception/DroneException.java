package musala.soft.drones.domain.exception;

import java.io.Serial;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DroneException extends RuntimeException {

  @Serial private static final long serialVersionUID = 42L;

  private final HttpStatus httpStatus;

  public DroneException(final HttpStatus httpStatus, final String message) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
