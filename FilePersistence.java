package data;

import java.io.*;
import java.util.List;
import model.BahanBaku;
import model.KategoriRoti;
import model.Produk;

/**
 * Class FilePersistence
 * Kelas khusus untuk File Handling
 * Berisi logika BufferedReader dan BufferedWriter untuk akses ke file .json
 * Menangani persistensi data Produk dan BahanBaku
 */
public class FilePersistence {
    
    private static final String PRODUK_FILE = "data/produk_data.json";
    private static final String BAHAN_FILE = "data/bahan_baku_data.json";
    
    // Constructor
    public FilePersistence() {
        // Ensure directory exists
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    // ============== PRODUK FILE OPERATIONS ==============
    
    /**
     * Load data Produk dari file JSON menggunakan BufferedReader
     */
    public void loadProdukFromFile(List<Produk> daftarProduk) {
        File file = new File(PRODUK_FILE);
        if (!file.exists()) {
            System.out.println("[FilePersistence] File produk tidak ditemukan: " + PRODUK_FILE);
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("{")) {
                    continue;
                }
                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1); // buang trailing comma
                }
                parseProdukLine(line, daftarProduk);
            }
            System.out.println("[FilePersistence] Produk berhasil dimuat dari: " + PRODUK_FILE);
        } catch (IOException e) {
            System.out.println("[FilePersistence ERROR] Gagal membaca file produk: " + e.getMessage());
        }
    }
    
    /**
     * Save data Produk ke file JSON menggunakan BufferedWriter
     * Wrapper Class: Integer untuk stok, Double untuk harga
     */
    public void saveProdukToFile(List<Produk> daftarProduk) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUK_FILE))) {
            writer.write("[\n");
            for (int i = 0; i < daftarProduk.size(); i++) {
                Produk p = daftarProduk.get(i);
                // Wrapper Class: Integer stok, Double harga
                writer.write("  {\"kode\":\"" + p.getKode() + "\",");
                writer.write("\"nama\":\"" + p.getNama() + "\",");
                writer.write("\"harga\":" + p.getHarga() + ",");
                writer.write("\"kategori\":\"" + p.getKategori().name() + "\",");
                writer.write("\"stok\":" + p.getStokProduk() + ",");
                writer.write("\"deskripsi\":\"" + escapeJson(p.getDeskripsi()) + "\"}");
                
                if (i < daftarProduk.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]");
            System.out.println("[FilePersistence] Produk berhasil disimpan ke: " + PRODUK_FILE);
        } catch (IOException e) {
            System.out.println("[FilePersistence ERROR] Gagal menulis file produk: " + e.getMessage());
        }
    }
    
    // ============== BAHAN BAKU FILE OPERATIONS ==============
    
    /**
     * Load data BahanBaku dari file JSON menggunakan BufferedReader
     */
    public void loadBahanBakuFromFile(List<BahanBaku> daftarBahanBaku) {
        File file = new File(BAHAN_FILE);
        if (!file.exists()) {
            System.out.println("[FilePersistence] File bahan baku tidak ditemukan: " + BAHAN_FILE);
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("{")) {
                    continue;
                }
                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1); // buang trailing comma
                }
                parseBahanBakuLine(line, daftarBahanBaku);
            }
            System.out.println("[FilePersistence] Bahan Baku berhasil dimuat dari: " + BAHAN_FILE);
        } catch (IOException e) {
            System.out.println("[FilePersistence ERROR] Gagal membaca file bahan baku: " + e.getMessage());
        }
    }
    
    /**
     * Save data BahanBaku ke file JSON menggunakan BufferedWriter
     * Wrapper Class: Integer untuk stok, Double untuk harga
     */
    public void saveBahanBakuToFile(List<BahanBaku> daftarBahanBaku) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BAHAN_FILE))) {
            writer.write("[\n");
            for (int i = 0; i < daftarBahanBaku.size(); i++) {
                BahanBaku b = daftarBahanBaku.get(i);
                // Wrapper Class: Integer stok, Double harga
                writer.write("  {\"kode\":\"" + b.getKode() + "\",");
                writer.write("\"nama\":\"" + b.getNama() + "\",");
                writer.write("\"harga\":" + b.getHarga() + ",");
                writer.write("\"stok\":" + b.getStok() + ",");
                writer.write("\"satuan\":\"" + b.getSatuan() + "\",");
                writer.write("\"supplier\":\"" + escapeJson(b.getSupplier()) + "\"}");
                
                if (i < daftarBahanBaku.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]");
            System.out.println("[FilePersistence] Bahan Baku berhasil disimpan ke: " + BAHAN_FILE);
        } catch (IOException e) {
            System.out.println("[FilePersistence ERROR] Gagal menulis file bahan baku: " + e.getMessage());
        }
    }
    
    // ============== PARSING METHODS ==============
    
    /**
     * Parse line JSON untuk Produk
     */
    private void parseProdukLine(String line, List<Produk> daftarProduk) {
        try {
            String kode = extractJsonValue(line, "kode");
            String nama = extractJsonValue(line, "nama");
            Double harga = Double.parseDouble(extractJsonValue(line, "harga"));
            String kategoriStr = extractJsonValue(line, "kategori");
            Integer stok = Integer.parseInt(extractJsonValue(line, "stok"));
            String deskripsi = extractJsonValue(line, "deskripsi");
            
            KategoriRoti kategori = KategoriRoti.valueOf(kategoriStr);
            Produk produk = new Produk(kode, nama, harga, kategori, stok, deskripsi);
            daftarProduk.add(produk);
        } catch (Exception e) {
            System.out.println("[FilePersistence ERROR] Gagal parse Produk: " + e.getMessage());
        }
    }
    
    /**
     * Parse line JSON untuk BahanBaku
     */
    private void parseBahanBakuLine(String line, List<BahanBaku> daftarBahanBaku) {
        try {
            String kode = extractJsonValue(line, "kode");
            String nama = extractJsonValue(line, "nama");
            Double harga = Double.parseDouble(extractJsonValue(line, "harga"));
            Integer stok = Integer.parseInt(extractJsonValue(line, "stok"));
            String satuan = extractJsonValue(line, "satuan");
            String supplier = extractJsonValue(line, "supplier");
            
            BahanBaku bahanBaku = new BahanBaku(kode, nama, harga, stok, satuan, supplier);
            daftarBahanBaku.add(bahanBaku);
        } catch (Exception e) {
            System.out.println("[FilePersistence ERROR] Gagal parse BahanBaku: " + e.getMessage());
        }
    }
    
    // ============== HELPER METHODS ==============
    
    /**
     * Extract nilai dari JSON value
     */
    private String extractJsonValue(String line, String key) {
        String pattern = "\"" + key + "\":";
        int startIdx = line.indexOf(pattern);
        if (startIdx == -1) return "";
        
        startIdx += pattern.length();
        
        // Skip whitespace
        while (startIdx < line.length() && Character.isWhitespace(line.charAt(startIdx))) {
            startIdx++;
        }
        
        // Check if value is quoted
        if (line.charAt(startIdx) == '"') {
            startIdx++;
            int endIdx = line.indexOf("\"", startIdx);
            return line.substring(startIdx, endIdx);
        } else {
            // Unquoted value (number)
            int endIdx = startIdx;
            while (endIdx < line.length() && line.charAt(endIdx) != ',' && line.charAt(endIdx) != '}') {
                endIdx++;
            }
            return line.substring(startIdx, endIdx).trim();
        }
    }
    
    /**
     * Escape JSON string
     */
    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}
