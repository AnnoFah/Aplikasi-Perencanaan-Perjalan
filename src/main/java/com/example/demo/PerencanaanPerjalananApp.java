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

public class PerencanaanPerjalananApp extends Application {
    private TableView<TravelPlan> table;
    private ObservableList<TravelPlan> travelPlans;
    private ImageView displayImageView;

    private static final String[] KOTA_INDONESIA = {"Jakarta", "Surabaya", "Bandung", "Yogyakarta", "Medan", "Bali", "Makassar", "Semarang", "Palembang", "Balikpapan"};

    private static final int[][] JARAK_KOTA = {
            {0, 780, 150, 530, 1900, 1200, 1380, 455, 625, 1300},
            {780, 0, 725, 330, 2650, 1080, 1680, 350, 1450, 1230},
            {150, 725, 0, 410, 2000, 1350, 1500, 400, 700, 1380},
            {530, 330, 410, 0, 2300, 980, 1500, 120, 800, 1050},
            {1900, 2650, 2000, 2300, 0, 2800, 1500, 2450, 1200, 3000},
            {1200, 1080, 1350, 980, 2800, 0, 1600, 870, 1500, 950},
            {1380, 1680, 1500, 1500, 1500, 1600, 0, 1400, 1300, 1300},
            {455, 350, 400, 120, 2450, 870, 1400, 0, 850, 920},
            {625, 1450, 700, 800, 1200, 1500, 1300, 850, 0, 1500},
            {1300, 1230, 1380, 1050, 3000, 950, 1300, 920, 1500, 0}
    };

    private static final int TARIF_PER_KM = 1000;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikasi Perencanaan Perjalanan");

        travelPlans = FXCollections.observableArrayList();

        // Tabel
        table = new TableView<>();
        setupTable();

        // Tempat untuk menampilkan gambar di kanan
        displayImageView = new ImageView();
        displayImageView.setFitWidth(400); // Lebar gambar
        displayImageView.setFitHeight(300); // Tinggi gambar
        displayImageView.setPreserveRatio(true);

        VBox imageContainer = new VBox();
        imageContainer.setPadding(new Insets(10));
        imageContainer.setStyle("-fx-border-color: gray; -fx-border-width: 1px; -fx-background-color: #f9f9f9;");
        imageContainer.getChildren().add(displayImageView);
        imageContainer.setAlignment(javafx.geometry.Pos.CENTER);

        // Layout untuk tabel dan gambar
        HBox layout = new HBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(table, imageContainer);

        // ToolBar
        Button addButton = new Button("Tambah Perjalanan");
        addButton.setOnAction(e -> tambahPerjalanan(primaryStage));

        Button deleteButton = new Button("Hapus Perjalanan");
        deleteButton.setOnAction(e -> hapusPerjalanan());

        ToolBar toolBar = new ToolBar(addButton, deleteButton);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(toolBar);
        mainLayout.setCenter(layout);

