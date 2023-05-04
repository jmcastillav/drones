package musala.soft.drones.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import musala.soft.drones.domain.entity.DroneEntity;
import musala.soft.drones.domain.exception.DroneException;
import musala.soft.drones.domain.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DroneServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DroneServiceImplTest {

  @MockBean private DroneRepository droneRepository;

  @Autowired private DroneServiceImpl droneServiceImpl;

  @Test
  void givenAnEmptyDrone_whenSaveDroneIsCalled_thenThrowException() {
    assertThrows(DroneException.class, () -> droneServiceImpl.saveDrone(new DroneEntity()));
  }

  @Test
  void givenAnInvalidSerial_whenSaveDroneIsCalled_thenThrowException() {
    assertThrows(
        DroneException.class,
        () ->
            droneServiceImpl.saveDrone(
                DroneEntity.builder()
                    .serialNumber(
                        "3q4w5e6r7t8y9u0i1o2p3q4w5e6r7t8y9u0i1o2p3q4w5e6r7t8y9u0i1o2p3q4w5e6r7t8y9u0i1o2p3q4w1hjK8fhJ93h2kx7z5")
                    .build()));
  }

  @Test
  void givenAnInvalidWeight_whenSaveDroneIsCalled_thenThrowException() {
    assertThrows(
        DroneException.class,
        () ->
            droneServiceImpl.saveDrone(
                DroneEntity.builder().serialNumber("abcd1234").weightLimit(501).build()));
  }

  @Test
  void givenDuplicateSerialNumber_whenSaveDrone_thenThrowDroneException() {

    when(droneRepository.save(Mockito.any())).thenReturn(new DroneEntity());
    when(droneRepository.findBySerialNumber(Mockito.any()))
        .thenReturn(Optional.of(new DroneEntity()));

    assertThrows(
        DroneException.class,
        () ->
            droneServiceImpl.saveDrone(
                new DroneEntity(
                    1L,
                    "42",
                    DroneEntity.DroneModel.LIGHTWEIGHT,
                    3,
                    1,
                    DroneEntity.DroneState.IDLE)));
    verify(droneRepository).findBySerialNumber(Mockito.any());
  }

  @Test
  void givenNewDroneWithValidSerialNumber_whenSaveDrone_thenDroneSavedSuccessfully() {
    final DroneEntity droneEntity = new DroneEntity();
    when(droneRepository.save(Mockito.any())).thenReturn(droneEntity);
    when(droneRepository.findBySerialNumber(Mockito.any())).thenReturn(Optional.empty());
    final DroneEntity drone =
        new DroneEntity(
            1L, "42", DroneEntity.DroneModel.LIGHTWEIGHT, 3, 1, DroneEntity.DroneState.IDLE);

    assertSame(droneEntity, droneServiceImpl.saveDrone(drone));
    verify(droneRepository).save(Mockito.any());
    verify(droneRepository).findBySerialNumber(Mockito.any());
    assertEquals(DroneEntity.DroneState.IDLE, drone.getState());
  }

  @Test
  void givenExistingDroneWithEmptySerialNumber_whenSaveDrone_thenThrowDroneException() {

    when(droneRepository.save(Mockito.any())).thenReturn(new DroneEntity());
    when(droneRepository.findBySerialNumber(Mockito.any()))
        .thenReturn(Optional.of(new DroneEntity()));

    assertThrows(
        DroneException.class,
        () ->
            droneServiceImpl.saveDrone(
                new DroneEntity(
                    1L,
                    "",
                    DroneEntity.DroneModel.LIGHTWEIGHT,
                    3,
                    1,
                    DroneEntity.DroneState.IDLE)));
  }

  @Test
  void givenSerialNumber_whenFindBySerialNumber_thenReturnsDroneEntity() {

    final Optional<DroneEntity> ofResult = Optional.of(new DroneEntity());
    when(droneRepository.findBySerialNumber(Mockito.any())).thenReturn(ofResult);

    final Optional<DroneEntity> actualFindBySerialNumberResult =
        droneServiceImpl.findBySerialNumber("42");

    assertSame(ofResult, actualFindBySerialNumberResult);
    assertTrue(actualFindBySerialNumberResult.isPresent());
    verify(droneRepository).findBySerialNumber(Mockito.any());
  }

  @Test
  void givenDroneEntity_whenUpdateDrone_thenReturnsUpdatedDroneEntity() {
    final DroneEntity droneEntity = new DroneEntity();
    when(droneRepository.save(Mockito.any())).thenReturn(droneEntity);
    when(droneRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(new DroneEntity()));

    assertSame(droneEntity, droneServiceImpl.updateDrone(new DroneEntity()));
    verify(droneRepository).save(Mockito.any());
    verify(droneRepository).findById(Mockito.<Long>any());
  }

  @Test
  void givenNonExistentDroneEntity_whenUpdateDrone_thenThrowsDroneException() {

    when(droneRepository.save(Mockito.any())).thenReturn(new DroneEntity());
    when(droneRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

    assertThrows(DroneException.class, () -> droneServiceImpl.updateDrone(new DroneEntity()));
    verify(droneRepository).findById(Mockito.<Long>any());
  }

  @Test
  void givenDroneState_whenGetAvailableDrones_thenReturnsListWithAvailableDrones() {

    final ArrayList<DroneEntity> droneEntityList = new ArrayList<>();
    when(droneRepository.findAllByStateIs(Mockito.any())).thenReturn(droneEntityList);

    final List<DroneEntity> actualAvailableDrones = droneServiceImpl.getAvailableDrones();

    assertSame(droneEntityList, actualAvailableDrones);
    assertTrue(actualAvailableDrones.isEmpty());
    verify(droneRepository).findAllByStateIs(Mockito.any());
  }

  @Test
  void whenGetAllDrones_thenReturnsListWithAllDrones() {
    final ArrayList<DroneEntity> droneEntityList = new ArrayList<>();
    when(droneRepository.findAll()).thenReturn(droneEntityList);

    final List<DroneEntity> actualAllDrones = droneServiceImpl.getAllDrones();

    assertSame(droneEntityList, actualAllDrones);
    assertTrue(actualAllDrones.isEmpty());
    verify(droneRepository).findAll();
  }
}
