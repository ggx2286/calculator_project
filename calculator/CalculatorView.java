package calculator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * View (Observer in Observer Pattern).
 * Displays the calculator UI and updates when Model notifies it.
 */
public class CalculatorView extends JFrame implements CalculatorObserver {

    private JTextField display;
    private JButton[] numberButtons   = new JButton[10];
    private JButton   btnAdd, btnSub, btnMul, btnDiv;
    private JButton   btnEquals, btnClear, btnDelete, btnDot;

   
 // Colors
    private static final Color BG_COLOR     = new Color(20,  0,   20);   // خلفية موف غامق
    private static final Color DISPLAY_BG   = new Color(10,  0,   10);   // شاشة موف أغمق
    private static final Color NUM_COLOR    = new Color(128, 0,   128);  // أزرار أرقام موف
    private static final Color OP_COLOR     = new Color(255, 20,  147);  // عمليات وردي
    private static final Color EQUALS_COLOR = new Color(220, 20,  60);   // يساوي أحمر
    private static final Color CLEAR_COLOR  = new Color(255, 105, 180);  // C وردي فاتح
    private static final Color DELETE_COLOR = new Color(199, 21,  133);  // حذف وردي غامق
    private static final Color TEXT_WHITE   = Color.WHITE;
    private static final Color TEXT_DARK    = new Color(20,  0,   20);
    
    public CalculatorView() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(380, 580);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(10, 10));

        initDisplay();
        initButtons();

        setVisible(true);
    }

    // ── Display ───────────────────────────────────────────────────
    private void initDisplay() {
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 28));
        display.setBackground(DISPLAY_BG);
        display.setForeground(TEXT_WHITE);
        display.setBorder(new EmptyBorder(20, 20, 20, 20));
        display.setPreferredSize(new Dimension(380, 100));
        add(display, BorderLayout.NORTH);
    }
    // ── Buttons ───────────────────────────────────────────────────
    private void initButtons() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 8, 8));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(8, 12, 12, 12));

        // Number buttons
        for (int i = 0; i <= 9; i++) {
            numberButtons[i] = makeButton(String.valueOf(i), NUM_COLOR, TEXT_WHITE);
        }

        btnAdd    = makeButton("+", OP_COLOR,    TEXT_DARK);
        btnSub    = makeButton("-", OP_COLOR,    TEXT_DARK);
        btnMul    = makeButton("×", OP_COLOR,    TEXT_DARK);
        btnDiv    = makeButton("÷", OP_COLOR,    TEXT_DARK);
        btnEquals = makeButton("=", EQUALS_COLOR, TEXT_DARK);
        btnClear  = makeButton("C",  CLEAR_COLOR,  TEXT_DARK);
        btnDelete = makeButton("⌫", DELETE_COLOR, TEXT_DARK);
        btnDot    = makeButton(".",  NUM_COLOR,    TEXT_WHITE);

        // Row 1: C  ⌫  ÷  ×
        panel.add(btnClear);
        panel.add(btnDelete);
        panel.add(btnDiv);
        panel.add(btnMul);

        // Row 2: 7  8  9  -
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(btnSub);

        // Row 3: 4  5  6  +
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(btnAdd);

        // Row 4: 1  2  3  =
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(btnEquals);

        // Row 5: .  0  (empty)  (empty) -- 0 spans 2
        JPanel bottomRow = new JPanel(new GridLayout(1, 4, 8, 8));
        bottomRow.setBackground(BG_COLOR);

        JButton btnZeroWide = makeButton("0", NUM_COLOR, TEXT_WHITE);
        btnZeroWide.setFont(new Font("Arial", Font.BOLD, 22));
        numberButtons[0] = btnZeroWide;

        bottomRow.add(btnDot);
        bottomRow.add(btnZeroWide);
        bottomRow.add(new JLabel()); // spacer
        bottomRow.add(new JLabel()); // spacer

        JPanel allButtons = new JPanel(new BorderLayout(8, 8));
        allButtons.setBackground(BG_COLOR);
        allButtons.add(panel, BorderLayout.CENTER);
        allButtons.add(bottomRow, BorderLayout.SOUTH);
        allButtons.setBorder(new EmptyBorder(0, 12, 12, 12));

        add(allButtons, BorderLayout.CENTER);
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 22));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Rounded feel via border
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bg.darker(), 1, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        return btn;
    }

    // ── Observer update ───────────────────────────────────────────
    @Override
    public void update(String displayValue) {
        display.setText(displayValue.isEmpty() ? "0" : displayValue);
    }

    // ── Getters for Controller ────────────────────────────────────
    public JButton getNumberButton(int n)  { return numberButtons[n]; }
    public JButton getBtnAdd()             { return btnAdd; }
    public JButton getBtnSub()             { return btnSub; }
    public JButton getBtnMul()             { return btnMul; }
    public JButton getBtnDiv()             { return btnDiv; }
    public JButton getBtnEquals()          { return btnEquals; }
    public JButton getBtnClear()           { return btnClear; }
    public JButton getBtnDelete()          { return btnDelete; }
    public JButton getBtnDot()             { return btnDot; }
}