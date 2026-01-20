package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Relasi: Composition dengan Produk
 * Memenuhi requirement: Class Relasi (Composition)
 * Model data transaksi
 */
public class Transaksi implements Storable {
    // Minimal 3 atribut private (menggunakan Wrapper Class)
    private String idTransaksi;
    private LocalDateTime tanggalTransaksi;
    private String namaPelanggan;
    private List<ItemTransaksi> daftarItem; // Composition
    private Double totalHarga;
    private Integer jumlahItem;
    
    // Inner class untuk item dalam transaksi (Composition)
    public static class ItemTransaksi {
        private Produk produk;
        private Integer jumlah;
        private Double subtotal;
        
        public ItemTransaksi(Produk produk, Integer jumlah) {
            this.produk = produk;
            this.jumlah = jumlah;
            this.subtotal = produk.getHarga() * jumlah;
        }
        
        public Produk getProduk() {
            return produk;
        }
        
        public Integer getJumlah() {
            return jumlah;
        }
        
        public Double getSubtotal() {
            return subtotal;
        }
        
        public void setJumlah(Integer jumlah) {
            this.jumlah = jumlah;
            this.subtotal = produk.getHarga() * jumlah;
        }
    }
    
    // Constructor berparameter
    public Transaksi(String idTransaksi, String namaPelanggan) {
        this.idTransaksi = idTransaksi;
        this.tanggalTransaksi = LocalDateTime.now();
        this.namaPelanggan = namaPelanggan;
        this.daftarItem = new ArrayList<>();
        this.totalHarga = 0.0;
        this.jumlahItem = 0;
    }
    
    // Default constructor
    public Transaksi() {
        this.idTransaksi = "";
        this.tanggalTransaksi = LocalDateTime.now();
        this.namaPelanggan = "";
        this.daftarItem = new ArrayList<>();
        this.totalHarga = 0.0;
        this.jumlahItem = 0;
    }
    
    // Getter dan Setter (Enkapsulasi)
    public String getIdTransaksi() {
        return idTransaksi;
    }
    
    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }
    
    public LocalDateTime getTanggalTransaksi() {
        return tanggalTransaksi;
    }
    
    public void setTanggalTransaksi(LocalDateTime tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }
    
    public String getNamaPelanggan() {
        return namaPelanggan;
    }
    
    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }
    
    public List<ItemTransaksi> getDaftarItem() {
        return daftarItem;
    }
    
    public Double getTotalHarga() {
        return totalHarga;
    }
    
    public Integer getJumlahItem() {
        return jumlahItem;
    }
    
    // Method tambahan 1: Tambah item ke transaksi
    public void tambahItem(Produk produk, Integer jumlah) {
        if (produk.kurangiStokProduk(jumlah)) {
            ItemTransaksi item = new ItemTransaksi(produk, jumlah);
            daftarItem.add(item);
            hitungTotal();
        }
    }
    
    // Method tambahan 2: Hitung total harga
    public void hitungTotal() {
        totalHarga = 0.0;
        jumlahItem = 0;
        for (ItemTransaksi item : daftarItem) {
            totalHarga += item.getSubtotal();
            jumlahItem += item.getJumlah();
        }
    }
    
    // Method tambahan 3: Hapus item dari transaksi
    public Boolean hapusItem(Integer index) {
        if (index >= 0 && index < daftarItem.size()) {
            ItemTransaksi item = daftarItem.remove(index);
            // Kembalikan stok
            item.getProduk().tambahStokProduk(item.getJumlah());
            hitungTotal();
            return true;
        }
        return false;
    }
    
    // Method tambahan 4: Apply diskon
    public void applyDiskon(Integer persenDiskon) {
        if (persenDiskon > 0 && persenDiskon <= 100) {
            Double diskon = totalHarga * persenDiskon / 100.0;
            totalHarga -= diskon;
        }
    }
    
    // Method tambahan 5: Validasi transaksi
    public Boolean validasiTransaksi() {
        return !daftarItem.isEmpty() && totalHarga > 0 && 
               namaPelanggan != null && !namaPelanggan.isEmpty();
    }
    
    // Implementasi interface Storable
    @Override
    public String getId() {
        return idTransaksi;
    }
    
    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append(idTransaksi).append("|");
        sb.append(tanggalTransaksi.format(formatter)).append("|");
        sb.append(namaPelanggan).append("|");
        sb.append(totalHarga).append("|");
        sb.append(jumlahItem).append("|");
        sb.append(daftarItem.size());
        
        for (ItemTransaksi item : daftarItem) {
            sb.append("|").append(item.getProduk().getKode());
            sb.append("|").append(item.getJumlah());
            sb.append("|").append(item.getSubtotal());
        }
        
        return sb.toString();
    }
    
    @Override
    public void fromFileString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length >= 6) {
            this.idTransaksi = parts[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.tanggalTransaksi = LocalDateTime.parse(parts[1], formatter);
            this.namaPelanggan = parts[2];
            this.totalHarga = Double.parseDouble(parts[3]);
            this.jumlahItem = Integer.parseInt(parts[4]);
            Integer itemCount = Integer.parseInt(parts[5]);
            
            this.daftarItem = new ArrayList<>();
            // Note: Loading items from file requires product lookup
            // Implementation depends on product repository
        }
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "ID: " + idTransaksi + 
               ", Tanggal: " + tanggalTransaksi.format(formatter) +
               ", Pelanggan: " + namaPelanggan + 
               ", Total: Rp " + String.format("%.2f", totalHarga) +
               ", Jumlah Item: " + jumlahItem;
    }
}
