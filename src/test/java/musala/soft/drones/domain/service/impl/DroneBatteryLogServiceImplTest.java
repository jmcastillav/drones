package musala.soft.drones.domain.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import musala.soft.drones.domain.entity.DroneBatteryLogEntity;
import musala.soft.drones.domain.exception.LogException;
import musala.soft.drones.domain.repository.DroneBatteryLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DroneBatteryLogServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DroneBatteryLogServiceImplTest {

  @MockBean private DroneBatteryLogRepository droneBatteryLogRepository;

  @Autowired private DroneBatteryLogServiceImpl droneBatteryLogServiceImpl;

  @Test
  void givenAValidInputDate_whenSaveAllIsCalled_thenCorrectSaveNewLogs() {

    when(droneBatteryLogRepository.saveAll(Mockito.any())).thenReturn(new ArrayList<>());
    droneBatteryLogServiceImpl.saveLog(new ArrayList<>());

    verify(droneBatteryLogRepository).saveAll(Mockito.any());
  }

  @Test
  void givenAValidDate_whenGetLogIsCalled_thenReturnCorrectRangeOfLogs() {
    final ArrayList<DroneBatteryLogEntity> droneBatteryLogEntityList = new ArrayList<>();
    when(droneBatteryLogRepository.findAllByCreatedBetween(Mockito.any(), Mockito.any()))
        .thenReturn(droneBatteryLogEntityList);
    final LocalDateTime startDate = LocalDate.of(1970, 1, 1).atStartOfDay();

    final List<DroneBatteryLogEntity> actualLog =
        droneBatteryLogServiceImpl.getLog(startDate, LocalDate.of(1970, 1, 1).atStartOfDay());

    assertSame(droneBatteryLogEntityList, actualLog);
    assertTrue(actualLog.isEmpty());
    verify(droneBatteryLogRepository).findAllByCreatedBetween(Mockito.any(), Mockito.any());
  }

  @Test
  void givenAnInvalidDate_whenGetLogIsCalled_thenReturnCorrectRangeOfLogs() {
    final ArrayList<DroneBatteryLogEntity> droneBatteryLogEntityList = new ArrayList<>();
    when(droneBatteryLogRepository.findAllByCreatedBetween(Mockito.any(), Mockito.any()))
        .thenReturn(droneBatteryLogEntityList);
    final LocalDateTime startDate = LocalDate.of(1970, 1, 2).atStartOfDay();

    assertThrows(
        LogException.class,
        () ->
            droneBatteryLogServiceImpl.getLog(startDate, LocalDate.of(1970, 1, 1).atStartOfDay()));
  }
}
