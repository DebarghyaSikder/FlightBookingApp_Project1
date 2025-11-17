package com.flightapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flightapp.entity.Booking;
import com.flightapp.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    List<Passenger> findByBooking(Booking booking);
}
