package com.essexboy.simpleservice;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.UUID;

@RestController
public class Controller {

    private ServiceStatus serviceStatus = new ServiceStatus();

    @GetMapping("/")
    public ResponseEntity<ServiceStatus> service() {
        serviceStatus.setMessage("hello greg");
        if (System.getProperty("MESSAGE") != null) {
            serviceStatus.setMessage(System.getProperty("MESSAGE"));
        }
        return new ResponseEntity<>(serviceStatus, HttpStatus.OK);
    }

    @GetMapping("/startup")
    public ResponseEntity<HealthCheckStatus> startup() {
        return getHealthCheckStatusResponseEntity(serviceStatus.getStartUpHealthCheckStatus());
    }

    @GetMapping("/liveness")
    public ResponseEntity<HealthCheckStatus> liveness() {
        return getHealthCheckStatusResponseEntity(serviceStatus.getLivenessHealthCheckStatus());
    }

    @GetMapping("/readiness")
    public ResponseEntity<HealthCheckStatus> readiness() {
        return getHealthCheckStatusResponseEntity(serviceStatus.getReadinessHealthCheckStatus());
    }

    private ResponseEntity<HealthCheckStatus> getHealthCheckStatusResponseEntity(HealthCheckStatus healthCheckStatus) {
        healthCheckStatus.setHealthy(true);
        HttpStatus httpStatus = HttpStatus.OK;
        healthCheckStatus.setCount(healthCheckStatus.getCount() + 1);
        if (healthCheckStatus.getCount() % healthCheckStatus.getFailEvery() == 0) {
            healthCheckStatus.setHealthy(false);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(healthCheckStatus, httpStatus);
    }

    @GetMapping("/healthy")
    public ResponseEntity<String> healthy() {
        return new ResponseEntity<>("healthy", HttpStatus.OK);
    }

    @GetMapping("/unhealthy")
    public ResponseEntity<String> unHealthy() {
        return new ResponseEntity<>("unhealthy", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/reset/startup/{failEvery}")
    public ResponseEntity<String> resetStartUp(@PathVariable("failEvery") Integer failEvery) {
        serviceStatus.getStartUpHealthCheckStatus().setHealthy(true);
        serviceStatus.getStartUpHealthCheckStatus().setCount(0);
        serviceStatus.getStartUpHealthCheckStatus().setFailEvery(failEvery);
        return new ResponseEntity<>("resetStartUp", HttpStatus.OK);
    }

    @PutMapping("/reset/liveness/{failEvery}")
    public ResponseEntity<String> resetLiveness(@PathVariable("failEvery") Integer failEvery) {
        serviceStatus.getLivenessHealthCheckStatus().setHealthy(true);
        serviceStatus.getLivenessHealthCheckStatus().setCount(0);
        serviceStatus.getLivenessHealthCheckStatus().setFailEvery(failEvery);
        return new ResponseEntity<>("resetLiveness", HttpStatus.OK);
    }

    @PutMapping("/reset/readiness/{failEvery}")
    public ResponseEntity<String> resetRediness(@PathVariable("failEvery") Integer failEvery) {
        serviceStatus.getReadinessHealthCheckStatus().setHealthy(true);
        serviceStatus.getReadinessHealthCheckStatus().setCount(0);
        serviceStatus.getReadinessHealthCheckStatus().setFailEvery(failEvery);
        return new ResponseEntity<>("resetReadiness", HttpStatus.OK);
    }
}
