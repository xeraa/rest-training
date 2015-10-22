package net.xeraa;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealth implements HealthIndicator {

  @Override
  public Health health() {
    if (true) {
      return Health.down().withDetail("Error Code", 500).build();
    }
    return Health.up().build();
  }

}
