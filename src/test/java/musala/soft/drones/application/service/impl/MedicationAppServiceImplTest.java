package musala.soft.drones.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import musala.soft.drones.application.dto.MedicationDto;
import musala.soft.drones.application.service.MedicationAppService;
import musala.soft.drones.domain.entity.MedicationEntity;
import musala.soft.drones.domain.exception.MedicationException;
import musala.soft.drones.domain.repository.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class MedicationAppServiceImplTest {

  @Autowired private MedicationAppService medicationAppService;

  @Autowired private MedicationRepository medicationRepository;

  @BeforeEach
  void setUp() {
    medicationRepository.deleteAll();
  }

  @Test
  void givenValidMedicationDto_WhenCreateMedication_ThenReturnMedicationDtoWithSameValues()
      throws MalformedURLException {

    final MedicationDto medicationDto =
        MedicationDto.builder()
            .name("nameTest")
            .weight(100)
            .code("ABC123")
            .imageUrl(new URL("https://test.com/image.jpg"))
            .build();
    final MedicationDto response = medicationAppService.create(medicationDto);
    assertAll(
        "The returned MedicationDto is equal to the input one",
        () -> assertEquals(response.code(), medicationDto.code()),
        () -> assertEquals(response.weight(), medicationDto.weight()),
        () -> assertEquals(response.name(), medicationDto.name()),
        () -> assertEquals(response.imageUrl(), medicationDto.imageUrl()));
  }

  @Test
  void givenMedicationCode_whenFindByCodeIsCalled_thenReturnsMatchingMedicationDto()
      throws MalformedURLException {
    final MedicationEntity medicationEntity =
        MedicationEntity.builder()
            .name("nameTest")
            .weight(100)
            .code("ABC123")
            .imageUrl(new URL("https://test.com/image.jpg"))
            .build();
    medicationRepository.save(medicationEntity);
    final MedicationDto response = medicationAppService.findByCode("ABC123");
    assertAll(
        "The returned MedicationDto is equal to the input one",
        () -> assertEquals(response.code(), medicationEntity.getCode()),
        () -> assertEquals(response.weight(), medicationEntity.getWeight()),
        () -> assertEquals(response.name(), medicationEntity.getName()),
        () -> assertEquals(response.imageUrl(), medicationEntity.getImageUrl()));
  }

  @Test
  void givenMedicationWithInvalidCode_whenFindByCodeIsCalled_thenThrowException() {
    assertThrows(MedicationException.class, () -> medicationAppService.findByCode("ABC123"));
  }

  @Test
  void givenMedicationUpdateEntity_whenUpdateIsCalled_thenReturnsMedicationWithUpdateData()
      throws MalformedURLException {
    final MedicationEntity medicationEntity =
        MedicationEntity.builder()
            .name("nameTest")
            .weight(100)
            .code("ABC123")
            .imageUrl(new URL("https://test.com/image.jpg"))
            .build();
    medicationRepository.save(medicationEntity);

    final MedicationDto medicationUpdate =
        MedicationDto.builder().name("updateName").weight(200).build();

    final MedicationDto response = medicationAppService.patch("ABC123", medicationUpdate);
    assertAll(
        "The returned MedicationDto is equal to the input one",
        () -> assertEquals(response.code(), medicationEntity.getCode()),
        () -> assertEquals(response.imageUrl(), medicationEntity.getImageUrl()),
        () -> assertEquals(response.weight(), medicationUpdate.weight()),
        () -> assertEquals(response.name(), medicationUpdate.name()));
  }

  @Test
  void givenMedicationCode_whenDeleteByCodeIsCalled_thenDeleteMedication()
      throws MalformedURLException {
    final MedicationEntity medicationEntity =
        MedicationEntity.builder()
            .name("nameTest")
            .weight(100)
            .code("ABC123")
            .imageUrl(new URL("https://test.com/image.jpg"))
            .build();
    medicationRepository.save(medicationEntity);
    medicationAppService.deleteByCode("ABC123");
    assertTrue(medicationRepository.findByCode("ABC123").isEmpty());
  }

  @Test
  void whenFindAllIsCalled_thenReturnAllMedicaments() throws MalformedURLException {
    final MedicationEntity medicationEntity =
        MedicationEntity.builder()
            .name("nameTest")
            .weight(100)
            .code("ABC123")
            .imageUrl(new URL("https://test.com/image.jpg"))
            .build();
    final MedicationEntity medicationEntity2 =
        MedicationEntity.builder()
            .name("nameTest2")
            .weight(150)
            .code("DEF456")
            .imageUrl(new URL("https://test.com/image2.jpg"))
            .build();
    final List<MedicationEntity> medications = List.of(medicationEntity, medicationEntity2);
    medicationRepository.saveAll(medications);
    final List<MedicationDto> response = medicationAppService.findAll();
    assertTrue(
        response.stream()
            .map(MedicationDto::code)
            .toList()
            .containsAll(medications.stream().map(MedicationEntity::getCode).toList()));
  }
}
