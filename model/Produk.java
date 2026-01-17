package model;

public class Produk extends Entitas {
	private JenisProduk jenis;
	private double hargaSatuan;

	public Produk(String kode, String nama, JenisProduk jenis, double hargaSatuan) {
		super(kode, nama);
		this.jenis = jenis;
		this.hargaSatuan = hargaSatuan;
	}

	public JenisProduk getJenis() {
		return jenis;
	}

	public void setJenis(JenisProduk jenis) {
		this.jenis = jenis;
	}

	public double getHargaSatuan() {
		return hargaSatuan;
	}

	public void setHargaSatuan(double hargaSatuan) {
		this.hargaSatuan = hargaSatuan;
	}

	public double hitungNilai(int jumlah) {
		return hargaSatuan * jumlah;
	}

	@Override
	public String deskripsi() {
		return "Produk " + getNama() + " - " + jenis + " (kode: " + getKode() + ")";
	}
}
