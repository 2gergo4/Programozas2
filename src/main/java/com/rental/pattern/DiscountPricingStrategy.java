package com.rental.pattern;

import com.rental.domain.Vehicle;

public class DiscountPricingStrategy implements PricingStrategy {

    @Override
    public double calculateTotal(Vehicle vehicle, int days) {
        double base = vehicle.calculateDailyCost() * days;
        return (days >= 7) ? base * 0.8 : base; // ha napok szama legalabb 7 akkor 20% kedvezmenyt kap a berlo. a baset megszorozza 0.8-al ha igaz ha pedig hamis akkor marad a base.
    }

    @Override
    public String getStrategyName() {
        return "Hosszú távra szóló árengedmény (20% kedvezmény 7 vagy több napig)";
    }
}