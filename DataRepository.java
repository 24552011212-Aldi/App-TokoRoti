package data;

import model.Produk;
import model.BahanBaku;
import java.util.ArrayList;
import java.util.List;

/**
 * Class DataRepository
 * Penampung utama ArrayList untuk objek Produk dan BahanBaku
 * Menggunakan Wrapper Class dalam koleksinya (Integer untuk stok, Double untuk harga)
 * Implementasi Pattern: Repository Pattern
 */
public class DataRepository {
    
    // Wrapper Class usage: ArrayList dengan Wrapper Classes
    private final List<Produk> daftarProduk;
    private final List<BahanBaku> daftarBahanBaku;
    private final FilePersistence filePersistence;
    
    // Constructor
    public DataRepository() {
        this.daftarProduk = new ArrayList<>();
        this.daftarBahanBaku = new ArrayList<>();
        this.filePersistence = new FilePersistence();
    }
    
    // ============== FILE OPERATIONS ==============
    public void loadDataFromFile() {
        filePersistence.loadProdukFromFile(daftarProduk);
        filePersistence.loadBahanBakuFromFile(daftarBahanBaku);
        System.out.println("[DataRepository] Data berhasil dimuat dari file");
    }
    
    public void saveDataToFile() {
        filePersistence.saveProdukToFile(daftarProduk);
        filePersistence.saveBahanBakuToFile(daftarBahanBaku);
        System.out.println("[DataRepository] Data berhasil disimpan ke file");
    }
    
    // ============== PRODUK OPERATIONS ==============
    public void tambahProduk(Produk produk) {
        if (produk != null && !daftarProduk.contains(produk)) {
            daftarProduk.add(produk);
            System.out.println("[DataRepository] Produk '" + produk.getNama() + "' berhasil ditambahkan");
        } else {
            System.out.println("[DataRepository ERROR] Produk gagal ditambahkan atau sudah ada");
        }
    }
    
    public void ubahProduk(Produk produk) {
        for (int i = 0; i < daftarProduk.size(); i++) {
            if (daftarProduk.get(i).getKode().equals(produk.getKode())) {
                daftarProduk.set(i, produk);
                System.out.println("[DataRepository] Produk '" + produk.getNama() + "' berhasil diubah");
                return;
            }
        }
        System.out.println("[DataRepository ERROR] Produk tidak ditemukan");
    }
    
    public void hapusProduk(String kodeProduk) {
        boolean removed = daftarProduk.removeIf(produk -> produk.getKode().equals(kodeProduk));
        if (removed) {
            System.out.println("[DataRepository] Produk dengan kode '" + kodeProduk + "' berhasil dihapus");
        } else {
            System.out.println("[DataRepository ERROR] Produk tidak ditemukan");
        }
    }
    
    public Produk cariProduk(String kodeProduk) {
        return daftarProduk.stream()
                .filter(produk -> produk.getKode().equals(kodeProduk))
                .findFirst()
                .orElse(null);
    }
    
    public List<Produk> tampilSemuaProduk() {
        return new ArrayList<>(daftarProduk);
    }
    
    // ============== BAHAN BAKU OPERATIONS ==============
    public void tambahBahanBaku(BahanBaku bahanBaku) {
        if (bahanBaku != null && !daftarBahanBaku.contains(bahanBaku)) {
            daftarBahanBaku.add(bahanBaku);
            System.out.println("[DataRepository] Bahan Baku '" + bahanBaku.getNama() + "' berhasil ditambahkan");
        } else {
            System.out.println("[DataRepository ERROR] Bahan Baku gagal ditambahkan atau sudah ada");
        }
    }
    
    public void ubahBahanBaku(BahanBaku bahanBaku) {
        for (int i = 0; i < daftarBahanBaku.size(); i++) {
            if (daftarBahanBaku.get(i).getKode().equals(bahanBaku.getKode())) {
                daftarBahanBaku.set(i, bahanBaku);
                System.out.println("[DataRepository] Bahan Baku '" + bahanBaku.getNama() + "' berhasil diubah");
                return;
            }
        }
        System.out.println("[DataRepository ERROR] Bahan Baku tidak ditemukan");
    }
    
    public void hapusBahanBaku(String kodeBahan) {
        boolean removed = daftarBahanBaku.removeIf(bahan -> bahan.getKode().equals(kodeBahan));
        if (removed) {
            System.out.println("[DataRepository] Bahan Baku dengan kode '" + kodeBahan + "' berhasil dihapus");
        } else {
            System.out.println("[DataRepository ERROR] Bahan Baku tidak ditemukan");
        }
    }
    
    public BahanBaku cariBahanBaku(String kodeBahan) {
        return daftarBahanBaku.stream()
                .filter(bahan -> bahan.getKode().equals(kodeBahan))
                .findFirst()
                .orElse(null);
    }
    
    public List<BahanBaku> tampilSemuaBahanBaku() {
        return new ArrayList<>(daftarBahanBaku);
    }
    
    // ============== HELPER METHODS ==============
    public Integer getTotalProduk() {
        return daftarProduk.size();
    }
    
    public Integer getTotalBahanBaku() {
        return daftarBahanBaku.size();
    }
    
    public void tampilkanStatistik() {
        System.out.println("\n=== STATISTIK DATA REPOSITORY ===");
        System.out.println("Total Produk: " + getTotalProduk());
        System.out.println("Total Bahan Baku: " + getTotalBahanBaku());
    }
}
