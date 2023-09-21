package scientificCalculatorPack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {
    private static boolean darkMode = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Scientific Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        // Create a panel to hold the calculator buttons
        JPanel calculatorPanel = new JPanel();
        calculatorPanel.setLayout(new GridLayout(5, 4, 5, 5));

        // Create buttons for digits and operations
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            calculatorPanel.add(button);
        }

        // Create buttons for dark and light mode
        JButton darkModeButton = new JButton("Dark Mode");
        JButton lightModeButton = new JButton("Light Mode");

        // Create a panel to hold mode-switching buttons
        JPanel modePanel = new JPanel();
        modePanel.add(darkModeButton);
        modePanel.add(lightModeButton);

        // Add action listeners for mode-switching buttons
        darkModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                darkMode = true;
                updateUI(frame);
            }
        });

        lightModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                darkMode = false;
                updateUI(frame);
            }
        });

        // Add the panels to the frame
        frame.add(calculatorPanel, BorderLayout.CENTER);
        frame.add(modePanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static void updateUI(JFrame frame) {
        // Change the UI's appearance based on the current mode
        if (darkMode) {
            frame.getContentPane().setBackground(Color.BLACK);
            frame.getContentPane().setForeground(Color.WHITE);
        } else {
            frame.getContentPane().setBackground(Color.WHITE);
            frame.getContentPane().setForeground(Color.BLACK);
        }

        // Repaint the frame to reflect the changes
        frame.repaint();
    }
}
