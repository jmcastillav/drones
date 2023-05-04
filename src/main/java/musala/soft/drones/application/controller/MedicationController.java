package musala.soft.drones.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.application.dto.ApiErrorResponseDto;
import musala.soft.drones.application.dto.MedicationDto;
import musala.soft.drones.application.service.MedicationAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Medication controller. */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/medication")
@Tag(name = "medication", description = "Endpoints for managing medications.")
public class MedicationController {

  private final MedicationAppService medicationAppService;

  /**
   * Create medication.
   *
   * @param medicationDto the medication dto
   * @return the response entity
   */
  @Operation(summary = "Create medication.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MedicationDto.class))),
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
  public ResponseEntity<MedicationDto> createMedication(
      @RequestBody @Valid final MedicationDto medicationDto) {
    return ResponseEntity.ok(medicationAppService.create(medicationDto));
  }

  /**
   * Gets medication.
   *
   * @param code the code
   * @return the medication
   */
  @Operation(summary = "Get medication by code")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MedicationDto.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Incorrect code",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Medication not found",
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
  @GetMapping("/{code}")
  public ResponseEntity<MedicationDto> getMedication(@PathVariable("code") final String code) {
    return ResponseEntity.ok(medicationAppService.findByCode(code));
  }

  /**
   * Gets all medications.
   *
   * @return the all medications
   */
  @Operation(summary = "Get all medications")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MedicationDto.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class)))
      })
  @GetMapping
  public ResponseEntity<List<MedicationDto>> getAllMedications() {
    return ResponseEntity.ok(medicationAppService.findAll());
  }

  /**
   * Update medication.
   *
   * @param code the code
   * @param medicationDto the medication dto
   * @return the response entity
   */
  @Operation(summary = "Patch medication by code")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MedicationDto.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Incorrect code, or bad body",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Medication not found",
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
  @PatchMapping("/{code}")
  public ResponseEntity<MedicationDto> patchMedication(
      @PathVariable("code") final String code,
      @RequestBody @Valid final MedicationDto medicationDto) {
    return ResponseEntity.ok(medicationAppService.patch(code, medicationDto));
  }

  /**
   * Delete medication.
   *
   * @param code the code
   * @return the response entity
   */
  @Operation(summary = "Deletes a medication by code")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "400",
            description = "Incorrect code",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Medication not found",
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
  @DeleteMapping("/{code}")
  public ResponseEntity<Void> deleteMedication(@PathVariable("code") final String code) {
    medicationAppService.deleteByCode(code);
    return ResponseEntity.ok().build();
  }
}
