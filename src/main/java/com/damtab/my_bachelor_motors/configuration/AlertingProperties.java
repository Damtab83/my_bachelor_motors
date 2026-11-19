package com.damtab.my_bachelor_motors.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "alerting")
public record AlertingProperties(
        boolean enabled,
        String from,
        List<String> recipients,
        String applicationName,
        int cooldownMinutes
) {
}
