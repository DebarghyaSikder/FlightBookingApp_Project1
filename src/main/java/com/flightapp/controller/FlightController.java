package com.flightapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.FlightSearchRequest;
import com.flightapp.dto.FlightSearchResponse;
import com.flightapp.dto.TicketResponse;
import com.flightapp.entity.FlightInventory;
import com.flightapp.service.BookingService;
import com.flightapp.service.FlightServiceTest;
import com.flightapp.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/flight")
public class FlightController {

    private final InventoryService inventoryService;
    private final FlightServiceTest flightService;
    private final BookingService bookingService;

    public FlightController(InventoryService inventoryService,
                            FlightServiceTest flightService,
                            BookingService bookingService) {
        this.inventoryService = inventoryService;
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    // POST /api/v1.0/flight/airline/inventory/add
    @PostMapping("/airline/inventory/add")
    public ResponseEntity<FlightInventory> addInventory(@RequestBody @Valid FlightInventory inventory) {
        FlightInventory saved = inventoryService.addInventory(inventory);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // POST /api/v1.0/flight/search
    @PostMapping("/search")
    public ResponseEntity<List<FlightSearchResponse>> searchFlights(
            @RequestBody @Valid FlightSearchRequest request) {
        List<FlightSearchResponse> flights = flightService.searchFlights(request);
        return ResponseEntity.ok(flights);
    }

    // POST /api/v1.0/flight/booking/{flightId}
    @PostMapping("/booking/{flightId}")
    public ResponseEntity<TicketResponse> bookTicket(
            @PathVariable("flightId") Long flightId,
            @RequestBody @Valid BookingRequest request) {
        TicketResponse ticket = bookingService.bookTicket(flightId, request);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    // GET /api/v1.0/flight/ticket/{pnr}
    @GetMapping("/ticket/{pnr}")
    public ResponseEntity<TicketResponse> getTicket(@PathVariable("pnr") String pnr) {
        TicketResponse ticket = bookingService.getTicketByPnr(pnr);
        return ResponseEntity.ok(ticket);
    }

    // GET /api/v1.0/flight/booking/history/{emailId}
    @GetMapping("/booking/history/{emailId}")
    public ResponseEntity<List<TicketResponse>> getHistory(@PathVariable("emailId") String emailId) {
        List<TicketResponse> history = bookingService.getBookingHistory(emailId);
        return ResponseEntity.ok(history);
    }

    // DELETE /api/v1.0/flight/booking/cancel/{pnr}
    @DeleteMapping("/booking/cancel/{pnr}")
    public ResponseEntity<Void> cancelBooking(@PathVariable("pnr") String pnr) {
        bookingService.cancelBooking(pnr);
        return ResponseEntity.noContent().build();
    }
}
