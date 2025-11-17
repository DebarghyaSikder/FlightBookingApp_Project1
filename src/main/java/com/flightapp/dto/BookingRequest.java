package com.flightapp.dto;

import java.util.List;

import com.flightapp.entity.enums.MealType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookingRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private MealType mealType;

    @Min(1)
    private int numberOfSeats;

    @NotNull
    private List<PassengerDto> passengers;

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
    public List<PassengerDto> getPassengers() {
        return passengers;
    }
    public void setPassengers(List<PassengerDto> passengers) {
        this.passengers = passengers;
    }
}
