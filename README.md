# App-TokoRoti - Tugas UAS smt 3 PBO

## kelompok 9
```
- Dwi
- Aldi
- Iqbal
- Raihan
```

## Struktur Folder
```
TokoRotiApp/
├── database/
│   └── data_transaksi.txt             (File Handling: Penyimpanan data)
├── src/
│   └── com/tokoroti/
│       ├── Main.java             (Inisialisasi & GUI Launcher)
│       ├── data/
│       │   ├── DataStorage.java  (Wrapper Class & List Management)
│       │   └── FileHandler.java  (Logika File Handling/I/O)
│       ├── model/                
│       │   ├── Item.java         (Abstract Class)
│       │   ├── BahanBaku.java    (Inheritance dari Item)
│       │   ├── Produk.java       (Inheritance dari Item)
│       │   ├── Transaksi.java    (Model data transaksi)
│       │   └── KategoriRoti.java (Enum: MANIS, ASIN, TAWAR)
│       ├── service/              
│       │   ├── IOperasiData.java (Interface: minimal 2 method)
│       │   ├── ProduksiService.java (Polymorphism)
│       │   └── TransaksiService.java (Logika Penjualan)
│       └── view/
│           ├── RoleSelect.java
│           ├── ProduksiPanel.java
│           └── TransaksiPanel.java
```
# App-TokoRoti - Tugas UAS smt 3 PBO

# 📋 DOKUMENTASI METHOD SERVICE

---

## **1. INTERFACE IOperasiData<T>**
**Package:** `service`  
**Tujuan:** Interface generic untuk operasi CRUD dan file persistence

```java
public interface IOperasiData<T> {
    // Basic CRUD operations
    void tambah(T data);
    void ubah(T data);
    void hapus(String id);
    T cari(String id);
    List<T> tampilSemua();
    
    // File operations
    void loadDataFromFile();
    void saveDataToFile();
}
```

### **1.1 Method: `void tambah(T data)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Parameter** | `T data` - Objek data yang akan ditambahkan (generic) |
| **Return** | `void` |
| **Fungsi** | Menambahkan data baru ke dalam koleksi/list |
| **Implementasi di TransaksiService** | Menambahkan string transaksi ke `riwayatTransaksi` |
| **Implementasi di ProduksiService** | Menambahkan string log ke `logAktivitas` |
| **Contoh Penggunaan** | |
| | `transaksiService.tambah("[2024-01-20] Pembelian Roti Tawar x2")` |
| | `produksiService.tambah("Konversi: 10kg bahan -> 50 unit produk")` |
| **Output/Efek** | Mencetak pesan sukses ke console, data tersimpan di list |

---

### **1.2 Method: `void ubah(T data)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Parameter** | `T data` - Objek data yang akan diperbarui |
| **Return** | `void` |
| **Fungsi** | Mengubah/update data yang sudah ada di dalam koleksi |
| **Implementasi di TransaksiService** | Mencari dan mengganti string transaksi tertentu |
| **Implementasi di ProduksiService** | Mencari dan mengganti string log tertentu |
| **Contoh Penggunaan** | |
| | `transaksiService.ubah("[2024-01-20] Pembelian Roti Manis x3")` |
| | `produksiService.ubah("Konversi: 15kg bahan -> 75 unit produk")` |
| **Catatan** | Pencarian berdasarkan kesamaan string/index dalam list |

---

### **1.3 Method: `void hapus(String id)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Parameter** | `String id` - ID/identitas unik data yang akan dihapus |
| **Return** | `void` |
| **Fungsi** | Menghapus data dari koleksi berdasarkan ID |
| **Implementasi di TransaksiService** | Menghapus transaksi yang berisi string `id` tertentu |
| **Implementasi di ProduksiService** | Menghapus log yang berisi string `id` tertentu |
| **Contoh Penggunaan** | |
| | `transaksiService.hapus("2024-01-20")` → Hapus semua transaksi tanggal 2024-01-20 |
| | `produksiService.hapus("Konversi")` → Hapus semua log yang berisi kata "Konversi" |
| **Catatan** | Menggunakan `removeIf` dengan filter string matching |

---

