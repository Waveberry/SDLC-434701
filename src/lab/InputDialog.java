package lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public final class InputDialog extends JDialog {
    final JTextField amount = new JTextField(22);
    final JComboBox<WeightUnit> from = new JComboBox<>(WeightUnit.values());
    final JComboBox<WeightUnit> to = new JComboBox<>(WeightUnit.values());
    final JButton submit = new JButton("Рассчитать");
    final JButton cancel = new JButton("Отмена");

    public InputDialog(MainView owner, Consumer<MainView.Input> onSubmit) {
        super(owner, "Ввод данных", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        MainView.Input saved = owner.getLastInput();
        amount.setText(saved.text()); from.setSelectedItem(saved.from()); to.setSelectedItem(saved.to());
        JPanel fields = new JPanel(new GridLayout(3, 2, 14, 14));
        fields.add(new JLabel("Вес")); fields.add(amount);
        fields.add(new JLabel("Из единицы")); fields.add(from);
        fields.add(new JLabel("В единицу")); fields.add(to);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT)); actions.add(cancel); actions.add(submit);
        JPanel body = new JPanel(new BorderLayout(0, 20));
        body.setBorder(BorderFactory.createEmptyBorder(22, 22, 16, 22));
        body.add(fields, BorderLayout.CENTER); body.add(actions, BorderLayout.SOUTH); setContentPane(body);
        submit.addActionListener(e -> onSubmit.accept(new MainView.Input(amount.getText(),
            (WeightUnit) from.getSelectedItem(), (WeightUnit) to.getSelectedItem())));
        cancel.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(submit);
        getRootPane().registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
        pack(); setResizable(false); setLocationRelativeTo(owner);
    }
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
    }
}
