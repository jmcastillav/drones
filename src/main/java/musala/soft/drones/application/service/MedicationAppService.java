package musala.soft.drones.application.service;

import musala.soft.drones.application.dto.MedicationDto;

/** The interface Medication app service. */
public interface MedicationAppService {

  /**
   * Create medication dto.
   *
   * @param medicationDto the medication dto
   * @return the medication dto
   */
  MedicationDto create(MedicationDto medicationDto);

  /**
   * Find by code medication dto.
   *
   * @param code the code
   * @return the medication dto
   */
  MedicationDto findByCode(String code);

  /**
   * Update medication dto.
   *
   * @param code the code
   * @param medicationDto the medication dto
   * @return the medication dto
   */
  MedicationDto update(String code, MedicationDto medicationDto);

  /**
   * Delete by code.
   *
   * @param code the code
   */
  void deleteByCode(String code);
}
