package com.rental.persistence;

import com.rental.domain.Car;
import com.rental.domain.ElectricScooter;
import com.rental.domain.Vehicle;
import com.rental.exception.RentalException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileVehicleRepository {

    private final String filePath;

    public FileVehicleRepository(String filePath) {
        this.filePath = filePath;
    }

    public void saveAll(List<Vehicle> vehicles) throws RentalException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Vehicle v : vehicles) {
                writer.write(v.toCsvString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RentalException("Hiba a fajl mentese kozben: " + e.getMessage());
        }
    }

    public List<Vehicle> loadAll() throws RentalException {
        List<Vehicle> vehicles = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return vehicles;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                String type = parts[0];
                String id = parts[1];
                String brand = parts[2];
                double rate = Double.parseDouble(parts[3].replace(",", "."));
                boolean rented = Boolean.parseBoolean(parts[4]);
                int extra = Integer.parseInt(parts[5]);

                if ("CAR".equals(type)) {
                    vehicles.add(new Car(id, brand, rate, rented, extra));
                } else if ("SCOOTER".equals(type)) {
                    vehicles.add(new ElectricScooter(id, brand, rate, rented, extra));
                }
            }
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new RentalException("Hiba a fajl beolvasasa kozben: " + e.getMessage());
        }
        return vehicles;
    }
}