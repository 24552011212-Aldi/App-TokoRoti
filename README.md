# App-TokoRoti - Tugas UAS smt 3 PBO

## kelompok 9
```
- Dwi
- Aldi
- Iqbal
- Raihan
```
METHOD SERVICE 
1. TransaksiService.java
	1. interface load data dari json
	2. interface save ke  dari json
	3. proses tranraksi penjualan 
	4. hitung kembalian 
	5. validasi pembayaran
	6. tampilkan riwayat transaksi 
	7. reset transaksi session 
2. ProduksiAService.java
	1. interface - Load data dari json
	2. interface - save data ke json 
	3. konversi bahan baku menjadi produk 
	4. validasi kapasitas bahan baku/stock bahan 
	5. hitung sisa bahan baku setelah produksi 
	6. Tampilkan log aktivitas produksi
	7. crud method dari interface IoperasiData
3. Ioperasidata.java
	1. Basic CURD
		1. void tambah(T data);
		2. void ubah(T data);
		3. void hapus(String id);
		4. T cari(String id);
		5. List<T> tampilSemua();
