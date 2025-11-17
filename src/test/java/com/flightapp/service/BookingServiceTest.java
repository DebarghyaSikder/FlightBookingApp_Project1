package com.flightapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.flightapp.dto.BookingRequest;
import com.flightapp.dto.PassengerDto;
import com.flightapp.entity.Airline;
import com.flightapp.entity.Booking;
import com.flightapp.entity.FlightInventory;
import com.flightapp.entity.Passenger;
import com.flightapp.entity.enums.BookingStatus;
import com.flightapp.entity.enums.Gender;
import com.flightapp.entity.enums.MealType;
import com.flightapp.exception.BadRequestException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightInventoryRepository;
import com.flightapp.repository.PassengerRepository;
import com.flightapp.util.PnrGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private FlightInventoryRepository flightRepo;

    @Mock
    private BookingRepository bookingRepo;

    @Mock
    private PassengerRepository passengerRepo;

    @Mock
    private PnrGenerator pnrGenerator;

    @InjectMocks
    private BookingService bookingService;

    private FlightInventory flight;
    private BookingRequest bookingRequest;

    @BeforeEach
    void setUp() {
        
        Airline airline = new Airline();
        airline.setName("Indigo");
        airline.setLogoUrl("https://example.com/logo.png");
        airline.setCode("6E");

        flight = new FlightInventory();
        flight.setId(1L);
        flight.setAvailableSeats(10);
        flight.setDepartureTime(LocalDateTime.now().plusDays(5));
        flight.setAirline(airline);     
        flight.setFlightNumber("6E123"); 

        PassengerDto p1 = new PassengerDto();
        p1.setName("Passenger One");
        p1.setGender(Gender.MALE);
        p1.setAge(25);
        p1.setSeatNumber("10A");

        PassengerDto p2 = new PassengerDto();
        p2.setName("Passenger Two");
        p2.setGender(Gender.FEMALE);
        p2.setAge(23);
        p2.setSeatNumber("10B");

        bookingRequest = new BookingRequest();
        bookingRequest.setName("Debarghya Sikder");
        bookingRequest.setEmail("deb@example.com");
        bookingRequest.setMealType(MealType.VEG);
        bookingRequest.setNumberOfSeats(2);
        bookingRequest.setPassengers(List.of(p1, p2));
    }


    @Test
    void bookTicket_successfulBooking_reducesAvailableSeatsAndSavesBooking() {
        when(flightRepo.findById(1L)).thenReturn(Optional.of(flight));

        // Mock PNR generation
        when(pnrGenerator.generate()).thenReturn("PNR12345");

        when(bookingRepo.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            b.setId(100L);
            return b;
        });

        Passenger passenger1 = new Passenger();
        passenger1.setName("Passenger One");
        passenger1.setGender(Gender.MALE);
        passenger1.setAge(25);
        passenger1.setSeatNumber("10A");

        Passenger passenger2 = new Passenger();
        passenger2.setName("Passenger Two");
        passenger2.setGender(Gender.FEMALE);
        passenger2.setAge(23);
        passenger2.setSeatNumber("10B");

        when(passengerRepo.findByBooking(any(Booking.class)))
                .thenReturn(List.of(passenger1, passenger2));

        var ticket = bookingService.bookTicket(1L, bookingRequest);

        assertEquals("PNR12345", ticket.getPnr());
        assertEquals(2, ticket.getNumberOfSeats());
        assertEquals("Debarghya Sikder", ticket.getName());
        assertEquals(BookingStatus.CONFIRMED, ticket.getBookingStatus());

        assertEquals(2, ticket.getPassengers().size());

        assertEquals(8, flight.getAvailableSeats());

        verify(bookingRepo, times(1)).save(any(Booking.class));
        verify(passengerRepo, times(2)).save(any());
    }


    @Test
    void bookTicket_throwsWhenFlightNotFound() {
        when(flightRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                     () -> bookingService.bookTicket(99L, bookingRequest));
    }

    @Test
    void bookTicket_throwsWhenSeatsMismatch() {
        // numberOfSeats != passengers.size()
        bookingRequest.setNumberOfSeats(3);

        when(flightRepo.findById(1L)).thenReturn(Optional.of(flight));

        assertThrows(BadRequestException.class,
                     () -> bookingService.bookTicket(1L, bookingRequest));
    }

    @Test
    void bookTicket_throwsWhenInsufficientSeats() {
        flight.setAvailableSeats(1);
        when(flightRepo.findById(1L)).thenReturn(Optional.of(flight));

        assertThrows(BadRequestException.class,
                     () -> bookingService.bookTicket(1L, bookingRequest));
    }

    @Test
    void cancelBooking_successfulCancellation_updatesStatusAndSeats() {
        Booking booking = new Booking();
        booking.setPnr("PNR12345");
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setNumSeats(2);
        booking.setJourneyDate(LocalDateTime.now().plusDays(3).toLocalDate()); 
        booking.setFlight(flight);

        when(bookingRepo.findByPnr("PNR12345")).thenReturn(Optional.of(booking));

        bookingService.cancelBooking("PNR12345");

        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus());
        assertEquals(12, flight.getAvailableSeats());

        verify(bookingRepo, times(1)).save(booking);
        verify(flightRepo, times(1)).save(flight);
    }

    @Test
    void cancelBooking_throwsWhenAlreadyCancelled() {
        Booking booking = new Booking();
        booking.setPnr("PNR12345");
        booking.setBookingStatus(BookingStatus.CANCELLED);

        when(bookingRepo.findByPnr("PNR12345")).thenReturn(Optional.of(booking));

        assertThrows(BadRequestException.class,
                     () -> bookingService.cancelBooking("PNR12345"));
    }

    @Test
    void cancelBooking_throwsWhenWithin24Hours() {
        Booking booking = new Booking();
        booking.setPnr("PNR12345");
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        booking.setJourneyDate(LocalDateTime.now().plusDays(1).toLocalDate());

        when(bookingRepo.findByPnr("PNR12345")).thenReturn(Optional.of(booking));

        assertThrows(BadRequestException.class,
                     () -> bookingService.cancelBooking("PNR12345"));
    }
}
