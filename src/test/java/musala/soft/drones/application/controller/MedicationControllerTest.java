package musala.soft.drones.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;
import musala.soft.drones.application.dto.MedicationDto;
import musala.soft.drones.application.service.MedicationAppService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class MedicationControllerTest {
  @Mock private MedicationAppService medicationAppService;
  @InjectMocks private MedicationController medicationController;

  @Test
  public void givenAValidMedication_whenCreateMedicationIsCalled_thenSaveNewMedication() {
    final MedicationDto medicationDto = createTestMedicationDto();

    when(medicationAppService.create(medicationDto)).thenReturn(medicationDto);

    final ResponseEntity<MedicationDto> response =
        medicationController.createMedication(medicationDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(medicationDto, response.getBody());
  }

  @Test
  public void givenAValidMedication_whenGetMedicationIsCalled_thenReturnMedication() {
    final MedicationDto medicationDto = createTestMedicationDto();

    when(medicationAppService.findByCode("ABC123")).thenReturn(medicationDto);

    final ResponseEntity<MedicationDto> response = medicationController.getMedication("ABC123");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(medicationDto, response.getBody());
  }

  @Test
  public void givenAValidMedication_whenUpdateMedicationIsCalled_thenUpdateNewMedicationIndo() {
    final MedicationDto medicationDto = createTestMedicationDto();

    when(medicationAppService.update(eq("ABC123"), any())).thenReturn(medicationDto);

    final ResponseEntity<MedicationDto> response =
        medicationController.updateMedication("ABC123", medicationDto);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(medicationDto, response.getBody());
  }

  @Test
  public void givenAValidMedication_whenDeleteMedicationIsCalled_thenDeleteMedication() {
    final ResponseEntity<Void> response = medicationController.deleteMedication("ABC123");

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNull(response.getBody());
  }

  private MedicationDto createTestMedicationDto() {
    try {
      return MedicationDto.builder()
          .name("Test Medication")
          .weight(50)
          .code("ABC123")
          .imageUrl(new URL("https://example.com/image.jpg"))
          .build();
    } catch (final MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
