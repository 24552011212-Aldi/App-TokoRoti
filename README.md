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
