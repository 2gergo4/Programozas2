package com.rental.domain;

import com.rental.exception.RentalException;

// "abstract class": Nem lehet belőle sima "Járművet" létrehozni, csak konkrét Autót vagy Rollert.
// "implements Comparable<Vehicle>": Megígérjük a Java-nak, hogy két járművet össze tudunk hasonlítani (sorbarendezéshez).
public abstract class Vehicle implements Comparable<Vehicle> {

    // ENCAPSULATION (Egységbezárás): Minden adat "private", így kívülről nem lehet őket véletlenül elrontani.
    private String id;
    private String brand;
    private double baseDailyRate;
    private boolean rented;

    // KONSTRUKTOR: Ez hozza létre a járművet. Rögtön ellenőrizzük is az adatokat (Validation)!
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

    // ABSTRACTION & POLYMORPHISM:
    // Ez egy "absztrakt metódus". Nincs törzse (nincs kapcsos zárójel {}), mert majd az Autó és a Roller
    // saját maga dönti el, hogyan számolja ki a napi díját!
    public abstract double calculateDailyCost();

    // Segédmetódus a fájlba mentéshez (ezt is minden gyerekosztály maga valósítja meg)
    public abstract String toCsvString();

    // GETTEREK ÉS SETTEREK: Ezeken a publikus függvényeken keresztül lehet biztonságosan lekérni
    // vagy módosítani a privát adatokat.
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

    // SORBARENDEZÉS (Comparable): Alapértelmezetten a napi költség szerint rendezzük sorba a járműveket.
    @Override
    public int compareTo(Vehicle other) {
        return Double.compare(this.calculateDailyCost(), other.calculateDailyCost());
    }

    // Szép szöveges megjelenítés, amikor kiírjuk a konzolra a jármű adatait
    @Override
    public String toString() {
        String status = rented ? "[KIBÉRELVE]" : "[SZABAD]";
        return String.format("%s ID: %s | Márka: %s | Napi díj: %.2f EUR",
                status, id, brand, calculateDailyCost());
    }
}