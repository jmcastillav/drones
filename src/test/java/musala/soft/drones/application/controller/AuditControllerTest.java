package musala.soft.drones.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import musala.soft.drones.application.dto.DroneBatteryLogDto;
import musala.soft.drones.application.service.AuditAppService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

  @Mock private AuditAppService auditAppService;

  @InjectMocks private AuditController auditController;

  @Test
  void givenAValidDateRange_whenGetDroneBatteryLogIsCalled_thenReturnCorrectLogs() {
    final LocalDateTime startDate = LocalDateTime.now().minusHours(1);
    final LocalDateTime endDate = LocalDateTime.now();
    final List<DroneBatteryLogDto> batteryLogs =
        Arrays.asList(
            new DroneBatteryLogDto("ABC123", 90, LocalDateTime.now()),
            new DroneBatteryLogDto("DEF456", 80, LocalDateTime.now()));
    when(auditAppService.getDroneBatteryByDateRange(startDate, endDate)).thenReturn(batteryLogs);

    final ResponseEntity<List<DroneBatteryLogDto>> response =
        auditController.getDroneBatteryLog(startDate, endDate);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(batteryLogs, response.getBody());
  }
}
