package com.flightapp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.entity.enums.TripType;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void searchFlights_returns200EvenIfNoFlights() throws Exception {
        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromPlace("KOL");
        req.setToPlace("HYD");
        req.setJourneyDate(LocalDate.now().plusDays(10));
        req.setTripType(TripType.ONE_WAY);

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(post("/api/v1.0/flight/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

    }
}