        Scene scene = new Scene(mainLayout, 1000, 600); // Lebar aplikasi disesuaikan
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event Listener untuk menampilkan gambar saat baris dipilih
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                File file = new File(newSelection.getGambarPath());
                if (file.exists() && file.isFile()) {
                    Image image = new Image(file.toURI().toString());
                    displayImageView.setImage(image);
                } else {
                    displayImageView.setImage(null); // Kosongkan jika gambar tidak valid
                }
            }
        });
    }

    private void setupTable() {
        TableColumn<TravelPlan, String> asalCol = new TableColumn<>("Asal");
        asalCol.setCellValueFactory(new PropertyValueFactory<>("asal"));
        asalCol.setMinWidth(100);

        TableColumn<TravelPlan, String> destinasiCol = new TableColumn<>("Destinasi");
        destinasiCol.setCellValueFactory(new PropertyValueFactory<>("destinasi"));
        destinasiCol.setMinWidth(100);

        TableColumn<TravelPlan, String> tanggalCol = new TableColumn<>("Tanggal");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        tanggalCol.setMinWidth(120);

        TableColumn<TravelPlan, Integer> jarakCol = new TableColumn<>("Jarak (km)");
        jarakCol.setCellValueFactory(new PropertyValueFactory<>("jarak"));
        jarakCol.setMinWidth(100);

        TableColumn<TravelPlan, Integer> biayaCol = new TableColumn<>("Biaya Rekomendasi (IDR)");
        biayaCol.setCellValueFactory(new PropertyValueFactory<>("biayaRekomendasi"));
        biayaCol.setMinWidth(150);

        TableColumn<TravelPlan, String> gambarCol = new TableColumn<>("Path Gambar");
        gambarCol.setCellValueFactory(new PropertyValueFactory<>("gambarPath"));
        gambarCol.setMinWidth(200);

        table.getColumns().addAll(asalCol, destinasiCol, tanggalCol, jarakCol, biayaCol, gambarCol);
        table.setItems(travelPlans);
    }

    private void tambahPerjalanan(Stage stage) {
        Dialog<TravelPlan> dialog = new Dialog<>();
        dialog.setTitle("Tambah Perjalanan");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(10);
        grid.setHgap(10);

        ComboBox<String> asalComboBox = new ComboBox<>(FXCollections.observableArrayList(KOTA_INDONESIA));
        ComboBox<String> destinasiComboBox = new ComboBox<>(FXCollections.observableArrayList(KOTA_INDONESIA));
        DatePicker tanggalPicker = new DatePicker();
        TextField gambarField = new TextField();
        Button pilihGambarButton = new Button("Pilih Gambar");

        pilihGambarButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                gambarField.setText(file.getAbsolutePath());
            }
        });

        grid.add(new Label("Asal:"), 0, 0);
        grid.add(asalComboBox, 1, 0);
        grid.add(new Label("Destinasi:"), 0, 1);
        grid.add(destinasiComboBox, 1, 1);
        grid.add(new Label("Tanggal:"), 0, 2);
        grid.add(tanggalPicker, 1, 2);
        grid.add(new Label("Gambar:"), 0, 3);
        grid.add(gambarField, 1, 3);
        grid.add(pilihGambarButton, 2, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                String asal = asalComboBox.getValue();
                String destinasi = destinasiComboBox.getValue();
                int jarak = getJarakKota(asal, destinasi);
                int biayaRekomendasi = jarak * TARIF_PER_KM;

                return new TravelPlan(
                        asal,
                        destinasi,
                        tanggalPicker.getValue().toString(),
                        jarak,
                        biayaRekomendasi,
                        gambarField.getText()
                );
            }
            return null;
        });

        dialog.showAndWait().ifPresent(travelPlans::add);
    }

    private void hapusPerjalanan() {
        TravelPlan selectedPlan = table.getSelectionModel().getSelectedItem();
        if (selectedPlan != null) {
            travelPlans.remove(selectedPlan);
            displayImageView.setImage(null); // Kosongkan gambar di tempat khusus
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Pilih perjalanan untuk dihapus!");
            alert.show();
        }
    }

    private int getJarakKota(String asal, String destinasi) {
        int asalIndex = FXCollections.observableArrayList(KOTA_INDONESIA).indexOf(asal);
        int destinasiIndex = FXCollections.observableArrayList(KOTA_INDONESIA).indexOf(destinasi);

        if (asalIndex >= 0 && destinasiIndex >= 0) {
            return JARAK_KOTA[asalIndex][destinasiIndex];
        } else {
            return 0; // Default jika data tidak valid
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class TravelPlan {
        private final String asal;
        private final String destinasi;
        private final String tanggal;
        private final int jarak;
        private final int biayaRekomendasi;
        private final String gambarPath;

        public TravelPlan(String asal, String destinasi, String tanggal, int jarak, int biayaRekomendasi, String gambarPath) {
            this.asal = asal;
            this.destinasi = destinasi;
            this.tanggal = tanggal;
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
}
