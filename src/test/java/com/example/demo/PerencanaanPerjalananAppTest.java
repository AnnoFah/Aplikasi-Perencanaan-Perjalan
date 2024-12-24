package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerencanaanPerjalananAppTest {
    private ObservableList<PerencanaanPerjalananApp.TravelPlan> travelPlans;

    @BeforeEach
    void setUp() {
        travelPlans = FXCollections.observableArrayList();
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
}
