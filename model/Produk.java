package model;

/**
 * Subclass 2: Inheritance dari Item
 * Memenuhi requirement: Subclass (extends), implements Interface, dan menggunakan Enum
 */
public class Produk extends Item {
    // Atribut tambahan (menggunakan Enum Type)
    private KategoriRoti kategori;
    private Integer stokProduk;
    private String deskripsi;
    
    // Constructor berparameter
    public Produk(String kode, String nama, Double harga, KategoriRoti kategori, 
                  Integer stokProduk, String deskripsi) {
        super(kode, nama, harga);
        this.kategori = kategori;
        this.stokProduk = stokProduk;
        this.deskripsi = deskripsi;
    }
    
    // Default constructor
    public Produk() {
        super("", "", 0.0);
        this.kategori = KategoriRoti.TAWAR;
        this.stokProduk = 0;
        this.deskripsi = "";
    }
    
    // Getter dan Setter
    public KategoriRoti getKategori() {
        return kategori;
    }
    
    public void setKategori(KategoriRoti kategori) {
        this.kategori = kategori;
    }
    
    public Integer getStokProduk() {
        return stokProduk;
    }
    
    public void setStokProduk(Integer stokProduk) {
        this.stokProduk = stokProduk;
    }
    
    public String getDeskripsi() {
        return deskripsi;
    }
    
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    // Method overriding (Polymorphism)
    @Override
    public String getTipeItem() {
        return "Produk Roti";
    }
    
    // Method tambahan 1: Menambah stok produk
    public void tambahStokProduk(Integer jumlah) {
        this.stokProduk += jumlah;
    }
    
    // Method tambahan 2: Kurangi stok produk
    public Boolean kurangiStokProduk(Integer jumlah) {
        if (stokProduk >= jumlah) {
            stokProduk -= jumlah;
            return true;
        }
        return false;
    }
    
    // Method tambahan 3: Cek ketersediaan
    public Boolean cekKetersediaan() {
        return stokProduk > 0;
    }
    
    // Method tambahan 4: Hitung harga berdasarkan kategori
    public Double hitungHargaKategori() {
        // Wrapper Class digunakan di sini
        Double hargaAkhir = getHarga();
        switch (kategori) {
            case MANIS:
                hargaAkhir *= 1.2; // Premium 20%
                break;
            case ASIN:
                hargaAkhir *= 1.1; // Premium 10%
                break;
            case TAWAR:
                // Harga standar
                break;
        }
        return hargaAkhir;
    }
    
    // Implementasi interface Storable
    @Override
    public String toFileString() {
        return getKode() + "|" + getNama() + "|" + getHarga() + "|" + 
               kategori.name() + "|" + stokProduk + "|" + deskripsi;
    }
    
    @Override
    public void fromFileString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length >= 6) {
            setKode(parts[0]);
            setNama(parts[1]);
            setHarga(Double.parseDouble(parts[2]));
            this.kategori = KategoriRoti.valueOf(parts[3]);
            this.stokProduk = Integer.parseInt(parts[4]);
            this.deskripsi = parts[5];
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Kategori: " + kategori.getDeskripsi() + 
               ", Stok: " + stokProduk + ", Deskripsi: " + deskripsi;
    }
}
