package scientificCalculatorPack;

import java.util.Stack;

public class CalculatorEngine {
    public CalculatorEngine() {
    }

    public double evaluateExpression(String expression) {
        try {
            expression = expression.replaceAll("\\s", "");

            if (expression.isEmpty()) {
                return 0.0; 
            }

            if (expression.matches("-?\\d+(\\.\\d+)?")) {
                return Double.parseDouble(expression);
            }

            Stack<Double> operandStack = new Stack<>();

            Stack<Character> operatorStack = new Stack<>();

            boolean unaryMinus = true;

            for (int i = 0; i < expression.length(); i++) {
                char c = expression.charAt(i);

                if (Character.isDigit(c) || (unaryMinus && c == '-' && (i == 0 || Character.isDigit(expression.charAt(i - 1))))) {

                    StringBuilder numBuilder = new StringBuilder();
                    while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        numBuilder.append(expression.charAt(i));
                        i++;
                    }
                    i--; 
                    double num = Double.parseDouble(numBuilder.toString());
                    operandStack.push(num);
                    unaryMinus = false; 
                } else if (c == '(') {
                    operatorStack.push(c);
                    unaryMinus = true; 
                } else if (c == ')') {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        evaluate(operandStack, operatorStack);
                    }
                    operatorStack.pop(); 
                    unaryMinus = false; 
                } else if (isOperator(c)) {
                    while (!operatorStack.isEmpty() && precedence(c) <= precedence(operatorStack.peek())) {
                        evaluate(operandStack, operatorStack);
                    }
                    operatorStack.push(c);
                    unaryMinus = true; 
                }
            }

            while (!operatorStack.isEmpty()) {
                evaluate(operandStack, operatorStack);
            }

            if (operandStack.size() == 1) {
                return operandStack.pop();
            } else {
                throw new IllegalArgumentException("Invalid expression");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN; 
        }
    }

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
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0; 
        }
    }
}
