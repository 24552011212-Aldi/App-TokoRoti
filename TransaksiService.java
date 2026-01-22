package service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * TransaksiService - Implementasi IOperasiData
 * Logika pemrosesan transaksi dan penghitungan kembalian dengan JSON file handling
 */
public class TransaksiService implements IOperasiData<String> {
    
    // Atribut private
    private String namaKasir; // Nama kasir default (tidak digunakan jika diinput per transaksi)
    private double totalTransaksi; // Total nilai semua transaksi yang tercatat
    private final List<String> riwayatTransaksi; // Riwayat semua transaksi
    
    // Constructor berparameter
    public TransaksiService(String namaKasir) {
        this.namaKasir = namaKasir;
        this.totalTransaksi = 0.0;
        this.riwayatTransaksi = new ArrayList<>();
    }
    
    // Getter dan Setter
    public String getNamaKasir() {
        return namaKasir;
    }
    
    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }
    
    public double getTotalTransaksi() {
        return totalTransaksi;
    }
    
    public void setTotalTransaksi(double totalTransaksi) {
        this.totalTransaksi = totalTransaksi;
    }
    
    public List<String> getRiwayatTransaksi() {
        return riwayatTransaksi;
    }
    
    // Method 1: Implementasi interface - Load data dari JSON file
    @Override
    public void loadDataFromFile() {
        String filePath = "data/transaksi_log.json";
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("[TransaksiService] File tidak ditemukan, membuat file baru");
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
                
                // Extract namaKasir
                String namaKasirPattern = "\"namaKasir\":\"";
                int startIdx = json.indexOf(namaKasirPattern);
                if (startIdx != -1) {
                    startIdx += namaKasirPattern.length();
                    int endIdx = json.indexOf("\"", startIdx);
                    this.namaKasir = json.substring(startIdx, endIdx);
                }
                
                // Extract totalTransaksi
                String totalPattern = "\"totalTransaksi\":";
                startIdx = json.indexOf(totalPattern);
                if (startIdx != -1) {
                    startIdx += totalPattern.length();
                    int endIdx = json.indexOf(",", startIdx);
                    if (endIdx == -1) endIdx = json.indexOf("}", startIdx);
                    try {
                        this.totalTransaksi = Double.parseDouble(json.substring(startIdx, endIdx).trim());
                    } catch (NumberFormatException e) {
                        this.totalTransaksi = 0.0;
                    }
                }
                
                // Extract riwayatTransaksi array
                String arrayPattern = "\"riwayatTransaksi\":[";
                startIdx = json.indexOf(arrayPattern);
                if (startIdx != -1) {
                    startIdx += arrayPattern.length();
                    int endIdx = json.lastIndexOf("]");
                    String arrayContent = json.substring(startIdx, endIdx);
                    riwayatTransaksi.clear();
                    
                    // Parse array items
                    String[] items = arrayContent.split("\"");
                    for (String item : items) {
                        item = item.trim();
                        if (!item.isEmpty() && !item.equals(",") && !item.startsWith("[")) {
                            riwayatTransaksi.add(item);
                        }
                    }
                }
            }
            
            System.out.println("[TransaksiService] Data transaksi berhasil dimuat dari JSON file: " + filePath);
        } catch (IOException e) {
            System.out.println("[ERROR TransaksiService] Gagal membaca file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR TransaksiService] Gagal parse JSON: " + e.getMessage());
        }
    }
    
    // Method 2: Implementasi interface - Save data ke JSON file
    @Override
    public void saveDataToFile() {
        String filePath = "data/transaksi_log.json";
        try {
            // Buat direktori jika belum ada
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // Build JSON manually
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            json.append("  \"namaKasir\": \"").append(namaKasir).append("\",\n");
            json.append("  \"totalTransaksi\": ").append(totalTransaksi).append(",\n");
            json.append("  \"riwayatTransaksi\": [\n");
            
            for (int i = 0; i < riwayatTransaksi.size(); i++) {
                json.append("    \"").append(escapeJson(riwayatTransaksi.get(i))).append("\"");
                if (i < riwayatTransaksi.size() - 1) {
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
            
            System.out.println("[TransaksiService] Data transaksi berhasil disimpan ke JSON file: " + filePath);
        } catch (IOException e) {
            System.out.println("[ERROR TransaksiService] Gagal menulis file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[ERROR TransaksiService] Gagal membuat JSON: " + e.getMessage());
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
    
    // Method 3: Proses transaksi penjualan
    /**
     * Memproses transaksi penjualan dengan validasi pembayaran
     * @param namaKasir Nama kasir yang melayani transaksi ini
     * @param namaBarang Nama barang yang dibeli
     * @param hargaSatuan Harga satuan barang (Rp)
     * @param jumlahBarang Jumlah barang yang dibeli
     * @param uangBayar Uang yang diberikan pembeli (Rp)
     * @return Array berisi [totalHarga, uangKembali] atau [-1, -1] jika pembayaran kurang
     */
    public double[] prosesPenjualan(String namaKasir, String namaBarang, double hargaSatuan, int jumlahBarang, double uangBayar) {
        double totalHarga = hargaSatuan * jumlahBarang;
        double uangKembali = uangBayar - totalHarga;
        
        // Validasi pembayaran
        if (uangBayar < totalHarga) {
            System.out.println("[ERROR] Pembayaran kurang! Kurang Rp " + (totalHarga - uangBayar));
            return new double[] {totalHarga, -1}; // -1 menandakan pembayaran gagal
        }
        
        // Update total transaksi
        totalTransaksi += totalHarga;
        
        // Catat transaksi
        String waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String catatan = String.format("[%s] Kasir: %s | Barang: %s | Qty: %d | Harga/satuan: Rp%.2f | Total: Rp%.2f | Bayar: Rp%.2f | Kembalian: Rp%.2f",
                                      waktu, namaKasir, namaBarang, jumlahBarang, hargaSatuan, 
                                      totalHarga, uangBayar, uangKembali);
        riwayatTransaksi.add(catatan);
        
        System.out.println("[TRANSAKSI] " + catatan);
        return new double[] {totalHarga, uangKembali};
    }
    
    // Method 4: Hitung kembalian
    /**
     * Menghitung jumlah uang kembalian
     * @param totalBelanja Total harga barang belanja (Rp)
     * @param uangBayar Uang yang diberikan (Rp)
     * @return Jumlah uang kembalian (negatif jika pembayaran kurang)
     */
    public double hitungKembalian(double totalBelanja, double uangBayar) {
        double kembalian = uangBayar - totalBelanja;
        String catatan = String.format("Perhitungan Kembalian: Rp%.2f (bayar) - Rp%.2f (belanja) = Rp%.2f", 
                                      uangBayar, totalBelanja, kembalian);
        riwayatTransaksi.add(catatan);
        return kembalian;
    }
    
    // Method 5: Validasi pembayaran
    /**
     * Validasi apakah pembayaran cukup
     * @param totalBelanja Total harga barang (Rp)
     * @param uangBayar Uang pembayaran (Rp)
     * @return true jika pembayaran cukup, false jika kurang
     */
    public boolean validasiPembayaran(double totalBelanja, double uangBayar) {
        boolean valid = uangBayar >= totalBelanja;
        String status = valid ? "Valid - Pembayaran Cukup" : "Tidak Valid - Pembayaran Kurang";
        String catatan = String.format("Validasi Pembayaran: %s (Total: Rp%.2f | Bayar: Rp%.2f)", 
                                      status, totalBelanja, uangBayar);
        riwayatTransaksi.add(catatan);
        System.out.println("[Validasi Pembayaran] " + status);
        return valid;
    }
    
    // Method 6: Tampilkan riwayat transaksi
    /**
     * Menampilkan semua riwayat transaksi
     * (Nama kasir tercatat di setiap transaksi)
     */
    public void tampilkanRiwayatTransaksi() {
        System.out.println("\n=== RIWAYAT SEMUA TRANSAKSI ===");
        if (riwayatTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi");
            return;
        }
        for (int i = 0; i < riwayatTransaksi.size(); i++) {
            System.out.println((i + 1) + ". " + riwayatTransaksi.get(i));
        }
        System.out.println("\nTotal Transaksi: Rp " + String.format("%.2f", totalTransaksi));
    }

    
    // Implementasi CRUD methods dari interface IOperasiData
    @Override
    public void tambah(String data) {
        riwayatTransaksi.add(data);
        System.out.println("[TransaksiService] Transaksi berhasil ditambahkan");
    }
    
    @Override
    public void ubah(String data) {
        int index = riwayatTransaksi.indexOf(data);
        if (index != -1) {
            riwayatTransaksi.set(index, data);
            System.out.println("[TransaksiService] Transaksi berhasil diubah");
        }
    }
    
    @Override
    public void hapus(String id) {
        riwayatTransaksi.removeIf(transaksi -> transaksi.contains(id));
        System.out.println("[TransaksiService] Transaksi berhasil dihapus");
    }
    
    @Override
    public String cari(String id) {
        return riwayatTransaksi.stream()
                .filter(transaksi -> transaksi.contains(id))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<String> tampilSemua() {
        return new ArrayList<>(riwayatTransaksi);
    }
}
