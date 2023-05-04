package musala.soft.drones.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import musala.soft.drones.domain.entity.DroneLoadEntity;
import musala.soft.drones.domain.repository.DroneLoadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DroneLoadServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DroneLoadServiceImplTest {

  @MockBean private DroneLoadRepository droneLoadRepository;

  @Autowired private DroneLoadServiceImpl droneLoadServiceImpl;

  @Test
  void givenAValidDroneId_whenGetDroneLoadByIdIsCalled_thenReturnCorrectDrone() {
    final ArrayList<DroneLoadEntity> droneLoadEntityList = new ArrayList<>();
    when(droneLoadRepository.findAllByDroneId(Mockito.<Long>any())).thenReturn(droneLoadEntityList);

    final List<DroneLoadEntity> actualDroneLoadById = droneLoadServiceImpl.getDroneLoadById(1L);

    assertSame(droneLoadEntityList, actualDroneLoadById);
    assertTrue(actualDroneLoadById.isEmpty());
    verify(droneLoadRepository).findAllByDroneId(Mockito.<Long>any());
  }

  @Test
  void givenAValidLoadInput_whenAddDroneLoadIsCalled_thenAddItemsToDroneLoad() {

    when(droneLoadRepository.save(Mockito.any())).thenReturn(new DroneLoadEntity());

    final ArrayList<Long> medicamentIds = new ArrayList<>();
    medicamentIds.add(1L);

    droneLoadServiceImpl.addDroneLoad(1L, medicamentIds);

    verify(droneLoadRepository).save(Mockito.any());
  }

  @Test
  void givenAValidLoadInput_whenAddDroneLoadIsCalled_thenAddItemsToDroneLoad2() {

    when(droneLoadRepository.save(Mockito.any())).thenReturn(new DroneLoadEntity());

    final ArrayList<Long> medicamentIds = new ArrayList<>();
    medicamentIds.add(0L);
    medicamentIds.add(1L);

    droneLoadServiceImpl.addDroneLoad(1L, medicamentIds);

    verify(droneLoadRepository, atLeast(1)).save(Mockito.any());
  }
}
