package musala.soft.drones.application.mapper;

import musala.soft.drones.application.dto.MedicationDto;
import musala.soft.drones.domain.entity.MedicationEntity;

/** The type Medication mapper. */
public final class MedicationMapper {

  /**
   * To dto medication.
   *
   * @param entity the entity
   * @return the medication dto
   */
  public static MedicationDto toDto(final MedicationEntity entity) {
    return MedicationDto.builder()
        .name(entity.getName())
        .weight(entity.getWeight())
        .code(entity.getCode())
        .imageUrl(entity.getImageUrl())
        .build();
  }

  /**
   * To entity medication.
   *
   * @param dto the dto
   * @return the medication entity
   */
  public static MedicationEntity toEntity(final MedicationDto dto) {
    return MedicationEntity.builder()
        .name(dto.name())
        .weight(dto.weight())
        .code(dto.code())
        .imageUrl(dto.imageUrl())
        .build();
  }
}
