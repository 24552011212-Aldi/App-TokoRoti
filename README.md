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
├── src/
   └── com/tokoroti/
       ├── Main.java                 (Titik masuk & inisialisasi data)
       ├── data/                     
       │   └── DataStorage.java      (ArrayList, Pusat List produk, karyawan, & uang) 
       ├── model/                    (Objek/class)
       │   ├── Produk.java
       │   ├── Karyawan.java
       │   └── Transaksi.java
       ├── view/                     (Tampilan GUISwing)
       │   ├── MainFrame.java        (Window Utama)
       │   ├── GudangPanel.java      (Tab/Halaman Gudang)
       │   ├── KaryawanPanel.java    (Tab/Halaman Karyawan)
       │   └── KeuanganPanel.java    (Tab/Halaman Keuangan)
       └── service/                  (Logika Pengolahan Data)
            ├── GudangService.java    (Logika tambah/kurang stok)
            ├── KaryawanService.java  (Logika input/pecat karyawan)
            └── KeuanganService.java  (Logika hitung laba/rugi)
```
