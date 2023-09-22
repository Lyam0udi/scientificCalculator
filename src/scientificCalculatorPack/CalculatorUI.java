package scientificCalculatorPack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CalculatorUI extends JFrame {
    private JTextField displayField;

    public CalculatorUI() {
        setTitle("Scientific Calculator");
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the display field
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Segoe UI", Font.PLAIN, 36)); // Change font and size
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(Color.WHITE); // Set background color
        displayField.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border
        
        // Allocate 20% of the frame for the display
        getContentPane().add(displayField, BorderLayout.NORTH);
        
        // Create the button panel with a GridBagLayout for better alignment
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        // Define button labels
        String[] buttonLabels = {
            "2nd", "π", "e", "CE",
            "x²", "1/x", "√", "/",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "ln", "+/-", "0", ".", "=",
            "(", ")", "n!", "%",
            "x^y", "|x|", "exp", "mod",
            "On", "OFF", "Theme Mode"
        };

        // Create and add buttons to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        int column = 0;
        int row = 0;

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Change font and size
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

        getContentPane().add(buttonPanel, BorderLayout.CENTER); // Use the center for the buttons

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
                // Calculate and display result
                // You'll implement this logic here
            } else if (buttonText.equals("CE")) {
                // Clear the display
                displayField.setText("");
            } else {
                // Append the button text to the display
                displayField.setText(displayField.getText() + buttonText);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorUI());
    }
}
