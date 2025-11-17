package com.flightapp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.flightapp.entity.enums.BookingStatus;
import com.flightapp.entity.enums.MealType;

public class TicketResponse {

    private String pnr;
    private String airlineName;
    private String flightNumber;
    private String fromPlace;
    private String toPlace;
    private LocalDate journeyDate;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private String name;
    private String email;
    private MealType mealType;
    private int numberOfSeats;

    private BookingStatus bookingStatus;

    private List<PassengerDto> passengers;

    public String getPnr() {
        return pnr;
    }
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    public String getAirlineName() {
        return airlineName;
    }
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
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
    public LocalDate getJourneyDate() {
        return journeyDate;
    }
    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public MealType getMealType() {
        return mealType;
    }
    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public List<PassengerDto> getPassengers() {
        return passengers;
    }
    public void setPassengers(List<PassengerDto> passengers) {
        this.passengers = passengers;
    }
}
