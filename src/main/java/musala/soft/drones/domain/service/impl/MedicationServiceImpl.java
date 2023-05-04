package musala.soft.drones.domain.service.impl;

import static musala.soft.drones.domain.entity.MedicationEntity.CODE_FORMAT;
import static musala.soft.drones.domain.entity.MedicationEntity.NAME_FORMAT;
import static musala.soft.drones.domain.helper.MapperHelper.setIfNotNull;
import static musala.soft.drones.domain.helper.MapperHelper.validateFormat;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.domain.entity.MedicationEntity;
import musala.soft.drones.domain.exception.MedicationException;
import musala.soft.drones.domain.repository.MedicationRepository;
import musala.soft.drones.domain.service.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService {

  private final MedicationRepository medicationRepository;

  @Override
  public MedicationEntity save(@NonNull final MedicationEntity medication) {
    log.info("Saving new Medication {}", medication);
    final StringBuilder problems = new StringBuilder();
    if (!validateFormat(CODE_FORMAT, medication.getCode())) {
      problems.append(
          String.format(
              "%nMedication code [%s] does not have a valid format.", medication.getCode()));
    }
    if (!validateFormat(NAME_FORMAT, medication.getName())) {
      problems.append(
          String.format(
              "%nMedication name [%s] does not have a valid format.", medication.getName()));
    }
    if (Objects.isNull(medication.getId())
        && medicationRepository.findByCode(medication.getCode()).isPresent()) {
      problems.append(
          String.format(
              "%nMedication with code [%s] is already registered.", medication.getCode()));
    }
    if (!problems.isEmpty()) {
      throw new MedicationException(
          HttpStatus.BAD_REQUEST, "Could not save medication. Causes: " + problems);
    }
    return medicationRepository.save(medication);
  }

  @Override
  public Optional<MedicationEntity> findByCode(@NonNull final String code) {
    log.info("Seeking medication by code {}", code);
    return medicationRepository.findByCode(code);
  }

  @Override
  public List<MedicationEntity> findByCodes(@NonNull final List<String> codes) {
    log.info("Seeking medications by codes {}", codes);
    return medicationRepository.findAllByCodeIn(codes);
  }

  @Override
  public Optional<MedicationEntity> findById(final long id) {
    log.info("Seeking medication by id {}", id);
    return medicationRepository.findById(id);
  }

  @Override
  public List<MedicationEntity> findByIds(@NonNull final Set<Long> ids) {
    log.info("Seeking medications by ids {}", ids);
    return medicationRepository.findAllByIdIn(ids);
  }

  @Override
  public void deleteByCode(@NonNull final String code) {
    log.info("Deleting medication by code {}", code);
    final MedicationEntity medication =
        findByCode(code)
            .orElseThrow(
                () ->
                    new MedicationException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Could not find medication with code: " + code));

    medicationRepository.delete(medication);
  }

  @Override
  public MedicationEntity update(
      @NonNull final String code, @NonNull final MedicationEntity medication) {
    log.info("Updating Medication with code {} and update data {}", code, medication);
    final Optional<MedicationEntity> currentMedication = findByCode(code);
    if (currentMedication.isEmpty()) {
      return save(medication);
    }
    final MedicationEntity updateMedication = currentMedication.get();
    setIfNotNull(medication.getName(), updateMedication::setName);
    setIfNotNull(medication.getWeight(), updateMedication::setWeight);
    setIfNotNull(medication.getCode(), updateMedication::setCode);
    setIfNotNull(medication.getImageUrl(), updateMedication::setImageUrl);

    return save(updateMedication);
  }
}
