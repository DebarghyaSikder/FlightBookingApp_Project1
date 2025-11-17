package com.flightapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flightapp.entity.FlightInventory;
import com.flightapp.repository.FlightInventoryRepository;

@Service
public class InventoryService {

    private final FlightInventoryRepository flightRepo;

    public InventoryService(FlightInventoryRepository flightRepo) {
        this.flightRepo = flightRepo;
    }

    @Transactional
    public FlightInventory addInventory(FlightInventory inventory) {
        if (inventory.getTotalSeats() == null || inventory.getTotalSeats() <= 0) {
            throw new IllegalArgumentException("Total seats must be positive");
        }
        inventory.setAvailableSeats(inventory.getTotalSeats());
        return flightRepo.save(inventory);
    }
}
