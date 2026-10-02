package lab;

public final class ConversionController {
    private final ConversionModel model;
    private final MainView view;
    InputDialog dialog;
    public ConversionController(ConversionModel model, MainView view) {
        this.model = model; this.view = view;
        view.enterButton.addActionListener(e -> openInput());
    }
    private void openInput() {
        dialog = new InputDialog(view, this::submit);
        dialog.setVisible(true);
    }
    private void submit(MainView.Input input) {
        try {
            model.convert(input.text(), input.from(), input.to());
            view.remember(input);
            dialog.dispose();
        } catch (IllegalArgumentException error) {
            dialog.showError(error.getMessage());
        }
    }
}
