package musala.soft.drones.application.service.impl;

import static musala.soft.drones.application.mapper.MedicationMapper.toDto;
import static musala.soft.drones.application.mapper.MedicationMapper.toEntity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musala.soft.drones.application.dto.MedicationDto;
import musala.soft.drones.application.mapper.MedicationMapper;
import musala.soft.drones.application.service.MedicationAppService;
import musala.soft.drones.domain.exception.MedicationException;
import musala.soft.drones.domain.service.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MedicationAppServiceImpl implements MedicationAppService {

  private final MedicationService medicationService;

  @Override
  public MedicationDto create(final MedicationDto medicationDto) {
    return toDto(medicationService.save(toEntity(medicationDto)));
  }

  @Override
  public MedicationDto findByCode(final String code) {
    return toDto(
        medicationService
            .findByCode(code)
            .orElseThrow(
                () ->
                    new MedicationException(
                        HttpStatus.NOT_FOUND, "Could not find medication with code: " + code)));
  }

  @Override
  public MedicationDto patch(final String code, final MedicationDto medicationDto) {
    return toDto(medicationService.update(code, toEntity(medicationDto)));
  }

  @Override
  public void deleteByCode(final String code) {
    medicationService.deleteByCode(code);
  }

  @Override
  public List<MedicationDto> findAll() {
    return medicationService.findAll().stream().map(MedicationMapper::toDto).toList();
  }
}
