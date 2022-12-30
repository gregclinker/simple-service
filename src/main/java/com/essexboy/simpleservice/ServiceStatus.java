package com.essexboy.simpleservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ServiceStatus {
    private String message = UUID.randomUUID().toString();
    private HealthCheckStatus startUpHealthCheckStatus = new HealthCheckStatus("startup");
    private HealthCheckStatus livenessHealthCheckStatus = new HealthCheckStatus("liveness");
    private HealthCheckStatus readinessHealthCheckStatus = new HealthCheckStatus("readiness");
}
