package com.rental.domain;

import com.rental.exception.RentalException;

public class Car extends Vehicle {

    private int numberOfDoors;

    public Car(String id, String brand, double baseDailyRate, boolean rented, int numberOfDoors) throws RentalException {
        super(id, brand, baseDailyRate, rented);
        if (numberOfDoors < 2 || numberOfDoors > 7) {
            throw new RentalException("Az autó ajtóinak száma 2 és 7 között kell legyen!");
        }
        this.numberOfDoors = numberOfDoors;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    @Override
    public double calculateDailyCost() {
        double extraFee = (numberOfDoors > 4) ? 15.0 : 5.0; // Ha legalább 5 ajtós akkor 15 euró plusz dij.
        return getBaseDailyRate() + extraFee;
    }

    @Override
    public String toCsvString() {
        return String.format("CAR;%s;%s;%.2f;%b;%d",
                getId(), getBrand(), getBaseDailyRate(), isRented(), numberOfDoors);
    }

    @Override
    public String toString() {
        return "[AUTÓ]    " + super.toString() + " | Ajtók száma: " + numberOfDoors;
    }
}