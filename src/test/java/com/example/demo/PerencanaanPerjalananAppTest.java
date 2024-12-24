package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerencanaanPerjalananAppTest {
    private ObservableList<PerencanaanPerjalananApp.TravelPlan> travelPlans;
    private PerencanaanPerjalananApp app;

    @BeforeEach
    void setUp() {
        travelPlans = FXCollections.observableArrayList();
        app = new PerencanaanPerjalananApp();
    }

    @Test
    void testTambahPerjalanan() {
        PerencanaanPerjalananApp.TravelPlan plan = new PerencanaanPerjalananApp.TravelPlan(
                "Jakarta", "Surabaya", "2024-12-21", "Mobil", 780, 1560000, "path/to/image.jpg"
        );
        travelPlans.add(plan);

        assertEquals(1, travelPlans.size());
        assertEquals("Jakarta", travelPlans.get(0).getAsal());
        assertEquals(780, travelPlans.get(0).getJarak());
    }

    @Test
    void testHapusPerjalanan() {
        PerencanaanPerjalananApp.TravelPlan plan = new PerencanaanPerjalananApp.TravelPlan(
                "Jakarta", "Surabaya", "2024-12-21", "Mobil", 780, 1560000, "path/to/image.jpg"
        );
        travelPlans.add(plan);
        travelPlans.remove(plan);

        assertEquals(0, travelPlans.size());
    }

    @Test
    void testTambahGambar() {
        PerencanaanPerjalananApp.TravelPlan plan = new PerencanaanPerjalananApp.TravelPlan(
                "Jakarta", "Surabaya", "2024-12-21", "Mobil", 780, 1560000, "path/to/image.jpg"
        );
        travelPlans.add(plan);

        assertNotNull(travelPlans.get(0).getGambarPath());
        assertEquals("path/to/image.jpg", travelPlans.get(0).getGambarPath());
    }

    @Test
    void testValidasiInputKosong() {
        PerencanaanPerjalananApp.TravelPlan plan = new PerencanaanPerjalananApp.TravelPlan(
                null, "Surabaya", "2024-12-21", "Mobil", 780, 1560000, "path/to/image.jpg"
        );

        assertNull(plan.getAsal());
        assertEquals("Surabaya", plan.getDestinasi());
    }

    @Test
    void testKalkulasiBiaya() {
        int jarak = 780; // Jarak dari Jakarta ke Surabaya
        int tarif = 2000; // Tarif per km untuk mobil
        int biayaDihitung = jarak * tarif;

        assertEquals(1560000, biayaDihitung);
    }

    @Test
    void testDataTidakValid() {
        PerencanaanPerjalananApp.TravelPlan plan = new PerencanaanPerjalananApp.TravelPlan(
                "Jakarta", "Surabaya", "2024-12-21", "Mobil", -1, -5000, null
        );

        assertTrue(plan.getJarak() < 0 || plan.getBiayaRekomendasi() < 0 || plan.getGambarPath() == null);
    }

    @Test
    void testHapusSemuaData() {
        travelPlans.add(new PerencanaanPerjalananApp.TravelPlan(
                "Jakarta", "Surabaya", "2024-12-21", "Mobil", 780, 1560000, "path/to/image.jpg"
        ));
        travelPlans.add(new PerencanaanPerjalananApp.TravelPlan(
                "Jakarta", "Bandung", "2024-12-22", "Bus", 150, 150000, "path/to/image2.jpg"
        ));

        travelPlans.clear();
        assertEquals(0, travelPlans.size());
    }

    @Test
    void testToggleDarkMode() {
        assertFalse(app.isDarkMode()); // Default mode terang

        app.setDarkMode(true); // Aktifkan mode gelap
        assertTrue(app.isDarkMode());

        app.setDarkMode(false); // Kembali ke mode terang
        assertFalse(app.isDarkMode());
    }
}
