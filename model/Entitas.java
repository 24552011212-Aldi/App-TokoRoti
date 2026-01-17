package model;

public abstract class Entitas {
    private final String kode;
    private String nama;

    protected Entitas(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public abstract String deskripsi();
}
