package musala.soft.drones.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
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
        () -> assertEquals(response.getCode(), medicationDto.getCode()),
        () -> assertEquals(response.getWeight(), medicationDto.getWeight()),
        () -> assertEquals(response.getName(), medicationDto.getName()),
        () -> assertEquals(response.getImageUrl(), medicationDto.getImageUrl()));
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
        () -> assertEquals(response.getCode(), medicationEntity.getCode()),
        () -> assertEquals(response.getWeight(), medicationEntity.getWeight()),
        () -> assertEquals(response.getName(), medicationEntity.getName()),
        () -> assertEquals(response.getImageUrl(), medicationEntity.getImageUrl()));
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

    final MedicationDto response = medicationAppService.update("ABC123", medicationUpdate);
    assertAll(
        "The returned MedicationDto is equal to the input one",
        () -> assertEquals(response.getCode(), medicationEntity.getCode()),
        () -> assertEquals(response.getImageUrl(), medicationEntity.getImageUrl()),
        () -> assertEquals(response.getWeight(), medicationUpdate.getWeight()),
        () -> assertEquals(response.getName(), medicationUpdate.getName()));
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
}
