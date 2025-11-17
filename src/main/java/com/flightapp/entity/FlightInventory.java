package com.flightapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "flight_inventory")
public class FlightInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @Column(nullable = false)
    private String flightNumber;

    @Column(nullable = false)
    private String fromPlace;

    @Column(nullable = false)
    private String toPlace;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    @Column(nullable = false)
    private BigDecimal priceOneWay;

    private BigDecimal priceRoundTrip;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Airline getAirline() {
        return airline;
    }
    public void setAirline(Airline airline) {
        this.airline = airline;
    }
    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
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
    public Integer getTotalSeats() {
        return totalSeats;
    }
    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }
    public Integer getAvailableSeats() {
        return availableSeats;
    }
    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
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
