package com.rental.pattern;

import com.rental.domain.Vehicle;

public class DiscountPricingStrategy implements PricingStrategy {

    @Override
    public double calculateTotal(Vehicle vehicle, int days) {
        double base = vehicle.calculateDailyCost() * days;
        return (days >= 7) ? base * 0.8 : base;
    }

    @Override
    public String getStrategyName() {
        return "Long-Term Discount (20% off for 7+ days)";
    }
}