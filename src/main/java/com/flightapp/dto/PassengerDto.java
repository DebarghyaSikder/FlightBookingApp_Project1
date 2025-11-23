package com.flightapp.dto;

import com.flightapp.entity.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               
@NoArgsConstructor  // no-args constructor
@AllArgsConstructor // all-args constructor
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
}
