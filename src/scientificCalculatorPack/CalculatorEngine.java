package scientificCalculatorPack;

import java.util.Stack;

public class CalculatorEngine {
    // Constructor
    public CalculatorEngine() {
        // You can initialize any necessary variables or settings here.
    }

    // Method to evaluate a mathematical expression
    public double evaluateExpression(String expression) {
        try {
            // Remove any spaces from the expression
            expression = expression.replaceAll("\\s", "");

            // Check if the expression is empty
            if (expression.isEmpty()) {
                return 0.0; // Empty expression should be treated as 0
            }

            // Check if the expression is a single number (positive or negative)
            if (expression.matches("-?\\d+(\\.\\d+)?")) {
                return Double.parseDouble(expression);
            }

            // Create a stack to store operands
            Stack<Double> operandStack = new Stack<>();

            // Create a stack to store operators
            Stack<Character> operatorStack = new Stack<>();

            // Flag to indicate whether the next operator should be treated as unary minus
            boolean unaryMinus = true;

            // Process each character in the expression
            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);

                if (Character.isDigit(c) || (unaryMinus && c == '-' && (i == 0 || !Character.isDigit(expression.charAt(i - 1))))) {
                    // If the character is a digit or a unary minus, parse the number
                    StringBuilder numBuilder = new StringBuilder();
                    while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        numBuilder.append(expression.charAt(i));
                        i++;
                    }
                    i--; // Move the index back by one
                    double num = Double.parseDouble(numBuilder.toString());
                    operandStack.push(num);
                    unaryMinus = false; // Reset the unary minus flag
                } else if (c == '(') {
                    // If it's an opening parenthesis, push it onto the operator stack
                    operatorStack.push(c);
                    unaryMinus = true; // Unary minus is allowed after an opening parenthesis
                } else if (c == ')') {
                    // If it's a closing parenthesis, evaluate the expression inside the parentheses
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        evaluate(operandStack, operatorStack);
                    }
                    operatorStack.pop(); // Pop the opening parenthesis
                    unaryMinus = false; // Reset the unary minus flag
                } else if (isOperator(c)) {
                    // If it's an operator, evaluate higher precedence operators on top of the stack
                    while (!operatorStack.isEmpty() && precedence(c) <= precedence(operatorStack.peek())) {
                        evaluate(operandStack, operatorStack);
                    }
                    operatorStack.push(c);
                    unaryMinus = true; // Unary minus is allowed after an operator
                }
            }

            // Evaluate any remaining operators on the stack
            while (!operatorStack.isEmpty()) {
                evaluate(operandStack, operatorStack);
            }

            // The result should be the only item on the operand stack
            if (operandStack.size() == 1) {
                return operandStack.pop();
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN; // Return NaN for invalid expressions
        }
    }


    // Method to evaluate an operator and perform the corresponding operation
    private void evaluate(Stack<Double> operandStack, Stack<Character> operatorStack) {
        if (operandStack.size() >= 2 && !operatorStack.isEmpty()) {
            double operand2 = operandStack.pop();
            double operand1 = operandStack.pop();
            char operator = operatorStack.pop();

            switch (operator) {
                case '+':
                    operandStack.push(operand1 + operand2);
                    break;
                case '-':
                    operandStack.push(operand1 - operand2);
                    break;
                case '*':
                    operandStack.push(operand1 * operand2);
                    break;
                case '/':
                    if (operand2 != 0) {
                        operandStack.push(operand1 / operand2);
                    } else {
                        throw new ArithmeticException("Division by zero");
                    }
                    break;
                // Add more operators as needed
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    // Helper method to check if a character is an operator
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Helper method to determine operator precedence
    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            // Add more operators and precedence levels as needed
            default:
                return 0; // Default precedence for unknown operators
        }
    }
}
