package musala.soft.drones.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import musala.soft.drones.domain.entity.MedicationEntity;
import musala.soft.drones.domain.exception.MedicationException;
import musala.soft.drones.domain.repository.MedicationRepository;
import musala.soft.drones.domain.service.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MedicationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MedicationServiceImplTest {

  @MockBean private MedicationRepository medicationRepository;

  @Autowired private MedicationService medicationService;

  @BeforeEach
  void setUp() {
    medicationRepository.deleteAll();
  }

  @Test
  void givenMedicationEntity_whenSaveMethodCalled_thenReturnMedicationEntity()
      throws MalformedURLException {

    final MedicationEntity medicationEntity = new MedicationEntity();
    when(medicationRepository.save(Mockito.any())).thenReturn(medicationEntity);

    assertSame(
        medicationEntity,
        medicationService.save(new MedicationEntity(1L, "U", 3, "U", new URL("https://test.com"))));
    verify(medicationRepository).save(Mockito.any());
  }

  @Test
  void givenInvalidMedicationEntity_whenSaveMethodCalled_thenThrowMedicationException() {

    when(medicationRepository.save(Mockito.any())).thenReturn(new MedicationEntity());

    assertThrows(
        MedicationException.class,
        () ->
            medicationService.save(
                MedicationEntity.builder()
                    .name("testName@#$")
                    .weight(3)
                    .code("U")
                    .imageUrl(new URL("https://test.com"))
                    .build()));
  }

  @Test
  void givenInvalidCodeFormatMedicationEntity_whenSaveMethodCalled_thenThrowMedicationException() {

    final Optional<MedicationEntity> ofResult = Optional.of(new MedicationEntity());
    when(medicationRepository.findByCode(Mockito.any())).thenReturn(ofResult);

    final Optional<MedicationEntity> actualFindByCodeResult = medicationService.findByCode("Code");

    assertSame(ofResult, actualFindByCodeResult);
    assertTrue(actualFindByCodeResult.isPresent());
    verify(medicationRepository).findByCode(Mockito.any());
  }

  @Test
  void
      givenListOfMedicationEntityCodes_whenFindByCodesMethodCalled_thenReturnListWithMatchingMedicationEntity() {

    final ArrayList<MedicationEntity> medicationEntityList = new ArrayList<>();
    when(medicationRepository.findAllByCodeIn(Mockito.any())).thenReturn(medicationEntityList);

    final List<MedicationEntity> actualFindByCodesResult =
        medicationService.findByCodes(new ArrayList<>());

    assertSame(medicationEntityList, actualFindByCodesResult);
    assertTrue(actualFindByCodesResult.isEmpty());
    verify(medicationRepository).findAllByCodeIn(Mockito.any());
  }

  @Test
  void givenMedicationId_whenFindByIdMethodCalled_thenReturnOptionalWithMatchingMedicationEntity() {

    final Optional<MedicationEntity> ofResult = Optional.of(new MedicationEntity());
    when(medicationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    final Optional<MedicationEntity> actualFindByIdResult = medicationService.findById(1L);

    assertSame(ofResult, actualFindByIdResult);
    assertTrue(actualFindByIdResult.isPresent());
    verify(medicationRepository).findById(Mockito.<Long>any());
  }

  @Test
  void
      givenSetOfMedicationIds_whenFindByIdsMethodCalled_thenReturnListWithMatchingMedicationEntity() {

    final ArrayList<MedicationEntity> medicationEntityList = new ArrayList<>();
    when(medicationRepository.findAllByIdIn(Mockito.any())).thenReturn(medicationEntityList);

    final List<MedicationEntity> actualFindByIdsResult =
        medicationService.findByIds(new HashSet<>());

    assertSame(medicationEntityList, actualFindByIdsResult);
    assertTrue(actualFindByIdsResult.isEmpty());
    verify(medicationRepository).findAllByIdIn(Mockito.any());
  }

  @Test
  void givenMedicationCode_whenDeleteByCodeMethodCalled_thenDeleteMatchingMedicationEntity() {

    doNothing().when(medicationRepository).delete(Mockito.any());
    when(medicationRepository.findByCode(Mockito.any()))
        .thenReturn(Optional.of(new MedicationEntity()));

    medicationService.deleteByCode("Code");

    verify(medicationRepository).findByCode(Mockito.any());
    verify(medicationRepository).delete(Mockito.any());
  }

  @Test
  void givenNonExistentMedicationCode_whenDeleteByCodeMethodCalled_thenThrowMedicationException() {

    doNothing().when(medicationRepository).delete(Mockito.any());
    when(medicationRepository.findByCode(Mockito.any())).thenReturn(Optional.empty());

    assertThrows(MedicationException.class, () -> medicationService.deleteByCode("Code"));
    verify(medicationRepository).findByCode(Mockito.any());
  }

  @Test
  void
      givenMedicationCodeAndMedicationToUpdate_whenUpdateMethodCalled_thenUpdateMatchingMedication()
          throws MalformedURLException {

    when(medicationRepository.findByCode(Mockito.any())).thenReturn(Optional.empty());
    final MedicationEntity update =
        MedicationEntity.builder()
            .name("testName")
            .weight(3)
            .code("U")
            .imageUrl(new URL("https://test.com"))
            .build();
    when(medicationRepository.save(Mockito.any())).thenReturn(update);
    assertSame(update, medicationService.update("Code", update));
  }

  @Test
  void givenValidMedicationEntityAndCode_whenUpdate_thenReturnsUpdatedEntity()
      throws MalformedURLException {
    when(medicationRepository.save(Mockito.any())).thenReturn(new MedicationEntity());
    when(medicationRepository.findByCode(Mockito.any()))
        .thenReturn(
            Optional.of(
                MedicationEntity.builder()
                    .name("testName")
                    .weight(3)
                    .code("U")
                    .imageUrl(new URL("https://test.com"))
                    .build()));

    assertThrows(
        MedicationException.class, () -> medicationService.update("Code", new MedicationEntity()));
  }
}
