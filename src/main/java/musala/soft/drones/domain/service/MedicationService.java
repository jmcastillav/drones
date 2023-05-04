package musala.soft.drones.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import musala.soft.drones.domain.entity.MedicationEntity;

/** The interface Medication service. */
public interface MedicationService {

  /**
   * Save medication entity.
   *
   * @param medication the medication
   * @return the medication entity
   */
  MedicationEntity save(MedicationEntity medication);

  /**
   * Find by code optional.
   *
   * @param code the code
   * @return the optional
   */
  Optional<MedicationEntity> findByCode(String code);

  /**
   * Find by codes list.
   *
   * @param codes the codes
   * @return the list
   */
  List<MedicationEntity> findByCodes(List<String> codes);

  /**
   * Find by id optional.
   *
   * @param id the id
   * @return the optional
   */
  Optional<MedicationEntity> findById(long id);

  /**
   * Find by ids list.
   *
   * @param ids the ids
   * @return the list
   */
  List<MedicationEntity> findByIds(Set<Long> ids);

  /**
   * Delete by code.
   *
   * @param code the code
   */
  void deleteByCode(String code);

  /**
   * Update medication entity.
   *
   * @param code the code
   * @param entity the entity
   * @return the medication entity
   */
  MedicationEntity update(String code, MedicationEntity entity);
}
