package com.sidhdharth.calculator.exception;

public class InvalidInputException extends CalculatorException {
    public InvalidInputException(String input) {
        super("Invalid input: " + input);
    }
}