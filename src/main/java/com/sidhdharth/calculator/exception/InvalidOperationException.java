package com.sidhdharth.calculator.exception;

public class InvalidOperationException extends CalculatorException {
    public InvalidOperationException(String operation) {
        super("Invalid operation: " + operation);
    }
}