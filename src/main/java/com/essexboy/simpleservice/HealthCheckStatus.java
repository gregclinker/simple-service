package com.essexboy.simpleservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class HealthCheckStatus {
    private String name;
    private Integer failEvery = 1000;
    private Integer count = 0;
    private Boolean healthy = true;

    public HealthCheckStatus(String name) {
        this.name = name;
    }
}
