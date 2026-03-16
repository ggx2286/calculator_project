package calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Model (Subject in Observer Pattern).
 * Stores calculator state and notifies observers (View) on change.
 */
public class CalculatorModel {

    // Observer pattern - list of observers
    private List<CalculatorObserver> observers = new ArrayList<>();

    private String currentInput = "";
    private double firstNumber  = 0;
    private String operation    = null;
    private boolean newInput    = false;

    // ── Observer Pattern ──────────────────────────────────────────
    public void addObserver(CalculatorObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (CalculatorObserver o : observers) {
            o.update(currentInput);
        }
    }

    // ── Input Handling ────────────────────────────────────────────
    public void appendDigit(String digit) {
        if (newInput) { currentInput = ""; newInput = false; }
        if (digit.equals(".") && currentInput.contains(".")) return;
        if (currentInput.equals("0") && !digit.equals(".")) currentInput = "";
        currentInput += digit;
        notifyObservers();
    }

    public void setOperation(String op) {
        if (!currentInput.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
        }
        operation = op;
        newInput  = true;
        notifyObservers();
    }

    public void calculate() {
        if (operation == null || currentInput.isEmpty()) return;
        try {
            double secondNumber = Double.parseDouble(currentInput);
            Calculation calc = new Calculation(firstNumber, secondNumber, operation);
            double result = calc.getResult();
            // Format: remove .0 if whole number
            if (result == (long) result) {
                currentInput = String.valueOf((long) result);
            } else {
                currentInput = String.valueOf(result);
            }
            operation = null;
            newInput  = true;
            notifyObservers();
        } catch (ArithmeticException e) {
            currentInput = "Error: Div/0";
            notifyObservers();
        }
    }

    public void clear() {
        currentInput = "";
        firstNumber  = 0;
        operation    = null;
        newInput     = false;
        notifyObservers();
    }

    public void deleteLast() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            notifyObservers();
        }
    }

    public String getCurrentInput() { return currentInput; }
}