package com.rental.pattern;

import com.rental.domain.Vehicle;

public interface PricingStrategy {
    double calculateTotal(Vehicle vehicle, int days);
    String getStrategyName();
}
