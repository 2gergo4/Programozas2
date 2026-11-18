package com.rental.ui;

import com.rental.domain.Car;
import com.rental.domain.ElectricScooter;
import com.rental.domain.Vehicle;
import com.rental.exception.RentalException;
import com.rental.pattern.DiscountPricingStrategy;
import com.rental.pattern.StandardPricingStrategy;
import com.rental.persistence.FileVehicleRepository;
import com.rental.service.RentalService;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    private static final String DATA_FILE = "vehicles.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RentalService service;

        try {
            FileVehicleRepository repository = new FileVehicleRepository(DATA_FILE);
            service = new RentalService(repository);
            if (service.getAllVehicles().isEmpty()) {
                seedInitialData(service);
            }
        } catch (RentalException e) {
            System.out.println("Inditasi hiba: " + e.getMessage());
            return;
        }

        boolean running = true;
        while (running) {
            printMenu(service);
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        listVehicles(service.getAllVehicles());
                        break;
                    case "2":
                        listVehicles(service.getVehiclesSortedByCost());
                        break;
                    case "3":
                        listVehicles(service.getVehiclesSortedByBrand());
                        break;
                    case "4":
                        addCar(scanner, service);
                        break;
                    case "5":
                        addScooter(scanner, service);
                        break;
                    case "6":
                        rentVehicle(scanner, service);
                        break;
                    case "7":
                        returnVehicle(scanner, service);
                        break;
                    case "8":
                        switchStrategy(scanner, service);
                        break;
                    case "9":
                        service.saveData();
                        System.out.println("Adatok elmentve a(z) " + DATA_FILE + " fajlba. Viszlat!");
                        running = false;
                        break;
                    default:
                        System.out.println("Ervenytelen valasztas, probald ujra!");
                }
            } catch (RentalException e) {
                System.out.println("HIBA: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("HIBA: Szamot kell megadni!");
            }
        }
        scanner.close();
    }

    private static void printMenu(RentalService service) {
        System.out.println("\n=== JARMUKOLCSONZO RENDSZER ===");
        System.out.println("Aktualis arkepzes: " + service.getPricingStrategy().getStrategyName());
        System.out.println("1. Osszes jarmu listazasa");
        System.out.println("2. Listazas napi dij szerint rendezve");
        System.out.println("3. Listazas marka szerint rendezve");
        System.out.println("4. Uj auto hozzaadasa");
        System.out.println("5. Uj elektromos roller hozzaadasa");
        System.out.println("6. Jarmu berlese");
        System.out.println("7. Jarmu visszahozatala");
        System.out.println("8. Arkepzesi strategia valtasa");
        System.out.println("9. Mentes es kilepes");
        System.out.print("Valassz menupontot: ");
    }

    private static void listVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("Nincs rogzitett jarmu a rendszerben.");
            return;
        }
        System.out.println("\n--- Jarmuvek ---");
        for (Vehicle v : vehicles) {
            System.out.println(v);
        }
    }

    private static void addCar(Scanner scanner, RentalService service) throws RentalException {
        System.out.print("Azonosito (ID): ");
        String id = scanner.nextLine();
        System.out.print("Marka: ");
        String brand = scanner.nextLine();
        System.out.print("Alap napi dij (EUR): ");
        double rate = Double.parseDouble(scanner.nextLine());
        System.out.print("Ajtok szama: ");
        int doors = Integer.parseInt(scanner.nextLine());

        service.addVehicle(new Car(id, brand, rate, false, doors));
        System.out.println("Auto sikeresen hozzaadva!");
    }

    private static void addScooter(Scanner scanner, RentalService service) throws RentalException {
        System.out.print("Azonosito (ID): ");
        String id = scanner.nextLine();
        System.out.print("Marka: ");
        String brand = scanner.nextLine();
        System.out.print("Alap napi dij (EUR): ");
        double rate = Double.parseDouble(scanner.nextLine());
        System.out.print("Hatotav (km): ");
        int range = Integer.parseInt(scanner.nextLine());

        service.addVehicle(new ElectricScooter(id, brand, rate, false, range));
        System.out.println("Roller sikeresen hozzaadva!");
    }

    private static void rentVehicle(Scanner scanner, RentalService service) throws RentalException {
        System.out.print("Berelni kivant jarmu ID-ja: ");
        String id = scanner.nextLine();
        System.out.print("Napok szama: ");
        int days = Integer.parseInt(scanner.nextLine());

        double total = service.rentVehicle(id, days);
        System.out.printf("Sikeres berles! Fizetendo osszeg: %.2f EUR%n", total);
    }

    private static void returnVehicle(Scanner scanner, RentalService service) throws RentalException {
        System.out.print("Visszahozott jarmu ID-ja: ");
        String id = scanner.nextLine();

        service.returnVehicle(id);
        System.out.println("Jarmu sikeresen visszavetelezve!");
    }

    private static void switchStrategy(Scanner scanner, RentalService service) {
        System.out.println("1. Standard arkepzes");
        System.out.println("2. Hosszú tavu kedvezmeny (7+ nap eseten 20% kedvezmeny)");
        System.out.print("Valassz strategiat: ");
        String s = scanner.nextLine().trim();
        if ("2".equals(s)) {
            service.setPricingStrategy(new DiscountPricingStrategy());
        } else {
            service.setPricingStrategy(new StandardPricingStrategy());
        }
        System.out.println("Strategia modositva: " + service.getPricingStrategy().getStrategyName());
    }

    private static void seedInitialData(RentalService service) throws RentalException {
        service.addVehicle(new Car("C-101", "Toyota Corolla", 45.0, false, 5));
        service.addVehicle(new Car("C-102", "Mazda MX-5", 65.0, false, 2));
        service.addVehicle(new ElectricScooter("S-201", "Xiaomi Pro 2", 15.0, false, 45));
    }
}