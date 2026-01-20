package model;

/**
 * Subclass 1: Inheritance dari Item
 * Memenuhi requirement: Subclass (extends) dan implements Interface
 */
public class BahanBaku extends Item {
    // Atribut tambahan (total 6 atribut dengan superclass)
    private Integer stok;
    private String satuan;
    private String supplier;
    
    // Constructor berparameter
    public BahanBaku(String kode, String nama, Double harga, Integer stok, String satuan, String supplier) {
        super(kode, nama, harga);
        this.stok = stok;
        this.satuan = satuan;
        this.supplier = supplier;
    }
    
    // Default constructor
    public BahanBaku() {
        super("", "", 0.0);
        this.stok = 0;
        this.satuan = "";
        this.supplier = "";
    }
    
    // Getter dan Setter
    public Integer getStok() {
        return stok;
    }
    
    public void setStok(Integer stok) {
        this.stok = stok;
    }
    
    public String getSatuan() {
        return satuan;
    }
    
    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
    
    public String getSupplier() {
        return supplier;
    }
    
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
    // Method overriding (Polymorphism)
    @Override
    public String getTipeItem() {
        return "Bahan Baku";
    }
    
    // Method tambahan 1: Menambah stok
    public void tambahStok(Integer jumlah) {
        this.stok += jumlah;
    }
    
    // Method tambahan 2: Kurangi stok
    public Boolean kurangiStok(Integer jumlah) {
        if (stok >= jumlah) {
            stok -= jumlah;
            return true;
        }
        return false;
    }
    
    // Method tambahan 3: Cek stok minimum
    public Boolean cekStokMinimum(Integer batasMinimum) {
        return stok <= batasMinimum;
    }
    
    // Implementasi interface Storable
    @Override
    public String toFileString() {
        return getKode() + "|" + getNama() + "|" + getHarga() + "|" + 
               stok + "|" + satuan + "|" + supplier;
    }
    
    @Override
    public void fromFileString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length >= 6) {
            setKode(parts[0]);
            setNama(parts[1]);
            setHarga(Double.parseDouble(parts[2]));
            this.stok = Integer.parseInt(parts[3]);
            this.satuan = parts[4];
            this.supplier = parts[5];
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Stok: " + stok + " " + satuan + 
               ", Supplier: " + supplier;
    }
}
