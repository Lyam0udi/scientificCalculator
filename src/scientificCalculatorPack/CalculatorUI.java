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
            
            // Handle different button actions based on the button's text
            if (buttonText.equals("=")) {
            	// Calculate the result when "=" is pressed
                String expression = displayField.getText();
                double result = calculatorEngine.evaluateExpression(expression);
                displayField.setText(Double.toString(result));
            } else if (buttonText.equals("CE")) {
            	// Clear the display when "CE" is pressed
                displayField.setText("");
            } else if (buttonText.equals("Theme Mode")) {
            	// Toggle dark mode when "Theme Mode" is pressed
                isDarkMode = !isDarkMode;
                updateTheme();
            } else if (buttonText.equals("On")) {
            	// Turn the calculator on
                isCalculatorOn = true;
                setButtonsEnabled(true);
            } else if (buttonText.equals("OFF")) {
            	// Turn the calculator off
                isCalculatorOn = false;
                setButtonsEnabled(false);
            } else if (buttonText.equals("+/-")) {
            	// Negate the current value
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double currentValue = Double.parseDouble(currentText);
                    double negatedValue = (-1) * currentValue;
                    displayField.setText(Double.toString(negatedValue));
                }
            } else if (buttonText.equals("Delete")) {
            	// Delete the last character in the display
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    String newText = currentText.substring(0, currentText.length() - 1);
                    displayField.setText(newText);
                }
            } else if (buttonText.equals("e")) {
            	// Append the mathematical constant "e" to the display
                displayField.setText(displayField.getText() + Math.E);
            } else if (buttonText.equals("π")) {
            	 // Append the mathematical constant "π" to the display
                displayField.setText(displayField.getText() + Math.PI);
            } else if (buttonText.equals("2nd")) {
            	// Calculate the square of a number
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double exponent = Double.parseDouble(currentText);
                    double result = Math.pow(2, exponent);
                    displayField.setText(Double.toString(result));
                }
            } else if (buttonText.equals("1/x")) {
            	// Calculate the reciprocal of a number
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
            	// Calculate the square of the current value
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    double square = number * number;
                    displayField.setText(Double.toString(square));
                }
            } else if (buttonText.equals("|x|")) {
            	// Calculate the absolute value of the current value
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    double absoluteValue = Math.abs(number);
                    displayField.setText(Double.toString(absoluteValue));
                }
            } else if (buttonText.equals("x^y")) {
            	// Calculate the result of x raised to the power of y
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
            } else if (buttonText.equals("ln")) {
            	// Calculate the natural logarithm of the current value
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
            	// Calculate the factorial of a number
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
            	// Calculate the exponential function of the current value
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    double expValue = Math.exp(number);
                    displayField.setText(Double.toString(expValue));
                }
            } else if (buttonText.equals("√")) {
            	// Calculate the square root of the current value
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
            	// Calculate the percentage of the current value
                String currentText = displayField.getText();
                if (!currentText.isEmpty()) {
                    double number = Double.parseDouble(currentText);
                    double percentageValue = number / 100.0;
                    displayField.setText(Double.toString(percentageValue));
                }
            } else {
            	// Append the button's text to the display
                displayField.setText(displayField.getText() + buttonText);
            }
        }
    }

    private void updateTheme() {
        // Check if the dark mode is enabled
        if (isDarkMode) {
            // Dark Mode Theme
            setDarkModeTheme();
        } else {
            // Light Mode Theme
            setLightModeTheme();
        }
        
        // Repaint the components to apply the theme changes
        repaint();
    }

    private void setDarkModeTheme() {
        // Set the background and foreground colors for the display field
        displayField.setBackground(Color.BLACK);
        displayField.setForeground(Color.WHITE);
        
        // Set the background color of the main content pane
        getContentPane().setBackground(Color.DARK_GRAY);
        
        // Set the background color for the button panel
        buttonPanel.setBackground(Color.DARK_GRAY);
        
        // Set the button colors in the button panel
        setButtonColors(Color.BLACK, Color.WHITE);
    }

    private void setLightModeTheme() {
        // Set the background and foreground colors for the display field
        displayField.setBackground(Color.WHITE);
        displayField.setForeground(Color.BLACK);
        
        // Set the background color of the main content pane
        getContentPane().setBackground(Color.LIGHT_GRAY);
        
        // Set the background color for the button panel
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        
        // Set the button colors in the button panel
        setButtonColors(Color.WHITE, Color.BLACK);
    }

    private void setButtonColors(Color backgroundColor, Color foregroundColor) {
        // Set the background and foreground colors for all buttons in the button panel
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setBackground(backgroundColor);
                button.setForeground(foregroundColor);
            }
        }
    }

    
    private void setButtonsEnabled(boolean enabled) {
        // Iterate through components in the button panel
        for (Component component : buttonPanel.getComponents()) {
            // Check if the component is a JButton
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                // Check if the button's text is not "On"
                if (!button.getText().equals("On")) {
                    // Set the enabled state of the button
                    button.setEnabled(enabled);
                }
            }
        }
    }

}
