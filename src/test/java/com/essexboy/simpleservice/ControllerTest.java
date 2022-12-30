package com.essexboy.simpleservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void init() {
        System.setProperty("MESSAGE", "very clever message");
    }

    @Test
    void reset() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/reset/startup/1000")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("resetStartUp")));
        mvc.perform(MockMvcRequestBuilders.put("/reset/liveness/1000")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("resetLiveness")));
        mvc.perform(MockMvcRequestBuilders.put("/reset/readiness/1000")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("resetReadiness")));
    }

    @Test
    void service() throws Exception {
        reset();
        final ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setMessage("very clever message");
        serviceStatus.getStartUpHealthCheckStatus().setCount(0);
        serviceStatus.getLivenessHealthCheckStatus().setCount(0);
        serviceStatus.getReadinessHealthCheckStatus().setCount(0);
        final String json = new ObjectMapper().writeValueAsString(serviceStatus);
        mvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(json)));
    }

    @Test
    void startUp() throws Exception {
        final HealthCheckStatus healthCheckStatus = new HealthCheckStatus("startup");
        healthCheckStatus.setCount(healthCheckStatus.getCount() + 1);
        final String json = new ObjectMapper().writeValueAsString(healthCheckStatus);
        mvc.perform(MockMvcRequestBuilders.get("/startup")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(json)));
    }

    @Test
    void liveness() throws Exception {
        final HealthCheckStatus healthCheckStatus = new HealthCheckStatus("liveness");
        healthCheckStatus.setCount(healthCheckStatus.getCount() + 1);
        final String json = new ObjectMapper().writeValueAsString(healthCheckStatus);
        mvc.perform(MockMvcRequestBuilders.get("/liveness")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(json)));
    }

    @Test
    void readiness() throws Exception {
        final HealthCheckStatus healthCheckStatus = new HealthCheckStatus("readiness");
        healthCheckStatus.setCount(healthCheckStatus.getCount() + 1);
        final String json = new ObjectMapper().writeValueAsString(healthCheckStatus);
        mvc.perform(MockMvcRequestBuilders.get("/readiness")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(json)));
    }

    @Test
    void healthy() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/healthy")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("healthy")));
    }

    @Test
    void unHealthy() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/unhealthy")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("unhealthy")));
    }

    @Test
    void failEvery() throws Exception {
        reset();
        for (int i = 1; i <= 999; i++) {
            mvc.perform(MockMvcRequestBuilders.get("/startup")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
        mvc.perform(MockMvcRequestBuilders.get("/startup")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

}