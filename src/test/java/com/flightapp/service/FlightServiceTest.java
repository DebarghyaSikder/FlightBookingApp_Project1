package com.flightapp.service;

import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.dto.FlightSearchResponse;
import com.flightapp.entity.Airline;
import com.flightapp.entity.FlightInventory;
import com.flightapp.repository.FlightInventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightInventoryRepository flightRepo;

    @InjectMocks
    private FlightService flightService;

    @Test
    void searchFlights_shouldReturnMatchingFlights() {
        // Arrange
        FlightSearchRequest request = new FlightSearchRequest();
        request.setFromPlace("DEL");
        request.setToPlace("BLR");
        request.setJourneyDate(LocalDate.of(2025, 11, 22));
        // if your DTO now has passengerCount / tripType, you can set them too,
        // but FlightService currently doesn't use them

        LocalDateTime start = request.getJourneyDate().atStartOfDay();
        LocalDateTime end = request.getJourneyDate().atTime(23, 59, 59);

        Airline airline = new Airline();
        airline.setName("Indigo");
        airline.setLogoUrl("https://logo.com/indigo.png");

        FlightInventory inv = new FlightInventory();
        inv.setId(1L);
        inv.setAirline(airline);
        inv.setFlightNumber("6E-202");
        inv.setFromPlace("DEL");
        inv.setToPlace("BLR");
        inv.setDepartureTime(LocalDateTime.of(2025, 11, 22, 10, 30));
        inv.setArrivalTime(LocalDateTime.of(2025, 11, 22, 13, 10));
        inv.setPriceOneWay(BigDecimal.valueOf(4500));
        inv.setPriceRoundTrip(BigDecimal.valueOf(8000));

        when(flightRepo.findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween(
                "DEL", "BLR", start, end
        )).thenReturn(List.of(inv));

        // Act
        List<FlightSearchResponse> result = flightService.searchFlights(request);

        // Assert
        assertThat(result).hasSize(1);
        FlightSearchResponse res = result.get(0);

        assertThat(res.getFlightId()).isEqualTo(1L);
        assertThat(res.getAirlineName()).isEqualTo("Indigo");
        assertThat(res.getAirlineLogoUrl()).isEqualTo("https://logo.com/indigo.png");
        assertThat(res.getFlightNumber()).isEqualTo("6E-202");
        assertThat(res.getPriceOneWay()).isEqualByComparingTo(BigDecimal.valueOf(4500));
        assertThat(res.getPriceRoundTrip()).isEqualByComparingTo(BigDecimal.valueOf(8000));

        verify(flightRepo, times(1))
                .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween("DEL", "BLR", start, end);
    }

    @Test
    void searchFlights_shouldReturnEmptyList_whenNoFlightsAvailable() {
        // Arrange
        FlightSearchRequest request = new FlightSearchRequest();
        request.setFromPlace("DEL");
        request.setToPlace("BLR");
        request.setJourneyDate(LocalDate.of(2025, 11, 22));

        LocalDateTime start = request.getJourneyDate().atStartOfDay();
        LocalDateTime end = request.getJourneyDate().atTime(23, 59, 59);

        when(flightRepo.findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween(
                "DEL", "BLR", start, end
        )).thenReturn(Collections.emptyList());

        // Act
        List<FlightSearchResponse> result = flightService.searchFlights(request);

        // Assert
        assertThat(result).isEmpty();

        verify(flightRepo, times(1))
                .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween("DEL", "BLR", start, end);
    }
}
