package lab;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;

public final class MainView extends JFrame {
    public record Input(String text, WeightUnit from, WeightUnit to) {
    }

    private Input lastInput = new Input("1", WeightUnit.KILOGRAM, WeightUnit.POUND);
    final JButton enterButton = new JButton("Ввести данные");
    final JLabel sourceLabel = new JLabel("Данные ещё не введены");
    final JTextArea resultLabel = new JTextArea("Нажмите «Ввести данные», чтобы выполнить конвертацию.");

    public MainView(ConversionModel model) {
        super("Конвертер веса");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBorder(BorderFactory.createEmptyBorder(24, 26, 24, 26));
        JLabel heading = new JLabel("Конвертер веса");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 26f));
        JPanel top = new JPanel(new GridLayout(2, 1, 0, 12));
        top.add(heading);
        top.add(sourceLabel);
        content.add(top, BorderLayout.NORTH);
        resultLabel.setEditable(false);
        resultLabel.setLineWrap(true);
        resultLabel.setWrapStyleWord(true);
        resultLabel.setOpaque(false);
        resultLabel.setFont(heading.getFont().deriveFont(19f));
        content.add(resultLabel, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new BorderLayout(0, 15));
        JTextArea reference = new JTextArea("Условные массы: медведь — 500 кг; слон — 6000 кг;\n"
                + "жигули — 1000 кг; банка пива — 0,5 кг;\nцыпленок — 0,05 кг; лист бумаги — 0,005 кг.\n"
                + "1 фунт = 0,45359237 кг. Данные сохраняются до закрытия программы.");
        reference.setEditable(false);
        reference.setOpaque(false);
        reference.setFont(reference.getFont().deriveFont(13f));
        bottom.add(reference, BorderLayout.CENTER);
        bottom.add(enterButton, BorderLayout.SOUTH);
        content.add(bottom, BorderLayout.SOUTH);
        setContentPane(content);
        setSize(670, 400);
        setMinimumSize(new Dimension(620, 380));
        setLocationRelativeTo(null);
        model.addListener(event -> showResult((ConversionModel.Result) event.getNewValue()));
    }

    public Input getLastInput() {
        return lastInput;
    }

    public void remember(Input input) {
        lastInput = input;
    }

    private static String format(BigDecimal number) {
        return number.round(new MathContext(12)).stripTrailingZeros().toPlainString().replace('.', ',');
    }

    private void showResult(ConversionModel.Result result) {
        sourceLabel.setText("Введено: " + format(result.amount()) + " · " + result.from());
        resultLabel.setText("Результат: " + format(result.value()) + " · " + result.to());
    }
}
