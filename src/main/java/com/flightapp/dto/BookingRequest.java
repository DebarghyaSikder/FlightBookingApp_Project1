package com.flightapp.dto;

import java.util.List;

import com.flightapp.entity.enums.MealType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {

    @NotBlank(message = "Customer name is required")
    private String name;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Meal type selection is required")
    private MealType mealType;

    @NotNull(message = "Number of seats is required")
    @Min(value = 1, message = "At least 1 seat must be booked")
    private Integer numberOfSeats;

    @NotEmpty(message = "Passenger list cannot be empty")
    @Valid
    private List<PassengerDto> passengers;
}
