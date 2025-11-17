package com.flightapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flightapp.entity.FlightInventory;

public interface FlightInventoryRepository extends JpaRepository<FlightInventory, Long> {

    List<FlightInventory> findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetween(
            String fromPlace,
            String toPlace,
            LocalDateTime start,
            LocalDateTime end
    );
}
