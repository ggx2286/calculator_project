package calculator;

import java.util.ArrayList;
import java.util.List;

public class CalculatorModel {

    private List<CalculatorObserver> observers = new ArrayList<>();

    private String currentInput = "";
    private double firstNumber  = 0;
    private String operation    = null;
    private boolean newInput    = false;
    private String expression   = ""; // ← جديد: يخزن كل العملية

    public void addObserver(CalculatorObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (CalculatorObserver o : observers) {
            o.update(expression.isEmpty() ? currentInput : expression);
        }
    }

    public void appendDigit(String digit) {
        if (newInput) { currentInput = ""; newInput = false; }
        if (digit.equals(".") && currentInput.contains(".")) return;
        if (currentInput.equals("0") && !digit.equals(".")) currentInput = "";
        currentInput += digit;

        // حدّث العملية الكاملة
        if (operation != null) {
            expression = format(firstNumber) + " " + operation + " " + currentInput;
        } else {
            expression = currentInput;
        }
        notifyObservers();
    }

    public void setOperation(String op) {
        if (!currentInput.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
        }
        operation  = op;
        expression = format(firstNumber) + " " + op + " ";
        newInput   = true;
        notifyObservers();
    }

    public void calculate() {
        if (operation == null || currentInput.isEmpty()) return;
        try {
            double secondNumber = Double.parseDouble(currentInput);
            Calculation calc    = new Calculation(firstNumber, secondNumber, operation);
            double result       = calc.getResult();

            // اعرض: 5 + 3 = 8
            expression   = format(firstNumber) + " " + operation + " " + format(secondNumber) + " = " + format(result);
            currentInput = format(result);
            operation    = null;
            newInput     = true;
            notifyObservers();
        } catch (ArithmeticException e) {
            expression   = "Error: Div/0";
            currentInput = "Error";
            notifyObservers();
        }
    }

    public void clear() {
        currentInput = "";
        firstNumber  = 0;
        operation    = null;
        newInput     = false;
        expression   = "";
        notifyObservers();
    }

    public void deleteLast() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            if (operation != null) {
                expression = format(firstNumber) + " " + operation + " " + currentInput;
            } else {
                expression = currentInput;
            }
            notifyObservers();
        }
    }

    // مساعد: يحذف .0 من الأرقام الصحيحة
    private String format(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    public String getCurrentInput() { return currentInput; }
}