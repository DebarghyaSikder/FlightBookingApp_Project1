package com.flightapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.PassengerDto;
import com.flightapp.dto.TicketResponse;
import com.flightapp.entity.Booking;
import com.flightapp.entity.FlightInventory;
import com.flightapp.entity.Passenger;
import com.flightapp.entity.enums.BookingStatus;
import com.flightapp.exception.BadRequestException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightInventoryRepository;
import com.flightapp.repository.PassengerRepository;
import com.flightapp.util.PnrGenerator;

@Service
public class BookingService {

    private final FlightInventoryRepository flightRepo;
    private final BookingRepository bookingRepo;
    private final PassengerRepository passengerRepo;
    private final PnrGenerator pnrGenerator;

    public BookingService(FlightInventoryRepository flightRepo,
                          BookingRepository bookingRepo,
                          PassengerRepository passengerRepo,
                          PnrGenerator pnrGenerator) {
        this.flightRepo = flightRepo;
        this.bookingRepo = bookingRepo;
        this.passengerRepo = passengerRepo;
        this.pnrGenerator = pnrGenerator;
    }

    @Transactional
    public TicketResponse bookTicket(Long flightId, BookingRequest request) {
        FlightInventory flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        if (request.getNumberOfSeats() != request.getPassengers().size()) {
            throw new BadRequestException("Number of seats must match passengers size");
        }

        if (flight.getAvailableSeats() < request.getNumberOfSeats()) {
            throw new BadRequestException("Not enough seats available");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - request.getNumberOfSeats());
        flightRepo.save(flight);

        Booking booking = new Booking();
        booking.setPnr(pnrGenerator.generate());
        booking.setFlight(flight);
        booking.setEmail(request.getEmail());
        booking.setName(request.getName());
        booking.setNumSeats(request.getNumberOfSeats());
        booking.setMealType(request.getMealType());
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setJourneyDate(flight.getDepartureTime().toLocalDate());
        booking.setCreatedAt(LocalDateTime.now());
        booking = bookingRepo.save(booking);

        for (PassengerDto dto : request.getPassengers()) {
            Passenger p = new Passenger();
            p.setBooking(booking);
            p.setName(dto.getName());
            p.setGender(dto.getGender());
            p.setAge(dto.getAge());
            p.setSeatNumber(dto.getSeatNumber());
            passengerRepo.save(p);
        }

        return toTicketResponse(booking);
    }

    public TicketResponse getTicketByPnr(String pnr) {
        Booking booking = bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("PNR not found"));
        return toTicketResponse(booking);
    }

    public List<TicketResponse> getBookingHistory(String email) {
        return bookingRepo.findByEmailOrderByCreatedAtDesc(email)
                .stream()
                .map(this::toTicketResponse)
                .toList();
    }

    @Transactional
    public void cancelBooking(String pnr) {
        Booking booking = bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new ResourceNotFoundException("PNR not found"));

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking already cancelled");
        }

        LocalDate journeyDate = booking.getJourneyDate();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cutoff = journeyDate.atStartOfDay().minusHours(24);

        if (!now.isBefore(cutoff)) {
            throw new BadRequestException("Cannot cancel within 24 hours of journey date");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepo.save(booking);

        FlightInventory flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNumSeats());
        flightRepo.save(flight);
    }

    private TicketResponse toTicketResponse(Booking booking) {
        TicketResponse res = new TicketResponse();
        res.setPnr(booking.getPnr());
        res.setName(booking.getName());
        res.setEmail(booking.getEmail());
        res.setMealType(booking.getMealType());
        res.setNumberOfSeats(booking.getNumSeats());
        res.setBookingStatus(booking.getBookingStatus());
        res.setJourneyDate(booking.getJourneyDate());

        FlightInventory flight = booking.getFlight();
        res.setAirlineName(flight.getAirline().getName());
        res.setFlightNumber(flight.getFlightNumber());
        res.setFromPlace(flight.getFromPlace());
        res.setToPlace(flight.getToPlace());
        res.setDepartureTime(flight.getDepartureTime());
        res.setArrivalTime(flight.getArrivalTime());

        List<PassengerDto> passengers = passengerRepo.findByBooking(booking).stream()
                .map(p -> {
                    PassengerDto dto = new PassengerDto();
                    dto.setName(p.getName());
                    dto.setGender(p.getGender());
                    dto.setAge(p.getAge());
                    dto.setSeatNumber(p.getSeatNumber());
                    return dto;
                })
                .toList();
        res.setPassengers(passengers);

        return res;
    }
}
