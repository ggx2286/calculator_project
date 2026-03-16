package calculator;

/**
 * Observer interface for the Observer Pattern.
 * The View implements this to receive updates from the Model.
 */
public interface CalculatorObserver {
    void update(String displayValue);
}