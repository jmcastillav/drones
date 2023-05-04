package musala.soft.drones.application.controller.handler;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.application.dto.ApiErrorResponseDto;
import musala.soft.drones.domain.exception.DroneException;
import musala.soft.drones.domain.exception.LogException;
import musala.soft.drones.domain.exception.MedicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DroneException.class)
  protected ResponseEntity<ApiErrorResponseDto> handleInternalException(
      final DroneException exception) {
    log.error("DroneException throw with cause: {}", exception.getMessage());
    return ResponseEntity.status(exception.getHttpStatus())
        .body(
            ApiErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .status(exception.getHttpStatus())
                .build());
  }

  @ExceptionHandler(MedicationException.class)
  protected ResponseEntity<ApiErrorResponseDto> handleInternalException(
      final MedicationException exception) {
    log.error("MedicationException throw with cause: {}", exception.getMessage());
    return ResponseEntity.status(exception.getHttpStatus())
        .body(
            ApiErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .status(exception.getHttpStatus())
                .build());
  }

  @ExceptionHandler(LogException.class)
  protected ResponseEntity<ApiErrorResponseDto> handleInternalException(
      final LogException exception) {
    log.error("LogException throw with cause: {}", exception.getMessage());
    return ResponseEntity.status(exception.getHttpStatus())
        .body(
            ApiErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .status(exception.getHttpStatus())
                .build());
  }
}
