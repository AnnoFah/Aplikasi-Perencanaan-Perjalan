Deskripsi Program
Aplikasi Perencanaan Perjalanan adalah aplikasi berbasis JavaFX yang membantu pengguna untuk merencanakan perjalanan antar kota di Indonesia. Aplikasi ini menyediakan informasi penting seperti jarak antar kota, biaya perjalanan berdasarkan jenis transportasi yang dipilih, dan tanggal perjalanan. Aplikasi juga memiliki fitur interaktif, termasuk mode gelap dan terang untuk kenyamanan visual pengguna.

Fitur Utama
Tambah Perjalanan :
Pengguna dapat menambahkan perjalanan baru dengan memilih:
Kota asal
Kota tujuan
Tanggal perjalanan
Jenis transportasi
Sistem secara otomatis menghitung jarak dan biaya rekomendasi perjalanan berdasarkan data jarak antar kota dan tarif transportasi.

Hapus Perjalanan :
Pengguna dapat menghapus data perjalanan yang tidak diperlukan.
Mode Gelap dan Terang:

Mendukung dua mode tampilan:
Mode Terang: Tampilan standar dengan warna latar putih.
Mode Gelap: Tampilan dengan warna gelap untuk mengurangi kelelahan mata.
Antarmuka Tabel yang Informatif:

Semua data perjalanan ditampilkan dalam tabel dengan kolom:
Kota Asal
Kota Tujuan
Tanggal Perjalanan
Jenis Transportasi
Jarak (dalam kilometer)
Biaya Rekomendasi (dalam IDR)

Perhitungan Otomatis:
Jarak antar kota dihitung berdasarkan data statis.
Biaya perjalanan dihitung berdasarkan jarak dan tarif transportasi per kilometer.
Validasi Input:

Aplikasi memastikan pengguna memilih semua input yang diperlukan sebelum menyimpan data.
Menampilkan pesan peringatan jika pengguna mencoba menghapus data tanpa memilih baris pada tabel.
Teknologi yang Digunakan
JavaFX:
Digunakan untuk membangun antarmuka grafis (GUI) interaktif.
Komponen utama:
TableView untuk menampilkan data perjalanan.
ComboBox, DatePicker, dan Dialog untuk input data perjalanan.
Data Jarak Statis:
Data jarak antar kota dan tarif transportasi disimpan dalam struktur data Map untuk akses cepat dan efisien.
Cara Menggunakan
1. Menjalankan Aplikasi
   Pastikan Anda memiliki Java 17 atau lebih baru serta JavaFX SDK versi 23.0.1.
   Jalankan aplikasi menggunakan IDE seperti IntelliJ IDEA atau Eclipse.
2. Tambah Perjalanan
   Klik tombol "Tambah Perjalanan".
   Pilih:
   Kota asal
   Kota tujuan
   Tanggal perjalanan
   Jenis transportasi
   Aplikasi secara otomatis akan menghitung jarak dan biaya perjalanan.
   Klik OK untuk menyimpan data.
3. Hapus Perjalanan
   Pilih baris perjalanan di tabel.
   Klik tombol "Hapus Perjalanan".
4. Ubah Mode Tampilan
   Klik tombol "Mode Gelap" untuk mengaktifkan atau menonaktifkan mode gelap.

Fitur Tambahan yang Direncanakan
1. Simpan dan Muat Data:
Menyimpan data perjalanan ke file lokal.
Memuat data perjalanan dari file lokal saat aplikasi dijalankan.
2. Peta Visual:
Menampilkan peta perjalanan antar kota menggunakan Google Maps API.
3. Dukungan Multi-Bahasa:
Menambahkan dukungan bahasa Inggris selain bahasa Indonesia.
Manfaat Program
1.  Efisiensi:
Menghemat waktu pengguna dalam merencanakan perjalanan dengan memberikan informasi jarak dan biaya secara otomatis.
2. Fleksibilitas:
Mendukung berbagai jenis transportasi dengan tarif yang dapat dimodifikasi.
3. Interaktivitas:
Antarmuka yang ramah pengguna dengan fitur mode tampilan dinamis.