package scientificCalculatorPack;


//This class typically serves as the entry point of the application. 
public class Main {
    public static void main(String[] args) {
        // Create and display the calculator UI
        javax.swing.SwingUtilities.invokeLater(() -> {
            CalculatorUI calculator = new CalculatorUI();
        });
    }
}
