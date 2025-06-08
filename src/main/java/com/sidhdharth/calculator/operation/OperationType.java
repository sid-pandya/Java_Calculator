package com.sidhdharth.calculator.operation;

import com.sidhdharth.calculator.exception.DivisionByZeroException;
import com.sidhdharth.calculator.exception.InvalidOperationException;
import com.sidhdharth.calculator.exception.ModuloByZeroException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Enum representing the available calculator operations.
 * Each operation has associated symbols and implements the Operation interface.
 */
public enum OperationType implements Operation {
    /**
     * Addition operation
     */
    ADD((num1, num2) -> {
        double result = num1.doubleValue() + num2.doubleValue();
        return result == -0.0 ? 0.0 : result;
    }, "+"),

    /**
     * Subtraction operation
     */
    SUBTRACT((num1, num2) -> num1.doubleValue() - num2.doubleValue(), "-"),

    /**
     * Multiplication operation
     */
    MULTIPLY((num1, num2) -> num1.doubleValue() * num2.doubleValue(), "*", "x", "X"),

    /**
     * Modulo operation
     */
    MODULO((num1, num2) -> {
        if (num2.doubleValue() == 0) {
            throw new ModuloByZeroException();
        }
        double result = num1.doubleValue() % num2.doubleValue();
        return result == -0.0 ? 0.0 : result;
    }, "%"),

    /**
     * Division operation
     */
    DIVIDE((num1, num2) -> {
        if (num2.doubleValue() == 0) {
            throw new DivisionByZeroException();
        }
        return num1.doubleValue() / num2.doubleValue();
    }, "/");

    private final BiFunction<Number, Number, Number> operation;
    private final Set<String> symbols;

    /**
     * Constructor for OperationType
     * 
     * @param operation The function that performs the operation
     * @param symbols   The symbols that represent this operation
     */
    OperationType(BiFunction<Number, Number, Number> operation, String... symbols) {
        this.operation = operation;
        // Use a Set for fast lookups; compare raw String equality
        this.symbols = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(symbols)));
    }

    @Override
    public Number apply(Number num1, Number num2) {
        return operation.apply(num1, num2);
    }

    /**
     * Converts a symbol string to the corresponding OperationType
     * 
     * @param input The symbol string to convert
     * @return The corresponding OperationType
     * @throws InvalidOperationException if the symbol is not recognized
     */
    public static OperationType fromSymbol(String input) {
        String sym = input.trim();
        for (OperationType op : values()) {
            if (op.symbols.contains(sym)) {
                return op;
            }
        }
        throw new InvalidOperationException(input);
    }

    /**
     * Returns a comma-separated list of all available operation symbols
     * 
     * @return String containing all operation symbols
     */
    public static @NotNull String allSymbols() {
        List<String> all = new ArrayList<>();
        for (OperationType op : values()) {
            all.addAll(op.symbols);
        }
        return String.join(", ", all);
    }
}
