package com.flightapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlightSearchResponse {

    private Long flightId;
    private String airlineName;
    private String airlineLogoUrl;
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal priceOneWay;
    private BigDecimal priceRoundTrip;

    public Long getFlightId() {
        return flightId;
    }
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    public String getAirlineName() {
        return airlineName;
    }
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    public String getAirlineLogoUrl() {
        return airlineLogoUrl;
    }
    public void setAirlineLogoUrl(String airlineLogoUrl) {
        this.airlineLogoUrl = airlineLogoUrl;
    }
    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public BigDecimal getPriceOneWay() {
        return priceOneWay;
    }
    public void setPriceOneWay(BigDecimal priceOneWay) {
        this.priceOneWay = priceOneWay;
    }
    public BigDecimal getPriceRoundTrip() {
        return priceRoundTrip;
    }
    public void setPriceRoundTrip(BigDecimal priceRoundTrip) {
        this.priceRoundTrip = priceRoundTrip;
    }
}
