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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import scientificCalculatorPack.CalculatorEngine;


// This class represents the graphical user interface (GUI) of the calculator
public class CalculatorUI extends JFrame {
    private JTextField displayField;

    // Set dark mode as the default theme
    private boolean isDarkMode = true;
    private JPanel buttonPanel;
    
    private CalculatorEngine calculatorEngine = new CalculatorEngine();

    public CalculatorUI() {
        setTitle("Scientific Calculator");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the display field
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(Color.BLACK); // Set the background color for dark mode
        displayField.setForeground(Color.WHITE); // Set the text color for dark mode
        displayField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        
        // Allocate 20% of the frame for the display
        getContentPane().add(displayField, BorderLayout.NORTH);
        
        // Create the button panel with a GridBagLayout for better alignment
        buttonPanel = new JPanel(new GridBagLayout());

        // Define button labels
        String[] buttonLabels = {
        	"CE","On", "OFF", "Theme Mode",
            "2nd", "π", "e", "%",
            "x^y", "|x|", "exp", "mod",
            "n!","(", ")", "ln",
            "x²", "1/x", "√", "/",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        // Create and add buttons to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        int column = 0;
        int row = 0;

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

        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        // Set the initial theme
        updateTheme();

        // Make the frame visible
        setVisible(true);
    }

    // ActionListener for all calculator buttons
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            // Handle button clicks here
            if (buttonText.equals("=")) {
                String expression = displayField.getText();
                double result = calculatorEngine.evaluateExpression(expression);
                displayField.setText(Double.toString(result));
            } else if (buttonText.equals("CE")) {
                // Clear the display
                displayField.setText("");
            } else if (buttonText.equals("Theme Mode")) {
                // Toggle theme mode
                isDarkMode = !isDarkMode;
                updateTheme();
            } else {
                // Append the button text to the display
                displayField.setText(displayField.getText() + buttonText);
            }
        }
    }


    // Method to update the UI based on the current theme mode
    private void updateTheme() {
        if (isDarkMode) {
            // Apply dark theme colors
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
            // Apply white theme colors
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
        // Repaint the UI to reflect the changes
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorUI());
    }
}
