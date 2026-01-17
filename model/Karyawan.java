package model;

public class Karyawan extends Entitas {
	private LevelKaryawan level;
	private double gajiPokok;

	public Karyawan(String kode, String nama, LevelKaryawan level, double gajiPokok) {
		super(kode, nama);
		this.level = level;
		this.gajiPokok = gajiPokok;
	}

	public LevelKaryawan getLevel() {
		return level;
	}

	public void setLevel(LevelKaryawan level) {
		this.level = level;
	}

	public double getGajiPokok() {
		return gajiPokok;
	}

	public void setGajiPokok(double gajiPokok) {
		this.gajiPokok = gajiPokok;
	}

	public double hitungGajiTotal(double nilaiPenjualan) {
		double bonus = level.hitungBonus(nilaiPenjualan);
		return gajiPokok + bonus;
	}

	@Override
	public String deskripsi() {
		return "Karyawan Toko Roti: " + getNama() + " - " + level + " (kode: " + getKode() + ")";
	}
}