### **1.4 Method: `T cari(String id)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Parameter** | `String id` - ID/kriteria pencarian |
| **Return** | `T` - Data yang ditemukan, atau `null` jika tidak ada |
| **Fungsi** | Mencari dan mengembalikan data berdasarkan ID |
| **Implementasi di TransaksiService** | Mencari string transaksi yang mengandung `id` |
| **Implementasi di ProduksiService** | Mencari string log yang mengandung `id` |
| **Contoh Penggunaan** | |
| | `String hasil = transaksiService.cari("Kasir: Budi")` |
| | → Mengembalikan: `"[2024-01-20 15:30:00] Kasir: Budi | Barang: Roti Tawar..."` |
| | `String hasil = produksiService.cari("Validasi")` |
| | → Mengembalikan: `"Validasi Kapasitas: Valid (Tersedia: 50kg, Dibutuhkan: 30kg)"` |
| **Return Value** | String pertama yang cocok, atau `null` |

---

### **1.5 Method: `List<T> tampilSemua()`**
| Aspek | Deskripsi |
|-------|-----------|
| **Parameter** | Tidak ada |
| **Return** | `List<T>` - List berisi semua data |
| **Fungsi** | Mengembalikan seluruh koleksi data |
| **Implementasi di TransaksiService** | Mengembalikan copy dari `riwayatTransaksi` |
| **Implementasi di ProduksiService** | Mengembalikan copy dari `logAktivitas` |
| **Contoh Penggunaan** | |
| | `List<String> semuaTransaksi = transaksiService.tampilSemua()` |
| | `for(String transaksi : semuaTransaksi) { System.out.println(transaksi); }` |
| **Catatan** | Mengembalikan `new ArrayList<>()` untuk menghindari modifikasi langsung |

---

### **1.6 Method: `void loadDataFromFile()`**
| Aspek | Deskripsi |
|-------|-----------|
| **Parameter** | Tidak ada |
| **Return** | `void` |
| **Fungsi** | Memuat data dari file JSON dan memasukkannya ke memory |
| **Implementasi di TransaksiService** | |
| | - File: `data/transaksi_log.json` |
| | - Membaca: `namaKasir`, `totalTransaksi`, `riwayatTransaksi` array |
| | - Menggunakan BufferedReader untuk pembacaan |
| | - Manual JSON parsing (regex-based) |
| **Implementasi di ProduksiService** | |
| | - File: `data/produksi_log.json` |
| | - Membaca: `namaProduksi`, `rateKonversi`, `logAktivitas` array |
| | - Menggunakan BufferedReader untuk pembacaan |
| | - Manual JSON parsing (regex-based) |
| **Contoh Penggunaan** | |
| | `transaksiService.loadDataFromFile()` |
| | `produksiService.loadDataFromFile()` |
| **Output/Efek** | |
| | - Data tersimpan di atribut instance |
| | - Mencetak pesan: `"[Service] Data berhasil dimuat dari JSON file"` |
| | - Jika file tidak ada, otomatis membuat file baru |
| **Error Handling** | Try-catch IOException dan parsing exception |

---

### **1.7 Method: `void saveDataToFile()`**
| Aspek | Deskripsi |
|-------|-----------|
| **Parameter** | Tidak ada |
| **Return** | `void` |
| **Fungsi** | Menyimpan semua data dari memory ke file JSON |
| **Implementasi di TransaksiService** | |
| | - File: `data/transaksi_log.json` |
| | - Menyimpan: `namaKasir`, `totalTransaksi`, `riwayatTransaksi` |
| | - Format: JSON dengan indentasi 2 spasi |
| | - Menggunakan FileWriter |
| **Implementasi di ProduksiService** | |
| | - File: `data/produksi_log.json` |
| | - Menyimpan: `namaProduksi`, `rateKonversi`, `logAktivitas` |
| | - Format: JSON dengan indentasi 2 spasi |
| | - Menggunakan FileWriter |
| **Contoh Penggunaan** | |
| | `transaksiService.saveDataToFile()` |
| | `produksiService.saveDataToFile()` |
| **Output/Efek** | |
| | - File JSON dibuat/diperbarui |
| | - Mencetak pesan: `"[Service] Data berhasil disimpan ke JSON file"` |
| | - Direktori `data/` dibuat otomatis jika tidak ada |

---

---

## **2. CLASS TransaksiService**
**Package:** `service`  
**Implements:** `IOperasiData<String>`  
**Tujuan:** Mengelola logika pemrosesan transaksi penjualan

### **Constructor & Atribut**

```java
private String namaKasir;           // Nama kasir default (dapat diganti saat input transaksi)
private double totalTransaksi;      // Total nilai semua transaksi yang tercatat
private final List<String> riwayatTransaksi; // Riwayat semua transaksi

public TransaksiService(String namaKasir)
```

