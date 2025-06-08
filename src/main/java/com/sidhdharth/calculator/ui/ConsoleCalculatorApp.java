package com.sidhdharth.calculator.ui;

import com.sidhdharth.calculator.api.Calculator;
import com.sidhdharth.calculator.chain.OperationChainBuilder;
import com.sidhdharth.calculator.config.CalculatorConfig;
import com.sidhdharth.calculator.operation.OperationType;

import java.util.Scanner;

public class ConsoleCalculatorApp {
    public static void main(String[] args) {
        Calculator calc = CalculatorConfig.createCalculator();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Calculator ===");
        while (true) {
            System.out.println("\nSelect mode:");
            System.out.println("1) Single operation");
            System.out.println("2) Chained operations");
            System.out.println("3) Exit");
            System.out.print("> ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> singleOperation(calc, sc);
                case "2" -> chainedOperations(calc, sc);
                case "3" -> {
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Wrong choice.");
            }
        }
    }

    private static void singleOperation(Calculator calc, Scanner sc) {
        try {
            System.out.println("Please enter the first number: ");
            double num1 = Double.parseDouble(sc.nextLine());

            System.out.print("Enter operator (" + OperationType.allSymbols() + "): ");
            String opInput = sc.nextLine().trim();
            OperationType op = OperationType.fromSymbol(opInput);

            System.out.println("Please enter the second number: ");
            double num2 = Double.parseDouble(sc.nextLine());

            Number result = calc.calculate(op, num1, num2);
            System.out.println("The result is: " + result.toString());
        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    private static void chainedOperations(Calculator calc, Scanner sc) {
        try {
            System.out.println("Please enter the first number: ");
            double initialValue = Double.parseDouble(sc.nextLine());
            OperationChainBuilder chain = calc.start(initialValue);
            double currentResult = initialValue;
            System.out.println("Current result: " + currentResult);

            while (true) {
                System.out.print("Enter operation (" + OperationType.allSymbols() + ") or 'end' to finish: ");
                String opInput = sc.nextLine().trim();
                if ("end".equalsIgnoreCase(opInput))
                    break;
                if (opInput.isEmpty()) {
                    System.out.println("Error: Empty input is not allowed. Returning final result.");
                    break;
                }

                OperationType op = OperationType.fromSymbol(opInput);

                System.out.println("Please enter the second number: ");
                double operand = Double.parseDouble(sc.nextLine());

                chain.chain(op, operand);
                currentResult = chain.getResult().doubleValue();
                System.out.println("Current result: " + currentResult);
            }
            System.out.println("The final result is: " + chain.getResult());
        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
}
