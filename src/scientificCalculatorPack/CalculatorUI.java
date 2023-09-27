package scientificCalculatorPack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CalculatorUI extends JFrame {
    private JTextField displayField;
    
    // Flags for calculator state
    private boolean isCalculatorOn = true; 
    private boolean isDarkMode = true;
    
    // Panel for calculator buttons
    private JPanel buttonPanel;
    
    // Calculator engine for evaluating expressions
    private CalculatorEngine calculatorEngine = new CalculatorEngine();

    public CalculatorUI() {
        setTitle("Scientific Calculator");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and configure the display field
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(Color.BLACK); 
        displayField.setForeground(Color.WHITE); 
        displayField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        getContentPane().add(displayField, BorderLayout.NORTH);
        
        // Create the button panel for calculator buttons
        buttonPanel = new JPanel(new GridBagLayout());

        // Button labels
        String[] buttonLabels = {
        	"CE","On", "OFF", "Theme Mode",
            "2nd", "π", "e", "Delete",
            "x^y", "|x|", "x²", "1/x",
            "√","exp", "n!", "ln",
            "(", ")", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        // GridBagLayout configuration
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        int column = 0;
        int row = 0;

        // Create and configure buttons
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            button.addActionListener(new ButtonClickListener());

            gbc.gridx = column;
            gbc.gridy = row;
            buttonPanel.add(button, gbc);

            column++;
            if (column > 3) {
                column = 0;
                row++;
            }
        }
        
        // Add button panel to the center of the frame
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        // Initialize the theme and button state
        updateTheme();
        setButtonsEnabled(isCalculatorOn);

        setVisible(true);
    }

    // ActionListener for calculator buttons
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            if (buttonText.equals("=")) {
                String expression = displayField.getText();
                double result = calculatorEngine.evaluateExpression(expression);
                displayField.setText(Double.toString(result));
            } else if (buttonText.equals("CE")) {
                displayField.setText("");
            } else if (buttonText.equals("Theme Mode")) {
                isDarkMode = !isDarkMode;
                updateTheme();
            } else if (buttonText.equals("On")) {
                isCalculatorOn = true;
                setButtonsEnabled(true);
            } else if (buttonText.equals("OFF")) {
                isCalculatorOn = false;
                setButtonsEnabled(false);
            } else if (buttonText.equals("+/-")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double currentValue = Double.parseDouble(currentText);
                    double negatedValue = (-1) * currentValue;
                    displayField.setText(Double.toString(negatedValue));
                }
            } else if (buttonText.equals("Delete")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    String newText = currentText.substring(0, currentText.length() - 1);
                    displayField.setText(newText);
                }
            } else if (buttonText.equals("e")) {
                displayField.setText(displayField.getText() + Math.E);
            } else if (buttonText.equals("π")) {
                displayField.setText(displayField.getText() + Math.PI);
            } else if (buttonText.equals("2nd")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double exponent = Double.parseDouble(currentText);
                    double result = Math.pow(2, exponent);
                    displayField.setText(Double.toString(result));
                }
            } else if (buttonText.equals("1/x")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    if (number != 0) {
                        double reciprocal = 1.0 / number;
                        displayField.setText(Double.toString(reciprocal));
                    } else {
                        displayField.setText("Error: Division by zero");
                    }
                }
            } else if (buttonText.equals("x²")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    double square = number * number;
                    displayField.setText(Double.toString(square));
                }
            } else if (buttonText.equals("|x|")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    double absoluteValue = Math.abs(number);
                    displayField.setText(Double.toString(absoluteValue));
                }
            } else if (buttonText.equals("x^y")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    String yText = JOptionPane.showInputDialog("Enter the exponent (y):");
                    if (yText != null) {
                        try {
                            double base = Double.parseDouble(currentText);
                            double exponent = Double.parseDouble(yText);
                            double result = Math.pow(base, exponent);
                            displayField.setText(Double.toString(result));
                        } catch (NumberFormatException ex) {
                            displayField.setText("Error: Invalid input");
                        }
                    }
                }
            }else if (buttonText.equals("ln")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    if (number > 0) { 
                        double lnValue = Math.log(number);
                        displayField.setText(Double.toString(lnValue));
                    } else {
                        displayField.setText("Error: ln of a non-positive number");
                    }
                }
            } else if (buttonText.equals("n!")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    try {
                        int number = Integer.parseInt(currentText);
                        if (number >= 0) {
                            int factorial = 1;
                            for (int i = 2; i <= number; i++) {
                                factorial *= i;
                            }
                            displayField.setText(Integer.toString(factorial));
                        } else {
                            displayField.setText("Error: Factorial of a negative number");
                        }
                    } catch (NumberFormatException ex) {
                        displayField.setText("Error: Invalid input");
                    }
                }
            } else if (buttonText.equals("exp")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    double expValue = Math.exp(number);
                    displayField.setText(Double.toString(expValue));
                }
            } else if (buttonText.equals("√")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    if (number >= 0) {
                        double sqrtValue = Math.sqrt(number);
                        displayField.setText(Double.toString(sqrtValue));
                    } else {
                        displayField.setText("Error: Square root of a negative number");
                    }
                }
            } else if (buttonText.equals("%")) {
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    double percentageValue = number / 100.0;
                    displayField.setText(Double.toString(percentageValue));
                }
            } else {
                displayField.setText(displayField.getText() + buttonText);
            }
        }
    }

    private void updateTheme() {
        if (isDarkMode) {
            displayField.setBackground(Color.BLACK);
            displayField.setForeground(Color.WHITE);
            getContentPane().setBackground(Color.DARK_GRAY);

            buttonPanel.setBackground(Color.DARK_GRAY);
            for (Component component : buttonPanel.getComponents()) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setBackground(Color.BLACK);
                    button.setForeground(Color.WHITE);
                }
            }
        } else {
            displayField.setBackground(Color.WHITE);
            displayField.setForeground(Color.BLACK);
            getContentPane().setBackground(Color.LIGHT_GRAY);

            buttonPanel.setBackground(Color.LIGHT_GRAY);
            for (Component component : buttonPanel.getComponents()) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                }
            }
        }
        repaint();
    }
    
    private void setButtonsEnabled(boolean enabled) {
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (!button.getText().equals("On")) { 
                    button.setEnabled(enabled);
                }
            }
        }
    }
}
