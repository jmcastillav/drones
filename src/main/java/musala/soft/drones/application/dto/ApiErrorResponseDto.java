package musala.soft.drones.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class ApiErrorResponseDto {

  private final LocalDateTime timestamp;
  private final String message;
  private final HttpStatus status;
}