**Catatan:** Nama kasir dapat berbeda di setiap transaksi karena diinput melalui menu transaksi.

---

### **2.1 Method: `double[] prosesPenjualan(...)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Signature** | `public double[] prosesPenjualan(String namaBarang, double hargaSatuan, int jumlahBarang, double uangBayar)` |
| **Parameter** | |
| | `namaBarang` (String) - Nama barang yang dibeli (misal: "Roti Tawar") |
| | `hargaSatuan` (double) - Harga per unit barang (Rp) |
| | `jumlahBarang` (int) - Jumlah unit yang dibeli |
| | `uangBayar` (double) - Uang tunai yang diberikan pembeli (Rp) |
| **Return** | `double[]` - Array dengan 2 elemen: `[totalHarga, uangKembali]` |
| | Jika pembayaran gagal: `[totalHarga, -1]` (uang kembali = -1) |
| **Fungsi** | Memproses transaksi penjualan lengkap dengan validasi pembayaran |
| **Logika Kerja** | 1. Hitung total: `totalHarga = hargaSatuan × jumlahBarang` |
| | 2. Hitung kembalian: `uangKembali = uangBayar - totalHarga` |
| | 3. Validasi: Jika pembayaran < totalHarga → gagal & return -1 |
| | 4. Update: `totalTransaksi += totalHarga` |
| | 5. Catat ke riwayat dengan timestamp |
| | 6. Print detail transaksi ke console |
| **Contoh Penggunaan** | |
| | `double[] hasil = transaksiService.prosesPenjualan("Roti Tawar", 5000, 2, 15000)` |
| | Hasil: `[10000, 5000]` (Total: Rp10.000, Kembalian: Rp5.000) |
| | |
| | `double[] hasil = transaksiService.prosesPenjualan("Roti Manis", 6000, 3, 15000)` |
| | Hasil: `[18000, -1]` (Total: Rp18.000, Pembayaran gagal!) |
| **Output Console** | |
| | `[TRANSAKSI] [2024-01-20 15:30:00] Kasir: Budi | Barang: Roti Tawar | Qty: 2 | Harga/satuan: Rp5000.00 | Total: Rp10000.00 | Bayar: Rp15000.00 | Kembalian: Rp5000.00` |
| **Catatan Penting** | |
| | - Return array, bukan void → nilai bisa diakses langsung |
| | - Nilai -1 di index [1] = indikator pembayaran gagal |
| | - Timestamp otomatis menggunakan `LocalDateTime.now()` |
| | - Riwayat disimpan sebelum file save |

---

### **2.2 Method: `double hitungKembalian(...)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Signature** | `public double hitungKembalian(double totalBelanja, double uangBayar)` |
| **Parameter** | |
| | `totalBelanja` (double) - Total nilai belanja (Rp) |
| | `uangBayar` (double) - Uang yang diberikan (Rp) |
| **Return** | `double` - Jumlah uang kembalian |
| | Positif = kembalian (normal) |
| | Negatif = pembayaran kurang |
| **Fungsi** | Menghitung jumlah uang kembalian yang harus diberikan |
| **Logika** | `kembalian = uangBayar - totalBelanja` |
| **Contoh Penggunaan** | |
| | `double kembalian = transaksiService.hitungKembalian(10000, 15000)` |
| | Hasil: `5000` (kembalian Rp5.000) |
| | |
| | `double kembalian = transaksiService.hitungKembalian(10000, 8000)` |
| | Hasil: `-2000` (kurang Rp2.000) |
| **Catatan** | Disimpan ke riwayat transaksi untuk audit |

---

### **2.3 Method: `boolean validasiPembayaran(...)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Signature** | `public boolean validasiPembayaran(double totalBelanja, double uangBayar)` |
| **Parameter** | |
| | `totalBelanja` (double) - Total nilai belanja (Rp) |
| | `uangBayar` (double) - Uang yang diberikan (Rp) |
| **Return** | `boolean` - `true` jika pembayaran cukup, `false` jika kurang |
| **Fungsi** | Validasi apakah uang yang diberikan sudah cukup |
| **Kondisi** | |
| | - TRUE: `uangBayar >= totalBelanja` ✓ Pembayaran valid |
| | - FALSE: `uangBayar < totalBelanja` ✗ Pembayaran kurang |
| **Contoh Penggunaan** | |
| | `if(transaksiService.validasiPembayaran(10000, 15000)) {` |
| | `    // Proses transaksi` |
| | `}` |
| **Output Console** | |
| | Valid: `[Validasi Pembayaran] Valid - Pembayaran Cukup (Total: Rp10000.00 | Bayar: Rp15000.00)` |
| | Tidak Valid: `[Validasi Pembayaran] Tidak Valid - Pembayaran Kurang (Total: Rp10000.00 | Bayar: Rp8000.00)` |
| **Catatan** | Disimpan ke riwayat sebagai audit trail |

