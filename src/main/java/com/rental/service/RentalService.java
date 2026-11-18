package com.rental.service;

import com.rental.domain.Vehicle;
import com.rental.exception.RentalException;
import com.rental.pattern.PricingStrategy;
import com.rental.pattern.StandardPricingStrategy;
import com.rental.persistence.FileVehicleRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RentalService {

    private final List<Vehicle> vehicles;
    private final FileVehicleRepository repository;
    private PricingStrategy pricingStrategy;

    public RentalService(FileVehicleRepository repository) throws RentalException {
        this.repository = repository;
        this.vehicles = new ArrayList<>(repository.loadAll());
        this.pricingStrategy = new StandardPricingStrategy();
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public void addVehicle(Vehicle vehicle) throws RentalException {
        for (Vehicle v : vehicles) {
            if (v.getId().equalsIgnoreCase(vehicle.getId())) {
                throw new RentalException("Mar letezik jarmu ezzel az ID-val: " + vehicle.getId());
            }
        }
        vehicles.add(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public List<Vehicle> getVehiclesSortedByCost() {
        List<Vehicle> sorted = new ArrayList<>(vehicles);
        Collections.sort(sorted);
        return sorted;
    }

    public List<Vehicle> getVehiclesSortedByBrand() {
        List<Vehicle> sorted = new ArrayList<>(vehicles);
        sorted.sort(Comparator.comparing(Vehicle::getBrand, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    public double rentVehicle(String id, int days) throws RentalException {
        if (days <= 0) {
            throw new RentalException("A berlesi napok szamanak legalabb 1-nek kell lennie!");
        }
        Vehicle vehicle = findById(id);
        if (vehicle.isRented()) {
            throw new RentalException("Ez a jarmu mar ki van berelve!");
        }
        vehicle.setRented(true);
        return pricingStrategy.calculateTotal(vehicle, days);
    }

    public void returnVehicle(String id) throws RentalException {
        Vehicle vehicle = findById(id);
        if (!vehicle.isRented()) {
            throw new RentalException("Ez a jarmu nincs kiberelve!");
        }
        vehicle.setRented(false);
    }

    public void saveData() throws RentalException {
        repository.saveAll(vehicles);
    }

    private Vehicle findById(String id) throws RentalException {
        for (Vehicle v : vehicles) {
            if (v.getId().equalsIgnoreCase(id)) {
                return v;
            }
        }
        throw new RentalException("Nem talalhato jarmu ezzel az azonosito-val: " + id);
    }
}