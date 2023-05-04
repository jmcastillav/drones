package musala.soft.drones.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.application.dto.ApiErrorResponseDto;
import musala.soft.drones.application.dto.DroneBatteryLogDto;
import musala.soft.drones.application.service.AuditAppService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The type Audit controller. */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/log")
@Tag(name = "audit", description = "Endpoints for retrieving audit logs")
public class AuditController {

  private final AuditAppService auditAppService;

  /**
   * Gets drone battery log.
   *
   * @param startDate the start date
   * @param endDate the end date
   * @return the drone battery log
   */
  @Operation(summary = "Get drones batteries level per date range.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DroneBatteryLogDto.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class)))
      })
  @GetMapping("/drone/battery")
  public ResponseEntity<List<DroneBatteryLogDto>> getDroneBatteryLog(
      @RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE_TIME) final LocalDateTime startDate,
      @RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE_TIME) final LocalDateTime endDate) {
    return ResponseEntity.ok(auditAppService.getDroneBatteryByDateRange(startDate, endDate));
  }
}