---

### **2.4 Method: `void tampilkanRiwayatTransaksi()`**
| Aspek | Deskripsi |
|-------|-----------|
| **Signature** | `public void tampilkanRiwayatTransaksi()` |
| **Parameter** | Tidak ada |
| **Return** | `void` |
| **Fungsi** | Menampilkan semua riwayat transaksi dalam format tabel |
| **Output Format** | |
| | ```
| | === RIWAYAT TRANSAKSI - KASIR: Kasir ===
| | 1. [2024-01-20 15:30:00] Kasir: Budi | Barang: Roti Tawar | Qty: 2 | ...
| | 2. [2024-01-20 16:00:00] Kasir: Siti | Barang: Roti Manis | Qty: 1 | ...
| | 
| | Total Transaksi: Rp 25000.00
| | ``` |
| **Kondisi Khusus** | Jika tidak ada transaksi: `"Belum ada transaksi"` |
| **Contoh Penggunaan** | `transaksiService.tampilkanRiwayatTransaksi()` |
| **Kegunaan** | Laporan penjualan harian (nama kasir tercatat per transaksi) |
| **Catatan** | Nama kasir dalam setiap transaksi bisa berbeda (diinput saat transaksi) |

---

### **2.5 Method: `void loadDataFromFile()` (Override)**
| Aspek | Deskripsi |
|-------|-----------|
| **Override dari** | `IOperasiData<String>` |
| **File** | `data/transaksi_log.json` |
| **Membaca** | |
| | - `namaKasir` (String) |
| | - `totalTransaksi` (double) |
| | - `riwayatTransaksi[]` (array String) |
| **Parsing** | Manual regex-based JSON parsing |
| **Error Handling** | IOException & parsing exception |

---

### **2.6 Method: `void saveDataToFile()` (Override)**
| Aspek | Deskripsi |
|-------|-----------|
| **Override dari** | `IOperasiData<String>` |
| **File** | `data/transaksi_log.json` |
| **Format Simpan** | |
| | ```json
| | {
| |   "namaKasir": "Budi",
| |   "totalTransaksi": 25000.00,
| |   "riwayatTransaksi": [
| |     "[2024-01-20 15:30:00] Kasir: Budi | Barang: Roti Tawar | Qty: 2 | ...",
| |     "[2024-01-20 16:00:00] Kasir: Budi | Barang: Roti Manis | Qty: 1 | ..."
| |   ]
| | }
| | ``` |

---

---

## **3. CLASS ProduksiService**
**Package:** `service`  
**Implements:** `IOperasiData<String>`  
**Tujuan:** Mengelola logika produksi bahan baku menjadi produk jadi

### **Constructor & Atribut**

```java
private String namaProduksi;        // Nama batch/proses produksi
private double rateKonversi;        // Rasio konversi bahan ke produk (kg → unit)
private final List<String> logAktivitas; // Log setiap aktivitas produksi

public ProduksiService(String namaProduksi, double rateKonversi)
```

---

### **3.1 Method: `double konversiBahanBakuKeProduk(double)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Signature** | `public double konversiBahanBakuKeProduk(double jumlahBahanBaku)` |
| **Parameter** | `jumlahBahanBaku` (double) - Jumlah bahan baku dalam kg |
| **Return** | `double` - Jumlah produk jadi yang dihasilkan |
| **Fungsi** | Mengkonversi jumlah bahan baku menjadi produk jadi |
| **Rumus** | `hasilProduksi = jumlahBahanBaku × rateKonversi` |
| **Contoh** | |
| | Rate konversi: 5 (1kg bahan → 5 unit produk) |
| | Input: 10 kg bahan baku |
| | Output: `10 × 5 = 50 unit produk` |
| | |
| | Rate konversi: 2.5 (1kg bahan → 2.5 unit produk) |
| | Input: 20 kg bahan baku |
| | Output: `20 × 2.5 = 50 unit produk` |
| **Output Console** | |
| | `[Produksi] Konversi: 10.00 kg bahan baku -> 50.00 unit produk (rate: 5.00)` |
| **Catatan** | |
| | - Hasil disimpan ke `logAktivitas` |
| | - Digunakan untuk perhitungan kapasitas produksi |

