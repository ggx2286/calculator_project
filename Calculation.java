package calculator;

import java.util.Objects;

/**
 * Immutable ADT representing a single mathematical calculation.
 *
 * RI:
 *   - firstNumber must be a valid numeric value
 *   - secondNumber must be a valid numeric value
 *   - operation != null
 *   - operation must be one of {+, -, *, /}
 *   - division by zero is not allowed
 *
 * AF:
 *   Represents a single mathematical calculation
 *   where 'firstNumber' and 'secondNumber' are
 *   combined using the selected 'operation'
 *   to produce the result shown by the calculator.
 */
public final class Calculation {

    private final double firstNumber;
    private final double secondNumber;
    private final String operation;
    private final double result;

    public Calculation(double firstNumber, double secondNumber, String operation) {
        if (operation == null) throw new IllegalArgumentException("Operation cannot be null");
        if (!operation.equals("+") && !operation.equals("-") &&
            !operation.equals("*") && !operation.equals("/")) {
            throw new IllegalArgumentException("Invalid operation: " + operation);
        }
        if (operation.equals("/") && secondNumber == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        this.firstNumber  = firstNumber;
        this.secondNumber = secondNumber;
        this.operation    = operation;
        this.result       = compute(firstNumber, secondNumber, operation);
    }

    private double compute(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default:  throw new IllegalArgumentException("Unknown operation");
        }
    }

    public double getFirstNumber()  { return firstNumber; }
    public double getSecondNumber() { return secondNumber; }
    public String getOperation()    { return operation; }
    public double getResult()       { return result; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Calculation)) return false;
        Calculation other = (Calculation) obj;
        return Double.compare(this.firstNumber,  other.firstNumber)  == 0 &&
               Double.compare(this.secondNumber, other.secondNumber) == 0 &&
               this.operation.equals(other.operation) &&
               Double.compare(this.result, other.result) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstNumber, secondNumber, operation, result);
    }

    @Override
    public String toString() {
        return firstNumber + " " + operation + " " + secondNumber + " = " + result;
    }
}