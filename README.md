# Járműkölcsönző Rendszer (Vehicle Rental System)

Java nyelven, Maven keretrendszerrel készült objektumorientált (OOP) konzolos alkalmazás járműflotta kezelésére, bérbeadására és díjszámítására.

## 1. Problémafelvetés (Problem Statement)
A kis- és közepes méretű járműkölcsönzők számára nehézséget okoz a különböző típusú járművek (autók, elektromos rollerek) eltérő díjszabásának követése, az aktuális foglaltság nyilvántartása és az időszakos kedvezmények (pl. hosszú távú bérlés) rugalmas alkalmazása. Az alkalmazás ezt a problémát oldja meg egy átlátható konzolos felülettel és automatikus fájlalapú adattárolással.

## 2. Előfeltételek (Prerequisites)
* Java Development Kit (JDK): 17 vagy újabb
* Apache Maven: 3.8 vagy újabb

## 3. Fordítás, Tesztelés és Futtatás (Build, Test & Run)

A projekt gyökérmappájából futtatható parancsok:

Fordítás és JUnit tesztek futtatása:
mvn clean test

Alkalmazás fordítása és indítása:
mvn clean compile exec:java

## 4. Megvalósított OOP követelmények
* Egységbezárás (Encapsulation): Minden mező private láthatóságú, az érvényes állapotot konstruktorok és getter/setter metódusok biztosítják.
* Absztrakció (Abstraction): A Vehicle absztrakt ősosztály és a PricingStrategy interfész definiálja a rendszer alapvető szerződéseit.
* Öröklődés és Polimorfizmus: A Car és az ElectricScooter a Vehicle osztályból öröklődik, és futásidőben eltérően valósítja meg a calculateDailyCost() metódust (biztosítási felár vs. zöld kedvezmény).
* Kapcsolatok: A RentalService aggregációval kezeli a Vehicle objektumokat (1 szolgáltatás -> * jármű), valamint asszociációban áll a FileVehicleRepository és PricingStrategy komponensekkel.
* Gyűjtemények és Rendezés: ArrayList<Vehicle> kollekció használata Comparable<Vehicle> (napi díj szerinti) és Comparator (márka szerinti) rendezéssel.
* Kivételkezelés: Saját RentalException osztály az üzleti szabályok (pl. negatív díj, foglalt jármű bérlése) megsértésének kezelésére.
* Perzisztencia: CSV formátumú adatmentés és visszatöltés try-with-resources szerkezettel (vehicles.csv).

## 5. Választott Tervezési Minta (Design Pattern): Strategy
* Probléma: A bérleti díj kiszámításának szabályai (normál díjszabás vs. 7 nap feletti 20%-os kedvezmény) dinamikusan változhatnak anélkül, hogy a járművek vagy a kölcsönző kódját módosítani kellene.
* Résztvevő osztályok: PricingStrategy (interfész), StandardPricingStrategy, DiscountPricingStrategy (konkrét stratégiák), valamint a RentalService (kliens/kontextus).
* Előny: Új árképzési akciók (pl. hétvégi kedvezmény) bármikor hozzáadhatók új osztály létrehozásával, a meglévő kód módosítása nélkül (Open/Closed Principle).

## 6. Tervezési döntések és ismert korlátok
* Döntések: A könnyű olvashatóság és külső függőségek elkerülése érdekében pontosvesszővel tagolt CSV fájlt használunk az adattároláshoz.
* Korlátok: A jelenlegi verzió egyfelhasználós konzolos felülettel rendelkezik, és nem tárolja a korábbi, már lezárt bérlések historikus naplóját.

## 7. Minta használat (Sample Usage)
    === JARMUKOLCSONZO RENDSZER ===
    Aktualis arkepzes: Standard Pricing (0% discount)
    1. Osszes jarmu listazasa
    ...
    Valassz menupontot: 1
    [AUTÓ]    [SZABAD] ID: C-101 | Márka: Toyota Corolla | Napi díj: 60,00 EUR | Ajtók száma: 5
    [ROLLER]  [SZABAD] ID: S-201 | Márka: Xiaomi Pro 2 | Napi díj: 13,50 EUR | Hatótáv: 45 km

## Készítette
* Benzsay Gergő (WENQL6)