---

### **3.2 Method: `boolean validasiKapasitasProduksi(...)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Signature** | `public boolean validasiKapasitasProduksi(double jumlahBahanBakuTersedia, double jumlahBahanBakuDibutuhkan)` |
| **Parameter** | |
| | `jumlahBahanBakuTersedia` (double) - Stok bahan baku yang tersedia (kg) |
| | `jumlahBahanBakuDibutuhkan` (double) - Bahan yang dibutuhkan untuk produksi (kg) |
| **Return** | `boolean` - `true` jika stok cukup, `false` jika kurang |
| **Fungsi** | Validasi apakah bahan baku yang tersedia cukup untuk produksi |
| **Kondisi** | |
| | - TRUE: `tersedia >= dibutuhkan` ✓ Stok cukup, lanjut produksi |
| | - FALSE: `tersedia < dibutuhkan` ✗ Stok kurang, tidak bisa produksi |
| **Contoh Penggunaan** | |
| | `if(produksiService.validasiKapasitasProduksi(100, 80)) {` |
| | `    // Stok cukup, lanjutkan produksi` |
| | `    double produk = produksiService.konversiBahanBakuKeProduk(80)` |
| | `}` |
| **Output Console** | |
| | Valid: `[Validasi] Validasi Kapasitas: Valid (Tersedia: 100.00 kg, Dibutuhkan: 80.00 kg)` |
| | Tidak Valid: `[Validasi] Validasi Kapasitas: Tidak Valid (Tersedia: 50.00 kg, Dibutuhkan: 80.00 kg)` |
| **Kegunaan** | Mencegah produksi yang melebihi stok tersedia |

---

### **3.3 Method: `double hitungSisaBahanBaku(...)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Signature** | `public double hitungSisaBahanBaku(double jumlahBahanBakuAwal, double jumlahBahanBakuDigunakan)` |
| **Parameter** | |
| | `jumlahBahanBakuAwal` (double) - Stok awal bahan baku (kg) |
| | `jumlahBahanBakuDigunakan` (double) - Bahan yang digunakan dalam produksi (kg) |
| **Return** | `double` - Sisa bahan baku setelah produksi |
| **Fungsi** | Menghitung sisa bahan baku setelah proses produksi |
| **Rumus** | `sisa = jumlahBahanBakuAwal - jumlahBahanBakuDigunakan` |
| **Contoh Penggunaan** | |
| | Stok awal: 100 kg |
| | Produksi menggunakan: 80 kg |
| | Sisa: `100 - 80 = 20 kg` |
| | |
| | `double sisa = produksiService.hitungSisaBahanBaku(100, 80)` |
| | Hasil: `20` |
| **Output Console** | |
| | `[Perhitungan Sisa] Perhitungan Sisa: 100.00 kg (awal) - 80.00 kg (digunakan) = 20.00 kg (sisa)` |
| **Kegunaan** | Update stok bahan baku setelah produksi |

---

### **3.4 Method: `void tampilkanLogAktivitas()`**
| Aspek | Deskripsi |
|-------|-----------|
| **Signature** | `public void tampilkanLogAktivitas()` |
| **Parameter** | Tidak ada |
| **Return** | `void` |
| **Fungsi** | Menampilkan semua log aktivitas produksi |
| **Output Format** | |
| | ```
| | === LOG AKTIVITAS PRODUKSI: Batch-001 ===
| | 1. Konversi: 10.00 kg bahan baku -> 50.00 unit produk (rate: 5.00)
| | 2. Validasi Kapasitas: Valid (Tersedia: 100.00 kg, Dibutuhkan: 80.00 kg)
| | 3. Perhitungan Sisa: 100.00 kg (awal) - 80.00 kg (digunakan) = 20.00 kg (sisa)
| | ``` |
| **Kondisi Khusus** | Jika tidak ada aktivitas: `"Belum ada aktivitas produksi"` |
| **Contoh Penggunaan** | `produksiService.tampilkanLogAktivitas()` |
| **Kegunaan** | Laporan aktivitas produksi untuk quality control |

---

### **3.5 Method: `void loadDataFromFile()` (Override)**
| Aspek | Deskripsi |
|-------|-----------|
| **Override dari** | `IOperasiData<String>` |
| **File** | `data/produksi_log.json` |
| **Membaca** | |
| | - `namaProduksi` (String) |
| | - `rateKonversi` (double) |
| | - `logAktivitas[]` (array String) |
| **Parsing** | Manual regex-based JSON parsing |
| **Error Handling** | IOException & parsing exception |

