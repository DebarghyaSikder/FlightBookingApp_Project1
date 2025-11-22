package com.flightapp.dto;

import java.time.LocalDate;

import com.flightapp.entity.enums.TripType;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FlightSearchRequest {

    @NotBlank(message = "Source location (fromPlace) is required")
    private String fromPlace;

    @NotBlank(message = "Destination location (toPlace) is required")
    private String toPlace;

    @NotNull(message = "Journey date is required")
    @FutureOrPresent(message = "Journey date cannot be in the past")
    private LocalDate journeyDate;

    @NotNull(message = "Trip type is required")
    private TripType tripType = TripType.ONE_WAY;

    @NotNull(message = "Passenger count is required")
    @Min(value = 1, message = "At least 1 passenger must be selected")
    private Integer passengerCount;

    // Getters and Setters
    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(Integer passengerCount) {
        this.passengerCount = passengerCount;
    }
}
