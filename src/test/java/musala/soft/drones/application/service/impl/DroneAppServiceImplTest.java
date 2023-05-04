package musala.soft.drones.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import musala.soft.drones.application.dto.DroneDto;
import musala.soft.drones.application.service.DroneAppService;
import musala.soft.drones.domain.entity.DroneEntity;
import musala.soft.drones.domain.entity.DroneEntity.DroneModel;
import musala.soft.drones.domain.entity.DroneEntity.DroneState;
import musala.soft.drones.domain.entity.MedicationEntity;
import musala.soft.drones.domain.exception.DroneException;
import musala.soft.drones.domain.repository.DroneRepository;
import musala.soft.drones.domain.repository.MedicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DroneAppServiceImplTest {

  @Autowired private DroneAppService droneAppService;
  @Autowired private MedicationRepository medicationRepository;
  @Autowired private DroneRepository droneRepository;

  @BeforeEach
  void setUp() {
    medicationRepository.deleteAll();
    droneRepository.deleteAll();
  }

  @Test
  void givenAValidInput_whenCreateIsCalled_thenSuccess() {
    final DroneDto drone =
        DroneDto.builder().serialNumber("ABC123").model("LightWeight").batteryCapacity(100).build();
    droneAppService.create(drone);
  }

  @Test
  void givenAValidInput_whenLoadDroneIsCalled_thenSuccess() throws MalformedURLException {

    final DroneDto drone =
        DroneDto.builder().serialNumber("ABC123").model("LightWeight").batteryCapacity(100).build();
    droneAppService.create(drone);

    final MedicationEntity medication1 =
        MedicationEntity.builder()
            .name("Aspirin")
            .weight(15)
            .code("ABC")
            .imageUrl(new URL("https://test.com"))
            .build();
    final MedicationEntity medication2 =
        MedicationEntity.builder()
            .name("Ibuprofen")
            .weight(30)
            .code("EFG")
            .imageUrl(new URL("https://test.com"))
            .build();
    final MedicationEntity medication3 =
        MedicationEntity.builder()
            .name("Paracetamol")
            .weight(45)
            .code("HIJ")
            .imageUrl(new URL("https://test.com"))
            .build();

    medicationRepository.saveAll(List.of(medication1, medication2, medication3));
    droneAppService.loadDrone("ABC123", List.of("ABC", "EFG", "HIJ"));
  }

  @Test
  void givenAnInvalidLoadWeight_whenLoadDroneIsCalled_thenThrowException()
      throws MalformedURLException {

    final DroneDto drone =
        DroneDto.builder().serialNumber("ABC123").model("LightWeight").batteryCapacity(100).build();
    droneAppService.create(drone);

    final MedicationEntity medication1 =
        MedicationEntity.builder()
            .name("Aspirin")
            .weight(100)
            .code("ABC")
            .imageUrl(new URL("https://test.com"))
            .build();
    final MedicationEntity medication2 =
        MedicationEntity.builder()
            .name("Ibuprofen")
            .weight(100)
            .code("EFG")
            .imageUrl(new URL("https://test.com"))
            .build();
    final MedicationEntity medication3 =
        MedicationEntity.builder()
            .name("Paracetamol")
            .weight(100)
            .code("HIJ")
            .imageUrl(new URL("https://test.com"))
            .build();

    medicationRepository.saveAll(List.of(medication1, medication2, medication3));
    assertThrows(
        DroneException.class,
        () -> droneAppService.loadDrone("ABC123", List.of("ABC", "EFG", "HIJ")));
  }

  @Test
  void givenAnInvalidMedications_whenLoadDroneIsCalled_thenThrowException() {

    final DroneDto drone =
        DroneDto.builder().serialNumber("ABC123").model("LightWeight").batteryCapacity(100).build();
    droneAppService.create(drone);

    assertThrows(
        DroneException.class,
        () -> droneAppService.loadDrone("ABC123", List.of("ABC", "EFG", "HIJ")));
  }

  @Test
  void givenAnInvalidDroneBattery_whenLoadDroneIsCalled_thenThrowException() {

    final DroneDto drone =
        DroneDto.builder().serialNumber("ABC123").model("LightWeight").batteryCapacity(10).build();
    droneAppService.create(drone);

    assertThrows(
        DroneException.class,
        () -> droneAppService.loadDrone("ABC123", List.of("ABC", "EFG", "HIJ")));
  }

  @Test
  void givenAValidSerial_whenGetDroneIsCalled_thenReturnCorrectDrone() {
    final DroneEntity drone =
        DroneEntity.builder()
            .serialNumber("ABC123")
            .model(DroneModel.LIGHTWEIGHT)
            .batteryCapacity(100)
            .state(DroneState.IDLE)
            .build();
    droneRepository.save(drone);
    droneAppService.getDrone("ABC123");
  }

  @Test
  void givenAnInvalidSerial_whenGetDroneIsCalled_thenThrowException() {
    assertThrows(DroneException.class, () -> droneAppService.getDrone("ABC123"));
  }

  @Test
  void getAvailableDrones() {
    final DroneEntity drone =
        DroneEntity.builder()
            .serialNumber("ABC123")
            .model(DroneModel.LIGHTWEIGHT)
            .batteryCapacity(100)
            .state(DroneState.IDLE)
            .build();
    final DroneEntity drone2 =
        DroneEntity.builder()
            .serialNumber("ABC123")
            .model(DroneModel.LIGHTWEIGHT)
            .batteryCapacity(100)
            .state(DroneState.IDLE)
            .build();
    final DroneEntity drone3 =
        DroneEntity.builder()
            .serialNumber("ABC123")
            .model(DroneModel.LIGHTWEIGHT)
            .batteryCapacity(100)
            .state(DroneState.LOADED)
            .build();
    droneRepository.saveAll(List.of(drone, drone2, drone3));
    final List<DroneDto> availableDrones = droneAppService.getAvailableDrones();
    assertEquals(2, availableDrones.size());
    final List<String> availableDronesCodes =
        availableDrones.stream().map(DroneDto::getSerialNumber).toList();
    assertTrue(
        availableDronesCodes.contains(drone.getSerialNumber())
            && availableDronesCodes.contains(drone2.getSerialNumber()));
  }
}
