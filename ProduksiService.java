package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProduksiService - Implementasi IOperasiData
 * Logika konversi bahan baku menjadi produk jadi dengan JSON file handling
 */
public class ProduksiService implements IOperasiData<String> {
    
    // Atribut private
    private String namaProduksi;
    private double rateKonversi; // Rasio konversi bahan baku ke produk jadi (kg bahan -> unit produk)
    private final List<String> logAktivitas; // Log setiap aktivitas produksi
    
    // Constructor berparameter
    public ProduksiService(String namaProduksi, double rateKonversi) {
        this.namaProduksi = namaProduksi;
        this.rateKonversi = rateKonversi;
        this.logAktivitas = new ArrayList<>();
    }
    
    // Getter dan Setter
    public String getNamaProduksi() {
        return namaProduksi;
    }
    
    public void setNamaProduksi(String namaProduksi) {
        this.namaProduksi = namaProduksi;
    }
    
    public double getRateKonversi() {
        return rateKonversi;
    }
    
    public void setRateKonversi(double rateKonversi) {
        this.rateKonversi = rateKonversi;
    }
    
    public List<String> getLogAktivitas() {
        return logAktivitas;
    }
    
    // Method 1: Implementasi interface - Load data dari JSON file
    @Override
    public void loadDataFromFile() {
        String filePath = "data/produksi_log.json";
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("[ProduksiService] File tidak ditemukan, membuat file baru");
                saveDataToFile();
                return;
            }
            
