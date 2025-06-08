package com.sidhdharth.calculator.operation;

import com.sidhdharth.calculator.exception.DivisionByZeroException;
import com.sidhdharth.calculator.exception.InvalidOperationException;
import com.sidhdharth.calculator.exception.ModuloByZeroException;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

public enum OperationType implements Operation {
    // Addition operation

    ADD((num1, num2) -> {
        double result = num1.doubleValue() + num2.doubleValue();
        return result == -0.0 ? 0.0 : result;
    }, "+"),

    // Subtraction operation
    SUBTRACT((num1, num2) -> num1.doubleValue() - num2.doubleValue(), "-"),

    // Multiplication operation
    MULTIPLY((num1, num2) -> num1.doubleValue() * num2.doubleValue(), "*", "x", "X"),

    // Modulo operation
    MODULO((num1, num2) -> {
        if (num2.doubleValue() == 0) {
            throw new ModuloByZeroException();
        }
        double result = num1.doubleValue() % num2.doubleValue();
        return result == -0.0 ? 0.0 : result;
    }, "%"),

    // Division operation
    DIVIDE((num1, num2) -> {
        if (num2.doubleValue() == 0) {
            throw new DivisionByZeroException();
        }
        return num1.doubleValue() / num2.doubleValue();
    }, "/");

    private final BiFunction<Number, Number, Number> operation;
    private final Set<String> symbols;

    OperationType(BiFunction<Number, Number, Number> operation, String... symbols) {
        this.operation = operation;
        this.symbols = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(symbols)));
    }

    @Override
    public Number apply(Number num1, Number num2) {
        return operation.apply(num1, num2);
    }

    public static OperationType fromSymbol(String input) {
        String sym = input.trim();
        for (OperationType op : values()) {
            if (op.symbols.contains(sym)) {
                return op;
            }
        }
        throw new InvalidOperationException(input);
    }

    public static @NotNull String allSymbols() {
        List<String> all = new ArrayList<>();
        for (OperationType op : values()) {
            all.addAll(op.symbols);
        }
        return String.join(", ", all);
    }
}
