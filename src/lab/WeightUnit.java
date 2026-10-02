package lab;

import java.math.BigDecimal;

public enum WeightUnit {
    POUND("Фунт", "0.45359237"),
    KILOGRAM("Килограмм", "1"),
    POLAR_BEAR("Белый медведь", "500"),
    ELEPHANT("Африканский слон", "6000"),
    ZHIGULI("Жигули", "1000"),
    BEER_CAN("Банка пива", "0.5"),
    CHICK("Цыпленок", "0.05"),
    PAPER("Лист бумаги", "0.005");

    private final String title;
    private final BigDecimal kilograms;

    WeightUnit(String title, String kilograms) {
        this.title = title;
        this.kilograms = new BigDecimal(kilograms);
    }

    public BigDecimal kilograms() {
        return kilograms;
    }

    @Override
    public String toString() {
        return title;
    }
}
