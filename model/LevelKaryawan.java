package model;

public enum LevelKaryawan {
    JUNIOR(0.02),
    SENIOR(0.05),
    LEAD(0.10);

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
