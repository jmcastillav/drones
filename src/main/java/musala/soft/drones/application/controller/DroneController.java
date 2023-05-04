package musala.soft.drones.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.application.dto.ApiErrorResponseDto;
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
@Tag(name = "drone", description = "Endpoints for managing drones.")
public class DroneController {

  private final DroneAppService droneAppService;

  /**
   * Register drone.
   *
   * @param droneDto the drone dto
   * @return the response entity
   */
  @Operation(summary = "Create Drone.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DroneDto.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class)))
      })
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
  @Operation(summary = "Loads a drone with medications.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DroneDto.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid code or Body",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class)))
      })
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
  @Operation(summary = "Get drone by serial number.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DroneDto.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid serial number",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Drone not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class)))
      })
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
  @Operation(summary = "Get idle drones.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DroneDto.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content)
      })
  @GetMapping("/available")
  public ResponseEntity<List<DroneDto>> getIdleDrone() {
    return ResponseEntity.ok(droneAppService.getAvailableDrones());
  }
}
