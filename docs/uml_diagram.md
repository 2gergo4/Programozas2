# UML Osztálydiagram és Felhasználói Történetek

## UML Class Diagram

```mermaid
classDiagram
    class Comparable~Vehicle~ {
        <<interface>>
        +compareTo(Vehicle o) int
    }

    class Vehicle {
        <<abstract>>
        -String id
        -String brand
        -double baseDailyRate
        -boolean rented
        +Vehicle(String id, String brand, double baseDailyRate, boolean rented)
        +calculateDailyCost()* double
        +toCsvString()* String
        +getId() String
        +getBrand() String
        +getBaseDailyRate() double
        +isRented() boolean
        +setRented(boolean rented) void
        +compareTo(Vehicle other) int
    }

    class Car {
        -int numberOfDoors
        +Car(String id, String brand, double baseDailyRate, boolean rented, int numberOfDoors)
        +getNumberOfDoors() int
        +calculateDailyCost() double
        +toCsvString() String
    }

    class ElectricScooter {
        -int rangeKm
        +ElectricScooter(String id, String brand, double baseDailyRate, boolean rented, int rangeKm)
        +getRangeKm() int
        +calculateDailyCost() double
        +toCsvString() String
    }

    class RentalException {
        +RentalException(String message)
    }

    class PricingStrategy {
        <<interface>>
        +calculateTotal(Vehicle vehicle, int days) double
        +getStrategyName() String
    }

    class StandardPricingStrategy {
        +calculateTotal(Vehicle vehicle, int days) double
        +getStrategyName() String
    }

    class DiscountPricingStrategy {
        +calculateTotal(Vehicle vehicle, int days) double
        +getStrategyName() String
    }

    class FileVehicleRepository {
        -String filePath
        +FileVehicleRepository(String filePath)
        +saveAll(List~Vehicle~ vehicles) void
        +loadAll() List~Vehicle~
    }

    class RentalService {
        -List~Vehicle~ vehicles
        -FileVehicleRepository repository
        -PricingStrategy pricingStrategy
        +RentalService(FileVehicleRepository repository)
        +setPricingStrategy(PricingStrategy strategy) void
        +addVehicle(Vehicle vehicle) void
        +getAllVehicles() List~Vehicle~
        +getVehiclesSortedByCost() List~Vehicle~
        +getVehiclesSortedByBrand() List~Vehicle~
        +rentVehicle(String id, int days) double
        +returnVehicle(String id) void
        +saveData() void
    }

    class ConsoleApp {
        +main(String[] args)$ void
    }

    Comparable~Vehicle~ <|.. Vehicle
    Vehicle <|-- Car
    Vehicle <|-- ElectricScooter
    PricingStrategy <|.. StandardPricingStrategy
    PricingStrategy <|.. DiscountPricingStrategy
    RentalService "1" o-- "*" Vehicle : manages
    RentalService "1" --> "1" FileVehicleRepository : uses
    RentalService "1" --> "1" PricingStrategy : uses
    ConsoleApp --> RentalService : interacts with
    Vehicle ..> RentalException : throws
    RentalService ..> RentalException : throws
    FileVehicleRepository ..> RentalException : throws

