package com.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PerencanaanPerjalananApp extends Application {
    private TableView<TravelPlan> table;
    private ObservableList<TravelPlan> travelPlans;
    private boolean darkMode = false;

    // Data jarak antara kota (dalam kilometer)
    private static final Map<String, Map<String, Integer>> jarakAntarKota = new HashMap<>();

    // Tarif per kilometer berdasarkan transportasi
    private static final Map<String, Integer> tarifTransportasi = new HashMap<>();

    static {
        jarakAntarKota.put("Jakarta", Map.of("Surabaya", 780, "Bandung", 150, "Yogyakarta", 530));
        jarakAntarKota.put("Surabaya", Map.of("Jakarta", 780, "Yogyakarta", 330, "Bandung", 725));
        jarakAntarKota.put("Bandung", Map.of("Jakarta", 150, "Surabaya", 725, "Yogyakarta", 410));
        jarakAntarKota.put("Yogyakarta", Map.of("Jakarta", 530, "Surabaya", 330, "Bandung", 410));

        tarifTransportasi.put("Mobil", 2000);
        tarifTransportasi.put("Motor", 1500);
        tarifTransportasi.put("Bus", 1000);
        tarifTransportasi.put("Kereta", 500);
        tarifTransportasi.put("Pesawat", 5000);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikasi Perencanaan Perjalanan");

        travelPlans = FXCollections.observableArrayList();

        // Tabel
        table = new TableView<>();
        setupTable();

        // ToolBar
        Button addButton = new Button("Tambah Perjalanan");
        addButton.setOnAction(e -> tambahPerjalanan(primaryStage));

        Button deleteButton = new Button("Hapus Perjalanan");
        deleteButton.setOnAction(e -> hapusPerjalanan());

        Button viewGalleryButton = new Button("Lihat Galeri");
        viewGalleryButton.setOnAction(e -> tampilkanGaleri(primaryStage));

        Button toggleDarkModeButton = new Button("Mode Gelap");
        toggleDarkModeButton.setOnAction(e -> toggleDarkMode(primaryStage.getScene()));

        ToolBar toolBar = new ToolBar(addButton, deleteButton, viewGalleryButton, toggleDarkModeButton);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(toolBar);
        mainLayout.setCenter(table);

        Scene scene = new Scene(mainLayout, 1000, 600);
        applyLightMode(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupTable() {
        TableColumn<TravelPlan, String> asalCol = new TableColumn<>("Asal");
        asalCol.setCellValueFactory(new PropertyValueFactory<>("asal"));
        asalCol.setMinWidth(150);

        TableColumn<TravelPlan, String> destinasiCol = new TableColumn<>("Destinasi");
        destinasiCol.setCellValueFactory(new PropertyValueFactory<>("destinasi"));
        destinasiCol.setMinWidth(150);

        TableColumn<TravelPlan, String> tanggalCol = new TableColumn<>("Tanggal");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        tanggalCol.setMinWidth(150);

        TableColumn<TravelPlan, String> transportasiCol = new TableColumn<>("Transportasi");
        transportasiCol.setCellValueFactory(new PropertyValueFactory<>("transportasi"));
        transportasiCol.setMinWidth(150);

        TableColumn<TravelPlan, Integer> jarakCol = new TableColumn<>("Jarak (km)");
        jarakCol.setCellValueFactory(new PropertyValueFactory<>("jarak"));
        jarakCol.setMinWidth(100);

        TableColumn<TravelPlan, Integer> biayaCol = new TableColumn<>("Biaya (IDR)");
        biayaCol.setCellValueFactory(new PropertyValueFactory<>("biayaRekomendasi"));
        biayaCol.setMinWidth(150);

        TableColumn<TravelPlan, String> gambarCol = new TableColumn<>("Gambar");
        gambarCol.setCellValueFactory(new PropertyValueFactory<>("gambarPath"));
        gambarCol.setMinWidth(200);

        table.getColumns().addAll(asalCol, destinasiCol, tanggalCol, transportasiCol, jarakCol, biayaCol, gambarCol);
        table.setItems(travelPlans);
    }

    private void tambahPerjalanan(Stage stage) {
        Dialog<TravelPlan> dialog = new Dialog<>();
        dialog.setTitle("Tambah Perjalanan");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        ComboBox<String> asalComboBox = new ComboBox<>(FXCollections.observableArrayList(jarakAntarKota.keySet()));
        ComboBox<String> destinasiComboBox = new ComboBox<>();
        DatePicker tanggalPicker = new DatePicker();
        ComboBox<String> transportasiComboBox = new ComboBox<>(FXCollections.observableArrayList(tarifTransportasi.keySet()));
        TextField jarakField = new TextField();
        jarakField.setDisable(true);
        TextField biayaField = new TextField();
        biayaField.setDisable(true);
        TextField gambarPathField = new TextField();
        gambarPathField.setDisable(true);
        Button pilihGambarButton = new Button("Pilih Gambar");

        asalComboBox.setOnAction(e -> {
            String asal = asalComboBox.getValue();
            if (asal != null) {
                destinasiComboBox.setItems(FXCollections.observableArrayList(jarakAntarKota.get(asal).keySet()));
            }
        });

        transportasiComboBox.setOnAction(e -> {
            String asal = asalComboBox.getValue();
            String destinasi = destinasiComboBox.getValue();
            String transportasi = transportasiComboBox.getValue();

            if (asal != null && destinasi != null && transportasi != null) {
                int jarak = jarakAntarKota.getOrDefault(asal, new HashMap<>()).getOrDefault(destinasi, 0);
                int biaya = jarak * tarifTransportasi.getOrDefault(transportasi, 0);
                jarakField.setText(String.valueOf(jarak));
                biayaField.setText(String.valueOf(biaya));
            }
        });

        pilihGambarButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                gambarPathField.setText(file.getAbsolutePath());
            }
        });

        grid.add(new Label("Asal:"), 0, 0);
        grid.add(asalComboBox, 1, 0);
        grid.add(new Label("Destinasi:"), 0, 1);
        grid.add(destinasiComboBox, 1, 1);
        grid.add(new Label("Tanggal:"), 0, 2);
        grid.add(tanggalPicker, 1, 2);
        grid.add(new Label("Transportasi:"), 0, 3);
        grid.add(transportasiComboBox, 1, 3);
        grid.add(new Label("Jarak (km):"), 0, 4);
        grid.add(jarakField, 1, 4);
        grid.add(new Label("Biaya (IDR):"), 0, 5);
        grid.add(biayaField, 1, 5);
        grid.add(new Label("Gambar:"), 0, 6);
        grid.add(gambarPathField, 1, 6);
        grid.add(pilihGambarButton, 2, 6);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                String asal = asalComboBox.getValue();
                String destinasi = destinasiComboBox.getValue();
                String transportasi = transportasiComboBox.getValue();
                int jarak = Integer.parseInt(jarakField.getText());
                int biayaRekomendasi = Integer.parseInt(biayaField.getText());
                String gambarPath = gambarPathField.getText();

                return new TravelPlan(asal, destinasi, tanggalPicker.getValue().toString(), transportasi, jarak, biayaRekomendasi, gambarPath);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(travelPlans::add);
    }

    private void tampilkanGaleri(Stage stage) {
        TravelPlan selectedPlan = table.getSelectionModel().getSelectedItem();
        if (selectedPlan == null || selectedPlan.getGambarPath() == null || selectedPlan.getGambarPath().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Tidak ada gambar yang dipilih!");
            alert.show();
            return;
        }

        Image image = new Image("file:" + selectedPlan.getGambarPath());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

        Stage galleryStage = new Stage();
        galleryStage.setTitle("Galeri Perjalanan");
        VBox root = new VBox(imageView);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 600, 400);
        galleryStage.setScene(scene);
        galleryStage.show();
    }

    private void hapusPerjalanan() {
        TravelPlan selectedPlan = table.getSelectionModel().getSelectedItem();
        if (selectedPlan != null) {
            travelPlans.remove(selectedPlan);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Pilih perjalanan untuk dihapus!");
            alert.show();
        }
    }

    private void toggleDarkMode(Scene scene) {
        darkMode = !darkMode;
        if (darkMode) {
            applyDarkMode(scene);
        } else {
            applyLightMode(scene);
        }
    }

    private void applyDarkMode(Scene scene) {
        scene.getRoot().setStyle("-fx-base: #2b2b2b; -fx-background: #3c3f41; -fx-text-base-color: white;");
    }

    private void applyLightMode(Scene scene) {
        scene.getRoot().setStyle("-fx-base: #f9f9f9; -fx-background: white; -fx-text-base-color: black;");
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public static class TravelPlan {
        private final String asal;
        private final String destinasi;
        private final String tanggal;
        private final String transportasi;
        private final int jarak;
        private final int biayaRekomendasi;
        private final String gambarPath;

        public TravelPlan(String asal, String destinasi, String tanggal, String transportasi, int jarak, int biayaRekomendasi, String gambarPath) {
            this.asal = asal;
            this.destinasi = destinasi;
            this.tanggal = tanggal;
            this.transportasi = transportasi;
            this.jarak = jarak;
            this.biayaRekomendasi = biayaRekomendasi;
            this.gambarPath = gambarPath;
        }

        public String getAsal() {
            return asal;
        }

        public String getDestinasi() {
            return destinasi;
        }

        public String getTanggal() {
            return tanggal;
        }

        public String getTransportasi() {
            return transportasi;
        }

        public int getJarak() {
            return jarak;
        }

        public int getBiayaRekomendasi() {
            return biayaRekomendasi;
        }

        public String getGambarPath() {
            return gambarPath;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
