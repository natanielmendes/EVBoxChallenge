package com.EVBox.challenge.charging;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.EVBox.challenge.charging.ChargingSession;
import com.EVBox.challenge.charging.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChargingSessionIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void addChargingSession() throws Exception {
        ChargingSession chargingSession = new ChargingSession();
        chargingSession.setStartedAt(new Date());
        mvc.perform( MockMvcRequestBuilders
                .post("/chargingSessions")
                .content(asJsonString(chargingSession))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suspendedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.STARTED.getStatusName()));
    }

    @Test
    public void addChargingSessionIncorrectStartedAtFormat() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/chargingSessions")
                .content("{\"startedAt\":\"20190716T19:51:40.560\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void addChargingSessionStartedAtNotPresent() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/chargingSessions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void getChargingSessionById() throws Exception {
        ChargingSession chargingSession = new ChargingSession();
        chargingSession.setStartedAt(new Date());
        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .post("/chargingSessions")
                .content(asJsonString(chargingSession))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suspendedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.STARTED.getStatusName())).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        ChargingSession chargingSessionResponse = mapper.readValue(result.getResponse().getContentAsString(), ChargingSession.class);

        mvc.perform(MockMvcRequestBuilders.get("/chargingSessions/{id}", chargingSessionResponse.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(String.valueOf(chargingSessionResponse.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suspendedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.STARTED.getStatusName())).andReturn();
    }

    @Test
    public void suspendChargingSessionById() throws Exception {
        ChargingSession chargingSession = new ChargingSession();
        chargingSession.setStartedAt(new Date());
        MvcResult result = mvc.perform( MockMvcRequestBuilders
                .post("/chargingSessions")
                .content(asJsonString(chargingSession))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suspendedAt").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.STARTED.getStatusName())).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        ChargingSession chargingSessionResponse = mapper.readValue(result.getResponse().getContentAsString(), ChargingSession.class);

        ChargingSession chargingSessionUpdate = new ChargingSession();
        chargingSessionUpdate.setSuspendedAt(new Date());
        mvc.perform( MockMvcRequestBuilders
                .put("/chargingSessions/{id}", chargingSessionResponse.getId())
                .content(asJsonString(chargingSessionUpdate))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.suspendedAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(Status.SUSPENDED.getStatusName()));
    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
