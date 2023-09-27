// Define the package name for the scientific calculator application
package scientificCalculatorPack;

// The main class for the application
public class Main {
    public static void main(String[] args) {
        // Use SwingUtilities to ensure that GUI-related tasks run on the Event Dispatch Thread (EDT)
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Create an instance of the CalculatorUI class
            CalculatorUI calculator = new CalculatorUI();
        });
    }
}
