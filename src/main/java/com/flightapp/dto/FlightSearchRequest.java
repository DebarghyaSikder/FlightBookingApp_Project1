package com.flightapp.dto;

import java.time.LocalDate;

import com.flightapp.entity.enums.TripType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FlightSearchRequest {

    @NotBlank
    private String fromPlace;

    @NotBlank
    private String toPlace;

    @NotNull
    private LocalDate journeyDate;

    private TripType tripType = TripType.ONE_WAY;

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
}
