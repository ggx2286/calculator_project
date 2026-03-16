package calculator;

import java.awt.event.ActionListener;

/**
 * Controller in MVC Pattern.
 * Handles user button actions and delegates to the Model.
 */
public class CalculatorController {

    private CalculatorModel model;
    private CalculatorView  view;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view  = view;

        // Register View as Observer of Model
        model.addObserver(view);

        attachListeners();
    }

    private void attachListeners() {
        // Number buttons 0-9
        for (int i = 0; i <= 9; i++) {
            final String digit = String.valueOf(i);
            view.getNumberButton(i).addActionListener(e -> model.appendDigit(digit));
        }

        // Dot
        view.getBtnDot().addActionListener(e -> model.appendDigit("."));

        // Operations
        view.getBtnAdd().addActionListener(e -> model.setOperation("+"));
        view.getBtnSub().addActionListener(e -> model.setOperation("-"));
        view.getBtnMul().addActionListener(e -> model.setOperation("*"));
        view.getBtnDiv().addActionListener(e -> model.setOperation("/"));

        // Equals
        view.getBtnEquals().addActionListener(e -> model.calculate());

        // Clear
        view.getBtnClear().addActionListener(e -> model.clear());

        // Delete last digit
        view.getBtnDelete().addActionListener(e -> model.deleteLast());
    }
}