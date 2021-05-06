package com.essexboy.simpleservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void service() throws Exception {
        final String message = "very clever message";
        System.setProperty("MESSAGE", message);
        mvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(message)));
    }

    @Test
    void health() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/health")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("healthy")));
    }

    @Test
    void healthy() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/healthy")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("healthy")));
    }

    @Test
    void setHealthy() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/healthy")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("healthy")));

        mvc.perform(MockMvcRequestBuilders.get("/health")
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
    void setUnHealthy() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/unhealthy")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("unhealthy")));

        mvc.perform(MockMvcRequestBuilders.get("/health")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("unhealthy")));
    }
}