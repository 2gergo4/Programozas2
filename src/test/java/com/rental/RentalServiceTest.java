package com.rental;

import com.rental.domain.Car;
import com.rental.domain.ElectricScooter;
import com.rental.exception.RentalException;
import com.rental.pattern.DiscountPricingStrategy;
import com.rental.persistence.FileVehicleRepository;
import com.rental.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RentalServiceTest {

    private RentalService service;
    private FileVehicleRepository repository;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws RentalException {
        Path testFile = tempDir.resolve("test_vehicles.csv");
        repository = new FileVehicleRepository(testFile.toString());
        service = new RentalService(repository);
    }

    @Test
    void testPolymorphicDailyCostCalculation() throws RentalException {
        Car familyCar = new Car("C1", "Ford", 50.0, false, 5);
        ElectricScooter scooter = new ElectricScooter("S1", "Segway", 20.0, false, 40);

        assertEquals(65.0, familyCar.calculateDailyCost(), 0.01);
        assertEquals(18.0, scooter.calculateDailyCost(), 0.01);
    }

    @Test
    void testRentAndReturnVehicleWithStrategies() throws RentalException {
        Car car = new Car("C1", "BMW", 85.0, false, 4);
        service.addVehicle(car);

        double standardCost = service.rentVehicle("C1", 10);
        assertEquals(900.0, standardCost, 0.01);
        assertTrue(car.isRented());

        assertThrows(RentalException.class, () -> service.rentVehicle("C1", 2));

        service.returnVehicle("C1");
        assertFalse(car.isRented());

        service.setPricingStrategy(new DiscountPricingStrategy());
        double discountedCost = service.rentVehicle("C1", 10);
        assertEquals(720.0, discountedCost, 0.01);
    }

    @Test
    void testValidationThrowsException() {
        assertThrows(RentalException.class, () -> new Car("", "Audi", 50.0, false, 4));
        assertThrows(RentalException.class, () -> new Car("C2", "Audi", -10.0, false, 4));
        assertThrows(RentalException.class, () -> new ElectricScooter("S2", "Xiaomi", 15.0, false, 0));
    }

    @Test
    void testPersistenceSaveAndLoad() throws RentalException {
        service.addVehicle(new Car("C1", "Opel", 40.0, false, 5));
        service.addVehicle(new ElectricScooter("S1", "Ninebot", 20.0, true, 35));
        service.saveData();

        RentalService loadedService = new RentalService(repository);
        assertEquals(2, loadedService.getAllVehicles().size());
        assertEquals("Opel", loadedService.getAllVehicles().get(0).getBrand());
        assertTrue(loadedService.getAllVehicles().get(1).isRented());
    }
}