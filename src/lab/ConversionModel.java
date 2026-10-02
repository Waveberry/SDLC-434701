package lab;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.MathContext;

public final class ConversionModel {
    public record Result(BigDecimal amount, WeightUnit from, WeightUnit to, BigDecimal value) {
    }

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private Result result;

    public void addListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener("result", listener);
    }

    public Result getResult() {
        return result;
    }

    public void convert(String text, WeightUnit from, WeightUnit to) {
        if (text == null || !text.trim().matches("[0-9]+([.,][0-9]+)?"))
            throw new IllegalArgumentException("Введите неотрицательное число, например 12,5.");
        if (text.trim().length() > 30)
            throw new IllegalArgumentException("Введите число длиной не более 30 символов.");
        if (from == null || to == null)
            throw new IllegalArgumentException("Выберите обе единицы измерения.");
        BigDecimal amount = new BigDecimal(text.trim().replace(',', '.'));
        BigDecimal kilograms = amount.multiply(from.kilograms());
        BigDecimal converted = kilograms.divide(to.kilograms(), MathContext.DECIMAL128);
        Result previous = result;
        result = new Result(amount, from, to, converted);
        changes.firePropertyChange("result", previous, result);
    }
}
