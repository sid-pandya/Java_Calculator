package com.sidhdharth.calculator.exception;

public class NullOperandException extends CalculatorException {
    public NullOperandException() {
        super("Operand cannot be null");
    }
}