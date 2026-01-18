package service;

import data.DataStorage;
import java.util.ArrayList;
import java.util.List;
import model.Karyawan;
import model.Produk;
import model.StatusTransaksi;
import model.Transaksi;

public class KeuanganService {
    
    // CREATE - Membuat transaksi penjualan baru
    public void prosesTransaksiBaru(String kode, Karyawan kasir, Produk produk, int jumlah) {
        // Cek transaksi
        for (Transaksi t : DataStorage.transaksiList) {
            if (t.getKode().equals(kode)) {
                System.out.println("Error: Transaksi dengan kode " + kode + " sudah ada!");
                return;
            }
        }
        
        // Instansiasi objek Transaksi
        Transaksi t = new Transaksi(kode, kasir, produk, jumlah);
        
        // Menambah ke riwayat transaksi
        DataStorage.transaksiList.add(t);
        
        // Mengubah status transaksi menggunakan method dari model Transaksi (OOP Behavior)
        t.selesaikan(); 
        
        System.out.println("Transaksi " + kode + " berhasil diproses: Rp" + t.hitungTotal());
    }
    
    // READ - Tampilkan semua transaksi
    public void tampilkanSemuaTransaksi() {
        if (DataStorage.transaksiList.isEmpty()) {
            System.out.println("Belum ada transaksi!");
            return;
        }
        
        System.out.println("\n=== DAFTAR TRANSAKSI ===");
        for (Transaksi t : DataStorage.transaksiList) {
            System.out.println(t.deskripsi() + " - Total: Rp" + t.hitungTotal());
        }
        System.out.println();
    }
    
    // READ - Cari transaksi berdasarkan kode
    public Transaksi cariTransaksiByKode(String kode) {
        for (Transaksi t : DataStorage.transaksiList) {
            if (t.getKode().equals(kode)) {
                return t;
            }
        }
        return null;
    }
    
    // READ - Cari transaksi berdasarkan status
    public List<Transaksi> cariTransaksiByStatus(StatusTransaksi status) {
        List<Transaksi> hasilCari = new ArrayList<>();
        for (Transaksi t : DataStorage.transaksiList) {
            if (t.getStatus() == status) {
                hasilCari.add(t);
            }
        }
        return hasilCari;
    }
    
    // UPDATE - Ubah status transaksi menjadi diproses
    public void prosesTransaksi(String kode) {
        Transaksi transaksi = cariTransaksiByKode(kode);
        if (transaksi != null) {
            transaksi.proses();
            System.out.println("Transaksi " + kode + " berhasil diproses");
        } else {
            System.out.println("Error: Transaksi dengan kode " + kode + " tidak ditemukan!");
        }
    }
    
    // UPDATE - Ubah status transaksi menjadi selesai
    public void selesaikanTransaksi(String kode) {
        Transaksi transaksi = cariTransaksiByKode(kode);
        if (transaksi != null) {
            transaksi.selesaikan();
            System.out.println("Transaksi " + kode + " berhasil diselesaikan");
        } else {
            System.out.println("Error: Transaksi dengan kode " + kode + " tidak ditemukan!");
        }
    }
    
    // UPDATE - Batalkan transaksi
    public void batalkanTransaksi(String kode) {
        Transaksi transaksi = cariTransaksiByKode(kode);
        if (transaksi != null) {
            transaksi.batalkan();
            System.out.println("Transaksi " + kode + " berhasil dibatalkan");
        } else {
            System.out.println("Error: Transaksi dengan kode " + kode + " tidak ditemukan!");
        }
    }
    
    // DELETE - Hapus transaksi
    public void hapusTransaksi(String kode) {
        for (int i = 0; i < DataStorage.transaksiList.size(); i++) {
            if (DataStorage.transaksiList.get(i).getKode().equals(kode)) {
                DataStorage.transaksiList.remove(i);
                System.out.println("Transaksi " + kode + " berhasil dihapus");
                return;
            }
        }
        System.out.println("Error: Transaksi dengan kode " + kode + " tidak ditemukan!");
    }

    // Menghitung total pendapatan dari semua transaksi yang SELESAI
    public double hitungTotalPendapatan() {
        double total = 0;
        for (Transaksi t : DataStorage.transaksiList) {
            if (t.getStatus() == StatusTransaksi.SELESAI) {
                total += t.hitungTotal();
            }
        }
        return total;
    }
    
    // Tampilkan jumlah transaksi
    public int jumlahTransaksi() {
        return DataStorage.transaksiList.size();
    }
}