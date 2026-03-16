package calculator;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorModel      model      = new CalculatorModel();
            CalculatorView       view       = new CalculatorView();
            CalculatorController controller = new CalculatorController(model, view);
        });
    }
}