package com.rental.exception;

// Az "extends Exception" azt jelenti, hogy ez az osztály a Java beépített Hiba (Exception)
// osztályából öröklődik, tehát a Java tudni fogja, hogy ez egy hibaüzenet.
public class RentalException extends Exception {

    // Ez a KONSTRUKTOR: akkor fut le, amikor létrehozzuk a hibát egy szöveges üzenettel.
    public RentalException(String message) {
        super(message); // Továbbadjuk a hiba szövegét az ősosztálynak (Exception).
    }
}