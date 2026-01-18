package service;

import data.DataStorage;
import java.util.ArrayList;
import java.util.List;
import model.JenisProduk;
import model.Produk;

public class GudangService {
    
    // CREATE - Tambah produk baru
    public void tambahProduk(String kode, String nama, JenisProduk jenis, double hargaSatuan) {
        // Cek produk
        for (Produk p : DataStorage.produkList) {
            if (p.getKode().equals(kode)) {
                System.out.println("Error: Produk dengan kode " + kode + " sudah ada!");
                return;
            }
        }
        
        Produk produk = new Produk(kode, nama, jenis, hargaSatuan);
        DataStorage.produkList.add(produk);
        System.out.println("Produk " + nama + " berhasil ditambahkan dengan harga Rp" + hargaSatuan);
    }
    
    // READ - Tampilkan semua produk
    public void tampilkanSemuaProduk() {
        if (DataStorage.produkList.isEmpty()) {
            System.out.println("Gudang masih kosong!");
            return;
        }
        
        System.out.println("\n=== DAFTAR PRODUK GUDANG ===");
        for (Produk p : DataStorage.produkList) {
            System.out.println(p.deskripsi());
        }
        System.out.println();
    }
    
    // READ - Cari produk berdasarkan kode
    public Produk cariProdukByKode(String kode) {
        for (Produk p : DataStorage.produkList) {
            if (p.getKode().equals(kode)) {
                return p;
            }
        }
        return null;
    }
    
    // READ - Cari produk berdasarkan jenis
    public List<Produk> cariProdukByJenis(JenisProduk jenis) {
        List<Produk> hasilCari = new ArrayList<>();
        for (Produk p : DataStorage.produkList) {
            if (p.getJenis() == jenis) {
                hasilCari.add(p);
            }
        }
        return hasilCari;
    }
    
    // UPDATE - Ubah harga produk
    public void ubahHargaProduk(String kode, double hargaBaru) {
        Produk produk = cariProdukByKode(kode);
        if (produk != null) {
            double hargaLama = produk.getHargaSatuan();
            produk.setHargaSatuan(hargaBaru);
            System.out.println("Harga produk " + produk.getNama() + " berhasil diubah dari Rp" + 
                             hargaLama + " menjadi Rp" + hargaBaru);
        } else {
            System.out.println("Error: Produk dengan kode " + kode + " tidak ditemukan!");
        }
    }
    
    // UPDATE - Ubah jenis produk
    public void ubahJenisProduk(String kode, JenisProduk jenisBaru) {
        Produk produk = cariProdukByKode(kode);
        if (produk != null) {
            JenisProduk jenisLama = produk.getJenis();
            produk.setJenis(jenisBaru);
            System.out.println("Jenis produk " + produk.getNama() + " berhasil diubah dari " + 
                             jenisLama + " menjadi " + jenisBaru);
        } else {
            System.out.println("Error: Produk dengan kode " + kode + " tidak ditemukan!");
        }
    }
    
    // DELETE - Hapus produk
    public void hapusProduk(String kode) {
        for (int i = 0; i < DataStorage.produkList.size(); i++) {
            if (DataStorage.produkList.get(i).getKode().equals(kode)) {
                String namaProduk = DataStorage.produkList.get(i).getNama();
                DataStorage.produkList.remove(i);
                System.out.println("Produk " + namaProduk + " berhasil dihapus dari gudang");
                return;
            }
        }
        System.out.println("Error: Produk dengan kode " + kode + " tidak ditemukan!");
    }
    
    // Hitung total nilai stok produk
    public double hitungTotalNilaiStok() {
        double totalNilai = 0;
        for (Produk p : DataStorage.produkList) {
            totalNilai += p.getHargaSatuan();
        }
        return totalNilai;
    }
    
    // Tampilkan jumlah produk di gudang
    public int jumlahProduk() {
        return DataStorage.produkList.size();
    }
}