---

### **3.6 Method: `void saveDataToFile()` (Override)**
| Aspek | Deskripsi |
|-------|-----------|
| **Override dari** | `IOperasiData<String>` |
| **File** | `data/produksi_log.json` |
| **Format Simpan** | |
| | ```json
| | {
| |   "namaProduksi": "Batch-001",
| |   "rateKonversi": 5.0,
| |   "logAktivitas": [
| |     "Konversi: 10.00 kg bahan baku -> 50.00 unit produk (rate: 5.00)",
| |     "Validasi Kapasitas: Valid (Tersedia: 100.00 kg, Dibutuhkan: 80.00 kg)"
| |   ]
| | }
| | ``` |

---

### **3.7 CRUD Methods dari IOperasiData (Override)**

#### **3.7.1 `void tambah(String data)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Fungsi** | Menambahkan string log ke dalam `logAktivitas` |
| **Implementasi** | `logAktivitas.add(data)` |
| **Output** | `[ProduksiService] Log aktivitas berhasil ditambahkan` |

#### **3.7.2 `void ubah(String data)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Fungsi** | Mengubah string log yang sudah ada |
| **Implementasi** | Cari index, lalu set dengan data baru |
| **Output** | `[ProduksiService] Log aktivitas berhasil diubah` |

#### **3.7.3 `void hapus(String id)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Fungsi** | Menghapus log yang mengandung string `id` |
| **Implementasi** | `removeIf(log -> log.contains(id))` |
| **Output** | `[ProduksiService] Log aktivitas berhasil dihapus` |

#### **3.7.4 `String cari(String id)`**
| Aspek | Deskripsi |
|-------|-----------|
| **Fungsi** | Mencari log yang mengandung string `id` |
| **Return** | String pertama yang cocok atau `null` |
| **Implementasi** | Stream dengan filter dan `findFirst()` |

#### **3.7.5 `List<String> tampilSemua()`**
| Aspek | Deskripsi |
|-------|-----------|
| **Fungsi** | Mengembalikan semua log aktivitas |
| **Return** | `new ArrayList<>(logAktivitas)` |

---

---

## **📊 PERBANDINGAN TRANSAKSI vs PRODUKSI SERVICE**

| Aspek | TransaksiService | ProduksiService |
|-------|------------------|-----------------|
| **Fokus** | Penjualan & Pembayaran | Produksi & Konversi Bahan |
| **File** | `transaksi_log.json` | `produksi_log.json` |
| **Main List** | `riwayatTransaksi` | `logAktivitas` |
| **Key Calculation** | Harga × Qty, Kembalian | Bahan × Rate, Sisa Bahan |
| **Validation** | Pembayaran cukup? | Stok bahan cukup? |
| **Domain** | Penjualan/Transaksi | QC/Produksi |
| **Input Kasir** | Diinput per transaksi di menu | - |

---

## **🔧 CONTOH IMPLEMENTASI LENGKAP**

### **Scenario: Proses Penjualan & Produksi**

```java
// 1. Inisialisasi
TransaksiService transaksiService = new TransaksiService("Kasir");
ProduksiService produksiService = new ProduksiService("Batch-001", 5.0);

// 2. Load data sebelumnya
transaksiService.loadDataFromFile();
produksiService.loadDataFromFile();

// 3. Proses Transaksi Penjualan (nama kasir diinput di menu transaksi)
double[] hasil = transaksiService.prosesPenjualan("Budi", "Roti Tawar", 5000, 2, 15000);
if(hasil[1] != -1) {
    System.out.println("Total: Rp" + hasil[0] + ", Kembalian: Rp" + hasil[1]);
} else {
    System.out.println("Pembayaran kurang!");
}

// 4. Proses Produksi
if(produksiService.validasiKapasitasProduksi(100, 80)) {
    double produk = produksiService.konversiBahanBakuKeProduk(80);
    double sisa = produksiService.hitungSisaBahanBaku(100, 80);
    System.out.println("Produk: " + produk + " unit, Sisa: " + sisa + " kg");
}

// 5. Tampilkan Riwayat
transaksiService.tampilkanRiwayatTransaksi();
produksiService.tampilkanLogAktivitas();

// 6. Simpan Data
transaksiService.saveDataToFile();
produksiService.saveDataToFile();
```

---
