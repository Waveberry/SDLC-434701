package lab;

import javax.swing.*;

public final class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            ConversionModel model = new ConversionModel();
            MainView view = new MainView(model);
            new ConversionController(model, view);
            view.setVisible(true);
        });
    }
}
