package com.damtab.my_bachelor_motors.monitor;

import com.damtab.my_bachelor_motors.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseHealthMonitor {

    private final DataSource dataSource;
    private final AlertService alertService;

    private volatile boolean databaseAvailable = true;

    @Scheduled(fixedDelay = 30000)
    public void checkDatabase() {

        try (Connection connection = dataSource.getConnection()) {

            if (!connection.isValid(5)) {
                throw new IllegalStateException(
                        "Database connection is invalid"
                );
            }

            if (!databaseAvailable) {
                alertService.sendAlert(
                        "DATABASE",
                        "Database restored",
                        "La connexion à la base de données est rétablie."
                );

                databaseAvailable = true;
            }

        } catch (Exception e) {

            if (databaseAvailable) {
                alertService.sendAlert(
                        "DATABASE",
                        "Database unavailable",
                        "Impossible de se connecter à la base de données."
                );

                log.error("Database connection failed", e);
            }

            databaseAvailable = false;
        }
    }
}
