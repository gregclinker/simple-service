package com.essexboy.simpleservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private boolean healthy = true;

    @GetMapping("/")
    public ResponseEntity<String> service() {
        String message = System.getProperty("MESSAGE");
        if (message == null) {
            message = "hello greg";
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        if (healthy) {
            return new ResponseEntity<>("healthy", HttpStatus.OK);
        }
        return new ResponseEntity<>("unhealthy", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/healthy")
    public ResponseEntity<String> healthy() {
        return new ResponseEntity<>("healthy", HttpStatus.OK);
    }

    @PutMapping("/healthy")
    public ResponseEntity<String> setHealthy() {
        healthy=true;
        return new ResponseEntity<>("healthy", HttpStatus.OK);
    }

    @GetMapping("/unhealthy")
    public ResponseEntity<String> unHealthy() {
        return new ResponseEntity<>("unhealthy", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/unhealthy")
    public ResponseEntity<String> setUnHealthy() {
        healthy=false;
        return new ResponseEntity<>("unhealthy", HttpStatus.OK);
    }
}