            StringBuilder jsonContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
            }
            
            // Parse JSON manually
            String json = jsonContent.toString().trim();
            if (json.startsWith("{") && json.endsWith("}")) {
                json = json.substring(1, json.length() - 1);
                
                // Extract namaProduksi
                String namaProduksiPattern = "\"namaProduksi\":\"";
                int startIdx = json.indexOf(namaProduksiPattern);
                if (startIdx != -1) {
                    startIdx += namaProduksiPattern.length();
                    int endIdx = json.indexOf("\"", startIdx);
                    this.namaProduksi = json.substring(startIdx, endIdx);
                }
                
                // Extract rateKonversi
                String ratePattern = "\"rateKonversi\":";
                startIdx = json.indexOf(ratePattern);
                if (startIdx != -1) {
                    startIdx += ratePattern.length();
                    int endIdx = json.indexOf(",", startIdx);
                    if (endIdx == -1) endIdx = json.indexOf("}", startIdx);
                    try {
                        this.rateKonversi = Double.parseDouble(json.substring(startIdx, endIdx).trim());
                    } catch (NumberFormatException e) {
                        this.rateKonversi = 1.0;
                    }
                }
                
                // Extract logAktivitas array
                String arrayPattern = "\"logAktivitas\":[";
                startIdx = json.indexOf(arrayPattern);
                if (startIdx != -1) {
                    startIdx += arrayPattern.length();
                    int endIdx = json.lastIndexOf("]");
                    String arrayContent = json.substring(startIdx, endIdx);
                    logAktivitas.clear();
                    
                    // Parse array items
                    String[] items = arrayContent.split("\"");
                    for (String item : items) {
                        item = item.trim();
                        if (!item.isEmpty() && !item.equals(",") && !item.startsWith("[")) {
                            logAktivitas.add(item);
                        }
                    }
                }
            }
            
            System.out.println("[ProduksiService] Data produksi berhasil dimuat dari JSON file: " + filePath);
        } catch (IOException e) {
            System.out.println("[ERROR ProduksiService] Gagal membaca file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR ProduksiService] Gagal parse JSON: " + e.getMessage());
        }
    }
    
    // Method 2: Implementasi interface - Save data ke JSON file
    @Override
    public void saveDataToFile() {
        String filePath = "data/produksi_log.json";
        try {
            // Buat direktori jika belum ada
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // Build JSON manually
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            json.append("  \"namaProduksi\": \"").append(namaProduksi).append("\",\n");
            json.append("  \"rateKonversi\": ").append(rateKonversi).append(",\n");
            json.append("  \"logAktivitas\": [\n");
            
            for (int i = 0; i < logAktivitas.size(); i++) {
                json.append("    \"").append(escapeJson(logAktivitas.get(i))).append("\"");
                if (i < logAktivitas.size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }
            
            json.append("  ]\n");
            json.append("}");
            
            // Write ke file
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(json.toString());
            }
            
            System.out.println("[ProduksiService] Data produksi berhasil disimpan ke JSON file: " + filePath);
        } catch (IOException e) {
            System.out.println("[ERROR ProduksiService] Gagal menulis file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR ProduksiService] Gagal membuat JSON: " + e.getMessage());
        }
    }
    
    // Helper method untuk escape JSON string
    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    // Method 3: Konversi bahan baku menjadi produk jadi
    /**
     * Mengkonversi jumlah bahan baku menjadi jumlah produk jadi
     * @param jumlahBahanBaku Jumlah bahan baku dalam kg
     * @return Jumlah produk jadi yang dihasilkan
     */
    public double konversiBahanBakuKeProduk(double jumlahBahanBaku) {
        double hasilProduksi = jumlahBahanBaku * rateKonversi;
        String catatan = String.format("Konversi: %.2f kg bahan baku -> %.2f unit produk (rate: %.2f)", 
                                      jumlahBahanBaku, hasilProduksi, rateKonversi);
        logAktivitas.add(catatan);
        System.out.println("[Produksi] " + catatan);
        return hasilProduksi;
    }
    
    // Method 4: Validasi kapasitas produksi
    /**
     * Validasi apakah bahan baku cukup untuk produksi
     * @param jumlahBahanBakuTersedia Jumlah bahan baku tersedia
     * @param jumlahBahanBakuDibutuhkan Jumlah bahan baku yang dibutuhkan
     * @return true jika cukup, false jika tidak
     */
    public boolean validasiKapasitasProduksi(double jumlahBahanBakuTersedia, double jumlahBahanBakuDibutuhkan) {
        boolean valid = jumlahBahanBakuTersedia >= jumlahBahanBakuDibutuhkan;
        String status = valid ? "Valid" : "Tidak Valid";
        String catatan = String.format("Validasi Kapasitas: %s (Tersedia: %.2f kg, Dibutuhkan: %.2f kg)", 
                                      status, jumlahBahanBakuTersedia, jumlahBahanBakuDibutuhkan);
        logAktivitas.add(catatan);
        System.out.println("[Validasi] " + catatan);
        return valid;
    }
    
    // Method 5: Hitung sisa bahan baku setelah produksi
    /**
     * Menghitung sisa bahan baku setelah proses produksi
     * @param jumlahBahanBakuAwal Jumlah awal bahan baku
     * @param jumlahBahanBakuDigunakan Jumlah bahan baku yang digunakan
     * @return Sisa bahan baku
     */
    public double hitungSisaBahanBaku(double jumlahBahanBakuAwal, double jumlahBahanBakuDigunakan) {
        double sisa = jumlahBahanBakuAwal - jumlahBahanBakuDigunakan;
        String catatan = String.format("Perhitungan Sisa: %.2f kg (awal) - %.2f kg (digunakan) = %.2f kg (sisa)", 
                                      jumlahBahanBakuAwal, jumlahBahanBakuDigunakan, sisa);
        logAktivitas.add(catatan);
        return sisa;
    }
    
    // Method 6: Tampilkan log aktivitas produksi
    /**
     * Menampilkan semua log aktivitas produksi
     */
    public void tampilkanLogAktivitas() {
        System.out.println("\n=== LOG AKTIVITAS PRODUKSI: " + namaProduksi + " ===");
        if (logAktivitas.isEmpty()) {
            System.out.println("Belum ada aktivitas produksi");
            return;
        }
        for (int i = 0; i < logAktivitas.size(); i++) {
            System.out.println((i + 1) + ". " + logAktivitas.get(i));
        }
    }
    
    // Implementasi CRUD methods dari interface IOperasiData
    @Override
    public void tambah(String data) {
        logAktivitas.add(data);
        System.out.println("[ProduksiService] Log aktivitas berhasil ditambahkan");
    }
    
    @Override
    public void ubah(String data) {
        int index = logAktivitas.indexOf(data);
        if (index != -1) {
            logAktivitas.set(index, data);
            System.out.println("[ProduksiService] Log aktivitas berhasil diubah");
        }
    }
    
    @Override
    public void hapus(String id) {
        logAktivitas.removeIf(log -> log.contains(id));
        System.out.println("[ProduksiService] Log aktivitas berhasil dihapus");
    }
    
    @Override
    public String cari(String id) {
        return logAktivitas.stream()
                .filter(log -> log.contains(id))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<String> tampilSemua() {
        return new ArrayList<>(logAktivitas);
    }
}
