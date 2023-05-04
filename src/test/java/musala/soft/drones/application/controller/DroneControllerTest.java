package musala.soft.drones.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import musala.soft.drones.application.dto.DroneDto;
import musala.soft.drones.application.service.DroneAppService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class DroneControllerTest {

  @Mock private DroneAppService droneAppService;

  @InjectMocks private DroneController droneController;

  @Test
  void givenAValidInputDrone_whenRegisterDroneIsCalled_thenSaveNewDrone() {
    final DroneDto droneDto =
        new DroneDto("123", "model", 100, 100, "state", Collections.emptyList());
    when(droneAppService.create(droneDto)).thenReturn(droneDto);

    final ResponseEntity<DroneDto> responseEntity = droneController.registerDrone(droneDto);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(droneDto, responseEntity.getBody());
  }

  @Test
  void givenAValidInputMedications_whenLoadDroneIsCalled_thenLoadDroneWithItems() {
    final String serialNumber = "123";
    final List<String> medicationCodes = List.of("A", "B", "C");
    final DroneDto droneDto =
        new DroneDto("123", "model", 100, 100, "state", Collections.emptyList());
    when(droneAppService.loadDrone(serialNumber, medicationCodes)).thenReturn(droneDto);

    final ResponseEntity<DroneDto> responseEntity =
        droneController.loadDrone(serialNumber, medicationCodes);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(droneDto, responseEntity.getBody());
  }

  @Test
  void givenAValidInputDrone_whenGetDrone_thenReturnRespectiveDrone() {
    final String serialNumber = "123";
    final DroneDto droneDto =
        new DroneDto("123", "model", 100, 100, "state", Collections.emptyList());
    when(droneAppService.getDrone(serialNumber)).thenReturn(droneDto);

    final ResponseEntity<DroneDto> responseEntity = droneController.getDrone(serialNumber);

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(droneDto, responseEntity.getBody());
  }

  @Test
  void givenAValidRequest_whenGetAvailableDrones_thenReturnIdleDrones() {
    final List<DroneDto> droneDtos =
        List.of(
            new DroneDto("123", "model", 100, 100, "state1", Collections.emptyList()),
            new DroneDto("456", "model", 100, 100, "state2", Collections.emptyList()));
    when(droneAppService.getAvailableDrones()).thenReturn(droneDtos);

    final ResponseEntity<List<DroneDto>> responseEntity = droneController.getIdleDrone();

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(droneDtos, responseEntity.getBody());
  }
}
