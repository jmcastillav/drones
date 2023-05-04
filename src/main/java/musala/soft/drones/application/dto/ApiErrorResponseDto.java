package musala.soft.drones.application.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ApiErrorResponseDto(LocalDateTime timestamp, String message, HttpStatus status) {}
