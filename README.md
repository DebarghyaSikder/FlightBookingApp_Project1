# Flight Booking Application 

This is a Spring Boot–based backend application that simulates a simple flight booking system.  
It exposes REST APIs for:

- Adding flight inventory (airlines, routes, timings, prices)
- Searching for flights
- Booking tickets for one or more passengers
- Viewing ticket details and booking history
- Cancelling a booking with basic business rules

I’ve mainly used this project to practice Spring Boot, JPA, validation, testing (JUnit + Mockito), MySQL, and tools like Postman and SonarQube. This was Project 1 assigned to me as the first task.

---

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot 3
- **Database:** MySQL
- **Build Tool:** Maven
- **Testing:** JUnit 5, Mockito
- **Quality & Coverage:** SonarQube, JaCoCo
- **Tools:** Postman (for API testing), Eclipse IDE

---

## Features

### 1. Flight Inventory Management

- Add flight inventory for an airline:
  - Airline details
  - Flight number
  - Source & destination
  - Departure & arrival times
  - Total and available seats
  - One-way and round-trip prices

### 2. Flight Search

- Search API accepts:
  - `fromPlace`
  - `toPlace`
  - `journeyDate`
  - `tripType` (ONE_WAY / ROUND_TRIP)
  - `passengerCount`
- Returns a list of matching flights with:
  - Airline name and logo URL
  - Flight number
  - Departure & arrival time
  - Price for one-way / round trip

### 3. Booking & Passengers

- Book tickets for multiple passengers in a single request.
- Validations on:
  - Customer name & email
  - Meal type
  - Number of seats
  - Passenger details (name, gender, age, seat number)
- Business rules:
  - Number of passengers must match `numberOfSeats`
  - Seats must be available on the selected flight
- Generates a **PNR** using a separate utility (`PnrGenerator`).
- Decreases available seats after a confirmed booking.

### 4. Ticket & Booking History

- Fetch ticket details using PNR.
- View booking history using customer email.
- Ticket response includes:
  - PNR
  - Name & email
  - Flight details
  - Journey date
  - Booking status
  - Passenger list and seat numbers

### 5. Booking Cancellation

- Cancel a booking by PNR.
- Rules enforced:
  - Cannot cancel if already cancelled.
  - Cannot cancel within 24 hours of journey time.
- On successful cancellation:
  - Booking status updated to `CANCELLED`
  - Seats are released back to flight inventory.

### 6. Error Handling & Validation

- Centralised `GlobalExceptionHandler` for:
  - `ResourceNotFoundException`
  - `BadRequestException`
  - Validation errors (`MethodArgumentNotValidException`)
  - Generic `Exception`
- Consistent JSON error structure:
  - `timestamp`
  - `message` or `errors` (for field-level validation errors)

---

## Project Structure (High Level)

```text
src/main/java/com/flightapp
├─ controller        # REST controllers (FlightController)
├─ dto               # Request / response DTOs
├─ entity            # JPA entities (Airline, FlightInventory, Booking, Passenger, etc.)
├─ entity/enums      # Enums (TripType, MealType, Gender, BookingStatus)
├─ exception         # Custom exceptions + GlobalExceptionHandler
├─ repository        # Spring Data JPA repositories
├─ service           # Business logic (BookingService, FlightService, InventoryService)
└─ util              # Utility classes (e.g., PnrGenerator)
