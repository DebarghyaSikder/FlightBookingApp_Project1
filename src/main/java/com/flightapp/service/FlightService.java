package com.flightapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.dto.FlightSearchResponse;
import com.flightapp.entity.FlightInventory;
import com.flightapp.repository.FlightInventoryRepository;

@Service
public class FlightService {          

    private final FlightInventoryRepository flightRepo;

    public FlightService(FlightInventoryRepository flightRepo) {
        this.flightRepo = flightRepo;
    }

    public List<FlightSearchResponse> searchFlights(FlightSearchRequest request) {
        LocalDate date = request.getJourneyDate();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        List<FlightInventory> flights = flightRepo
                .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween(
                        request.getFromPlace(),
                        request.getToPlace(),
                        start,
                        end
                );

        return flights.stream().map(this::toResponse).toList();
    }

    private FlightSearchResponse toResponse(FlightInventory f) {
        FlightSearchResponse res = new FlightSearchResponse();
        res.setFlightId(f.getId());
        res.setAirlineName(f.getAirline().getName());
        res.setAirlineLogoUrl(f.getAirline().getLogoUrl());
        res.setFlightNumber(f.getFlightNumber());
        res.setDepartureTime(f.getDepartureTime());
        res.setArrivalTime(f.getArrivalTime());
        res.setPriceOneWay(f.getPriceOneWay());
        res.setPriceRoundTrip(f.getPriceRoundTrip());
        return res;
    }
}
