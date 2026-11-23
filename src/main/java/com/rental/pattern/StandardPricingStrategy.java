package com.rental.pattern;

import com.rental.domain.Vehicle;

public class StandardPricingStrategy implements PricingStrategy {

    @Override
    public double calculateTotal(Vehicle vehicle, int days) {
        return vehicle.calculateDailyCost() * days;
    }

    @Override
    public String getStrategyName() {
        return "Alapértelmezett árazás (0% akció)";
    }
}