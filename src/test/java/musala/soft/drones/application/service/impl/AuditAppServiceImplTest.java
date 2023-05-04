package musala.soft.drones.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import musala.soft.drones.application.dto.DroneBatteryLogDto;
import musala.soft.drones.application.service.AuditAppService;
import musala.soft.drones.domain.entity.DroneBatteryLogEntity;
import musala.soft.drones.domain.repository.DroneBatteryLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AuditAppServiceImplTest {

  @Autowired private AuditAppService auditAppService;
  @Autowired private DroneBatteryLogRepository droneBatteryLogRepository;

  @BeforeEach
  void setUp() {}

  @Test
  void getDroneBatteryByDateRange() {
    final DroneBatteryLogEntity droneBatteryLogEntity1 =
        DroneBatteryLogEntity.builder()
            .droneId(1L)
            .batteryPercentage(100)
            .created(LocalDateTime.of(1970, 1, 1, 12, 0, 0))
            .build();
    final DroneBatteryLogEntity droneBatteryLogEntity2 =
        DroneBatteryLogEntity.builder()
            .droneId(1L)
            .batteryPercentage(100)
            .created(LocalDateTime.of(1970, 1, 1, 12, 1, 0))
            .build();
    final DroneBatteryLogEntity droneBatteryLogEntity3 =
        DroneBatteryLogEntity.builder()
            .droneId(1L)
            .batteryPercentage(100)
            .created(LocalDateTime.of(1970, 1, 1, 12, 5, 0))
            .build();
    droneBatteryLogRepository.saveAll(
        List.of(droneBatteryLogEntity1, droneBatteryLogEntity2, droneBatteryLogEntity3));
    final List<DroneBatteryLogDto> response =
        auditAppService.getDroneBatteryByDateRange(
            LocalDateTime.of(1970, 1, 1, 12, 0, 0), LocalDateTime.of(1970, 1, 1, 12, 4, 0));
    assertEquals(2, response.size());
  }
}
