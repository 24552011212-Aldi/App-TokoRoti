package model;

public enum LevelKaryawan {
    STAF_TOKO(0.02),
    KASIR(0.03),
    BAKER(0.05),
    MANAGER(0.08);

    private final double bonusRate;

    LevelKaryawan(double bonusRate) {
        this.bonusRate = bonusRate;
    }

    public double getBonusRate() {
        return bonusRate;
    }

    public double hitungBonus(double nilaiPenjualan) {
        return nilaiPenjualan * bonusRate;
    }
}
