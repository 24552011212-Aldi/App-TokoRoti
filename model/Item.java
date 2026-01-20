package model;

/**
 * Abstract Class sebagai Superclass
 * Memenuhi requirement: Abstract Class dengan minimal 1 abstract method
 */
public abstract class Item implements Storable {
    // Minimal 3 atribut private
    private String kode;
    private String nama;
    private Double harga;
    
    // Constructor berparameter
    public Item(String kode, String nama, Double harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }
    
    // Getter dan Setter (Enkapsulasi)
    public String getKode() {
        return kode;
    }
    
    public void setKode(String kode) {
        this.kode = kode;
    }
    
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public Double getHarga() {
        return harga;
    }
    
    public void setHarga(Double harga) {
        this.harga = harga;
    }
    
    // Abstract method (wajib diimplementasi oleh subclass)
    public abstract String getTipeItem();
    
    // Method tambahan 1: Menghitung harga dengan diskon
    public Double hitungHargaDiskon(Integer persenDiskon) {
        return harga - (harga * persenDiskon / 100.0);
    }
    
    // Method tambahan 2: Validasi harga
    public Boolean validasiHarga() {
        return harga != null && harga > 0;
    }
    
    // Implementasi interface Storable
    @Override
    public String getId() {
        return kode;
    }
    
    @Override
    public String toString() {
        return "Kode: " + kode + ", Nama: " + nama + ", Harga: " + harga;
    }
}
