package musala.soft.drones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DronesApplication {

  public static void main(final String[] args) {
    SpringApplication.run(DronesApplication.class, args);
  }
}
