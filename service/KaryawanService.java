package service;

import data.DataStorage;
import java.util.ArrayList;
import java.util.List;
import model.Karyawan;
import model.LevelKaryawan;

public class KaryawanService {
    
    // CREATE - Tambah karyawan baru
    public void tambahKaryawan(String kode, String nama, LevelKaryawan level, double gajiPokok) {
        // Cek karyawan
        for (Karyawan k : DataStorage.karyawanList) {
            if (k.getKode().equals(kode)) {
                System.out.println("Error: Karyawan dengan kode " + kode + " sudah ada!");
                return;
            }
        }
        
        Karyawan karyawan = new Karyawan(kode, nama, level, gajiPokok);
        DataStorage.karyawanList.add(karyawan);
        System.out.println("Karyawan bernama " + nama + " berhasil ditambahkan dengan gaji Rp" + gajiPokok);
    }
    
    // READ - Tampilkan semua karyawan
    public void tampilkanSemuaKaryawan() {
        if (DataStorage.karyawanList.isEmpty()) {
            System.out.println("Belum ada data karyawan!");
            return;
        }
        
        System.out.println("\n=== DAFTAR KARYAWAN ===");
        for (Karyawan k : DataStorage.karyawanList) {
            System.out.println(k.deskripsi() + " - Gaji: Rp" + k.getGajiPokok());
        }
        System.out.println();
    }
    
    // READ - Cari karyawan berdasarkan kode
    public Karyawan cariKaryawanByKode(String kode) {
        for (Karyawan k : DataStorage.karyawanList) {
            if (k.getKode().equals(kode)) {
                return k;
            }
        }
        return null;
    }
    
    // READ - Cari karyawan berdasarkan level
    public List<Karyawan> cariKaryawanByLevel(LevelKaryawan level) {
        List<Karyawan> hasilCari = new ArrayList<>();
        for (Karyawan k : DataStorage.karyawanList) {
            if (k.getLevel() == level) {
                hasilCari.add(k);
            }
        }
        return hasilCari;
    }
    
    // UPDATE - Ubah gaji karyawan
    public void ubahGajiKaryawan(String kode, double gajiBaru) {
        Karyawan karyawan = cariKaryawanByKode(kode);
        if (karyawan != null) {
            double gajiLama = karyawan.getGajiPokok();
            karyawan.setGajiPokok(gajiBaru);
            System.out.println("Gaji karyawan " + karyawan.getNama() + " berhasil diubah dari Rp" + 
                             gajiLama + " menjadi Rp" + gajiBaru);
        } else {
            System.out.println("Error: Karyawan dengan kode " + kode + " tidak ditemukan!");
        }
    }
    
    // DELETE - Hapus karyawan
    public void hapusKaryawan(String kode) {
        for (int i = 0; i < DataStorage.karyawanList.size(); i++) {
            if (DataStorage.karyawanList.get(i).getKode().equals(kode)) {
                String namaKaryawan = DataStorage.karyawanList.get(i).getNama();
                DataStorage.karyawanList.remove(i);
                System.out.println("Karyawan " + namaKaryawan + " berhasil dihapus");
                return;
            }
        }
        System.out.println("Error: Karyawan dengan kode " + kode + " tidak ditemukan!");
    }

    // Hitung gaji total seluruh karyawan
    public double hitungTotalGaji(){
        double totalGaji = 0;
        for (Karyawan k : DataStorage.karyawanList) {
            totalGaji += k.getLevel().getBonusRate() * k.getGajiPokok();
        }
        return totalGaji;
    }
    
    // Tampilkan jumlah karyawan
    public int jumlahKaryawan() {
        return DataStorage.karyawanList.size();
    }
}