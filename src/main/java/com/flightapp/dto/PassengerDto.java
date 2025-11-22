package com.flightapp.dto;

import com.flightapp.entity.enums.Gender;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PassengerDto {

    @NotBlank(message = "Passenger name is required")
    private String name;

    @NotNull(message = "Passenger gender is required")
    private Gender gender;

    @NotNull(message = "Passenger age is required")
    @Min(value = 0, message = "Age cannot be negative")
    private Integer age;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    public PassengerDto() {
    }

    public PassengerDto(String name, Gender gender, Integer age, String seatNumber) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
