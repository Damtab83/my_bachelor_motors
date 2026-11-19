package com.damtab.my_bachelor_motors.alerting.monitor;

import com.damtab.my_bachelor_motors.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AvailabilityMonitor {

    private final HealthEndpoint healthEndpoint;
    private final AlertService alertService;

    private volatile boolean applicationAvailable = true;

    @Scheduled(fixedDelay = 30000)
    public void checkAvailability() {

        boolean available =
                healthEndpoint.health().getStatus().getCode()
                        .equals("UP");

        if (!available && applicationAvailable) {

            alertService.sendAlert(
                    "AVAILABILITY",
                    "Application unavailable",
                    "L'application Spring Boot est en état DOWN."
            );
        }

        if (available && !applicationAvailable) {

            alertService.sendAlert(
                    "AVAILABILITY",
                    "Application restored",
                    "L'application Spring Boot est de nouveau disponible."
            );
        }

        applicationAvailable = available;
    }
}
