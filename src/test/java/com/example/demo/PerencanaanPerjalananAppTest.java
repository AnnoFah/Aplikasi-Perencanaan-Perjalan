package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PerencanaanPerjalananAppTest {

    @Test
    void testJarakAntarKota() {
        Map<String, Map<String, Integer>> jarakAntarKota = PerencanaanPerjalananApp.getJarakAntarKota();

        assertNotNull(jarakAntarKota, "Data jarak antar kota tidak boleh null.");
        assertEquals(780, jarakAntarKota.get("Jakarta").get("Surabaya"), "Jarak Jakarta-Surabaya salah.");
        assertEquals(150, jarakAntarKota.get("Jakarta").get("Bandung"), "Jarak Jakarta-Bandung salah.");
        assertNull(jarakAntarKota.get("Jakarta").get("Medan"), "Data Medan seharusnya null.");
    }

    @Test
    void testTarifTransportasi() {
        Map<String, Integer> tarifTransportasi = PerencanaanPerjalananApp.getTarifTransportasi();

        assertNotNull(tarifTransportasi, "Data tarif transportasi tidak boleh null.");
        assertEquals(2000, tarifTransportasi.get("Mobil"), "Tarif untuk Mobil salah.");
        assertEquals(1500, tarifTransportasi.get("Motor"), "Tarif untuk Motor salah.");
        assertNull(tarifTransportasi.get("Helikopter"), "Data Helikopter seharusnya null.");
    }

    @Test
    void testPerhitunganBiaya() {
        Map<String, Map<String, Integer>> jarakAntarKota = PerencanaanPerjalananApp.getJarakAntarKota();
        Map<String, Integer> tarifTransportasi = PerencanaanPerjalananApp.getTarifTransportasi();

        int jarak = jarakAntarKota.get("Jakarta").get("Surabaya");
        int tarif = tarifTransportasi.get("Mobil");

        int biaya = jarak * tarif;

        assertEquals(780 * 2000, biaya, "Perhitungan biaya salah.");
    }

    @Test
    void testTambahPerjalanan() {
        PerencanaanPerjalananApp.TravelPlan travelPlan = new PerencanaanPerjalananApp.TravelPlan(
                "Jakarta",
                "Surabaya",
                "2024-12-25",
                "Mobil",
                780,
                1560000
        );

        assertNotNull(travelPlan, "TravelPlan tidak boleh null.");
        assertEquals("Jakarta", travelPlan.getAsal(), "Asal perjalanan salah.");
        assertEquals("Surabaya", travelPlan.getDestinasi(), "Destinasi perjalanan salah.");
        assertEquals("Mobil", travelPlan.getTransportasi(), "Transportasi salah.");
        assertEquals(780, travelPlan.getJarak(), "Jarak perjalanan salah.");
        assertEquals(1560000, travelPlan.getBiayaRekomendasi(), "Biaya rekomendasi salah.");
    }

    @Test
    void testHapusPerjalanan() {
        PerencanaanPerjalananApp.TravelPlan travelPlan = new PerencanaanPerjalananApp.TravelPlan(
                "Jakarta",
                "Surabaya",
                "2024-12-25",
                "Mobil",
                780,
                1560000
        );

        // Menggunakan daftar sederhana untuk pengujian
        java.util.List<PerencanaanPerjalananApp.TravelPlan> travelPlans = new java.util.ArrayList<>();
        travelPlans.add(travelPlan);

        travelPlans.remove(travelPlan);

        assertTrue(travelPlans.isEmpty(), "Daftar perjalanan seharusnya kosong setelah dihapus.");
    }

    @Test
    void testExceptionHandling() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Map<String, Map<String, Integer>> jarakAntarKota = PerencanaanPerjalananApp.getJarakAntarKota();
            jarakAntarKota.get("Medan").get("Bandung"); // Data tidak ada
        });

        assertTrue(exception.getMessage().contains("null"), "Pesan exception seharusnya menyebutkan null.");
    }

    @Test
    void testTransportasiTidakValid() {
        Map<String, Integer> tarifTransportasi = PerencanaanPerjalananApp.getTarifTransportasi();

        int tarifInvalid = tarifTransportasi.getOrDefault("Helikopter", 0);

        assertEquals(0, tarifInvalid, "Tarif untuk transportasi tidak valid seharusnya 0.");
    }
}
