package scientificCalculatorPack;

public class Main {
    public static void main(String[] args) {
        // Create and display the calculator UI
        javax.swing.SwingUtilities.invokeLater(() -> {
            CalculatorUI calculator = new CalculatorUI();
        });
    }
}
