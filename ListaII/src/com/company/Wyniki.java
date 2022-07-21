package com.company;

public class Wyniki {
    public static void main(String[] args) {

        // pierwsza liczba - ilość testów
        // druga liczba - ilość żądań
        // trzecia liczba - rozmiar dysku
        // czwarta liczba - ilość żądań po prawej stronie dysku w procentach

        Results results = new Results(3, 20, 20, 50);
        results.ShowResults();
    }
}
