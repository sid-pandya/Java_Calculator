package com.sidhdharth.calculator.exception;

public class ModuloByZeroException extends CalculatorException {
    public ModuloByZeroException() {
        super("Cannot perform modulo by zero");
    }
}