package com.rental.domain;

import com.rental.exception.RentalException;

public class ElectricScooter extends Vehicle {

    private int rangeKm;

    public ElectricScooter(String id, String brand, double baseDailyRate, boolean rented, int rangeKm) throws RentalException {
        super(id, brand, baseDailyRate, rented);
        if (rangeKm <= 0) {
            throw new RentalException("A roller hatótávjának nagyobbnak kell lennie 0 km-nél!");
        }
        this.rangeKm = rangeKm;
    }

    public int getRangeKm() {
        return rangeKm;
    }

    @Override
    public double calculateDailyCost() {
        return getBaseDailyRate() * 0.9;
    }

    @Override
    public String toCsvString() {
        return String.format("SCOOTER;%s;%s;%.2f;%b;%d",
                getId(), getBrand(), getBaseDailyRate(), isRented(), rangeKm);
    }

    @Override
    public String toString() {
        return "[ROLLER]  " + super.toString() + " | Hatótáv: " + rangeKm + " km";
    }
}
