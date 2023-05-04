package musala.soft.drones.application.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.application.dto.MedicationDto;
import musala.soft.drones.application.service.MedicationAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Medication controller. */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/medication")
public class MedicationController {

  private final MedicationAppService medicationAppService;

  /**
   * Create medication.
   *
   * @param medicationDto the medication dto
   * @return the response entity
   */
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
  @GetMapping("/{code}")
  public ResponseEntity<MedicationDto> getMedication(@PathVariable("code") final String code) {
    return ResponseEntity.ok(medicationAppService.findByCode(code));
  }

  /**
   * Update medication.
   *
   * @param code the code
   * @param medicationDto the medication dto
   * @return the response entity
   */
  @PutMapping("/{code}")
  public ResponseEntity<MedicationDto> updateMedication(
      @PathVariable("code") final String code,
      @RequestBody @Valid final MedicationDto medicationDto) {
    return ResponseEntity.ok(medicationAppService.update(code, medicationDto));
  }

  /**
   * Delete medication.
   *
   * @param code the code
   * @return the response entity
   */
  @DeleteMapping("/{code}")
  public ResponseEntity<Void> deleteMedication(@PathVariable("code") final String code) {
    medicationAppService.deleteByCode(code);
    return ResponseEntity.ok().build();
  }
}
