package com.flightapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.flightapp.entity.enums.BookingStatus;
import com.flightapp.entity.enums.MealType;

import jakarta.persistence.*;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String pnr;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    private FlightInventory flight;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer numSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus bookingStatus;

    @Column(nullable = false)
    private LocalDate journeyDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPnr() {
        return pnr;
    }
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    public FlightInventory getFlight() {
        return flight;
    }
    public void setFlight(FlightInventory flight) {
        this.flight = flight;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getNumSeats() {
        return numSeats;
    }
    public void setNumSeats(Integer numSeats) {
        this.numSeats = numSeats;
    }
    public MealType getMealType() {
        return mealType;
    }
    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public LocalDate getJourneyDate() {
        return journeyDate;
    }
    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
