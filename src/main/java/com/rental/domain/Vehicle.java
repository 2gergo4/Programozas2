package com.rental.domain;

import com.rental.exception.RentalException;


public abstract class Vehicle implements Comparable<Vehicle> {


    private String id;
    private String brand;
    private double baseDailyRate;
    private boolean rented;


    public Vehicle(String id, String brand, double baseDailyRate, boolean rented) throws RentalException {
        if (id == null || id.trim().isEmpty()) {
            throw new RentalException("A jármű azonosítója (ID) nem lehet üres!");
        }
        if (brand == null || brand.trim().isEmpty()) {
            throw new RentalException("A márka nem lehet üres!");
        }
        if (baseDailyRate <= 0) {
            throw new RentalException("Az alap napi díjnak nagyobbnak kell lennie 0-nál!");
        }
        this.id = id;
        this.brand = brand;
        this.baseDailyRate = baseDailyRate;
        this.rented = rented;
    }


    public abstract double calculateDailyCost();


    public abstract String toCsvString();


    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public double getBaseDailyRate() {
        return baseDailyRate;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }


    @Override
    public int compareTo(Vehicle other) {
        return Double.compare(this.calculateDailyCost(), other.calculateDailyCost());
    }


    @Override
    public String toString() {
        String status = rented ? "[KIBÉRELVE]" : "[SZABAD]";
        return String.format("%s ID: %s | Márka: %s | Napi díj: %.2f EUR",
                status, id, brand, calculateDailyCost());
    }
}