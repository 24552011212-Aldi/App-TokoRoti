package model;

/**
 * Interface untuk class yang dapat disimpan ke file
 * Memenuhi requirement: Interface dengan minimal 2 method
 */
public interface Storable {
    /**
     * Mengkonversi object ke format string untuk disimpan
     */
    String toFileString();
    
    /**
     * Memuat data dari string
     */
    void fromFileString(String data);
    
    /**
     * Mendapatkan ID unik object
     */
    String getId();
}
