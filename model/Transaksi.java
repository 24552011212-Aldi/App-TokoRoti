package model;

public class Transaksi extends Entitas {
	private Karyawan kasir;
	private Produk produk;
	private int jumlah;
	private StatusTransaksi status;

	public Transaksi(String kode, Karyawan kasir, Produk produk, int jumlah) {
		super(kode, "Transaksi " + kode);
		this.kasir = kasir;
		this.produk = produk;
		this.jumlah = jumlah;
		this.status = StatusTransaksi.DIBUAT;
	}

	public Karyawan getKasir() {
		return kasir;
	}

	public void setKasir(Karyawan kasir) {
		this.kasir = kasir;
	}

	public Produk getProduk() {
		return produk;
	}

	public void setProduk(Produk produk) {
		this.produk = produk;
	}

	public int getJumlah() {
		return jumlah;
	}

	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}

	public StatusTransaksi getStatus() {
		return status;
	}

	public void setStatus(StatusTransaksi status) {
		this.status = status;
	}

	public double hitungTotal() {
		return produk.hitungNilai(jumlah);
	}

	public void proses() {
		status = StatusTransaksi.DIPROSES;
	}

	public void selesaikan() {
		status = StatusTransaksi.SELESAI;
	}

	public void batalkan() {
		status = StatusTransaksi.DIBATALKAN;
	}

	@Override
	public String deskripsi() {
		return "Transaksi Toko Roti " + getKode() + " oleh " + kasir.getNama() + " untuk " + produk.getNama()
			+ " x" + jumlah + " - status: " + status;
	}
}
