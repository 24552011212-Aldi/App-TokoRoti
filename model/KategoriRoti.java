package model;

/**
 * Enum untuk kategori roti
 * Memenuhi requirement: Enum Type
 */
public enum KategoriRoti {
    MANIS("Roti Manis"),
    ASIN("Roti Asin"),
    TAWAR("Roti Tawar");
    
    private final String deskripsi;
    
    KategoriRoti(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    public String getDeskripsi() {
        return deskripsi;
    }
}
