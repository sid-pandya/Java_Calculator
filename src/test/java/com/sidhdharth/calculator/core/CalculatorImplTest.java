package com.sidhdharth.calculator.core;

import com.sidhdharth.calculator.api.Calculator;
import com.sidhdharth.calculator.config.CalculatorConfig;
import com.sidhdharth.calculator.exception.DivisionByZeroException;
import com.sidhdharth.calculator.exception.InvalidOperationException;
import com.sidhdharth.calculator.exception.ModuloByZeroException;
import com.sidhdharth.calculator.exception.NullOperandException;
import com.sidhdharth.calculator.operation.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorImplTest {

    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = CalculatorConfig.createCalculator();
    }

    @Test
    void testBasicOperations() {
        assertAll(
                () -> assertEquals(5.0, calc.calculate(OperationType.ADD, 2, 3)),
                () -> assertEquals(4.0, calc.calculate(OperationType.SUBTRACT, 7, 3)),
                () -> assertEquals(12.0, calc.calculate(OperationType.MULTIPLY, 4, 3)),
                () -> assertEquals(2.5, calc.calculate(OperationType.DIVIDE, 5, 2)),
                () -> assertEquals(1.0, calc.calculate(OperationType.MODULO, 5, 2)));
    }

    @Test
    void testZeroEdgeCases() {
        assertAll(
                () -> assertThrows(DivisionByZeroException.class, () -> calc.calculate(OperationType.DIVIDE, 5, 0)),
                () -> assertThrows(ModuloByZeroException.class, () -> calc.calculate(OperationType.MODULO, 5, 0)),
                () -> assertEquals(0.0, calc.calculate(OperationType.MULTIPLY, 0, Double.MAX_VALUE)),
                () -> assertEquals(0.0, calc.calculate(OperationType.MULTIPLY, Double.MAX_VALUE, 0)),
                () -> assertEquals(0.0, calc.calculate(OperationType.MODULO, 0, 5)),
                () -> assertEquals(0.0, calc.calculate(OperationType.ADD, 0, 0)),
                () -> assertEquals(0.0, calc.calculate(OperationType.SUBTRACT, 0, 0)));
    }

    @Test
    void testNullAndInvalidInputs() {
        assertAll(
                () -> assertThrows(NullOperandException.class, () -> calc.calculate(null, 1, 2)),
                () -> assertThrows(NullOperandException.class, () -> calc.calculate(OperationType.ADD, null, 2)),
                () -> assertThrows(NullOperandException.class, () -> calc.calculate(OperationType.ADD, 1, null)),
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol("")),
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol(" ")),
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol("++")),
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol("add")),
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol("123")));
    }

    @Test
    void testNegativeNumberOperations() {
        assertAll(
                () -> assertEquals(-1.0, calc.calculate(OperationType.ADD, -2, 1)),
                () -> assertEquals(-3.0, calc.calculate(OperationType.SUBTRACT, -2, 1)),
                () -> assertEquals(-2.0, calc.calculate(OperationType.MULTIPLY, -2, 1)),
                () -> assertEquals(-2.0, calc.calculate(OperationType.DIVIDE, -2, 1)),
                () -> assertEquals(0.0, calc.calculate(OperationType.MODULO, -2, 1)),
                () -> assertEquals(2.0, calc.calculate(OperationType.MULTIPLY, -2, -1)),
                () -> assertEquals(2.0, calc.calculate(OperationType.DIVIDE, -2, -1)),
                () -> assertEquals(-1.0, calc.calculate(OperationType.MODULO, -2, -1)));
    }

    @Test
    void testDecimalPrecision() {
        assertAll(
                () -> assertEquals(3.14, calc.calculate(OperationType.ADD, 1.57, 1.57)),
                () -> assertEquals(0.5, calc.calculate(OperationType.SUBTRACT, 1.5, 1.0)),
                () -> assertEquals(2.5, calc.calculate(OperationType.MULTIPLY, 1.25, 2.0)),
                () -> assertEquals(0.5, calc.calculate(OperationType.DIVIDE, 1.0, 2.0)),
                () -> assertEquals(0.5, calc.calculate(OperationType.MODULO, 1.5, 1.0)),
                () -> assertEquals(0.3333333333333333, calc.calculate(OperationType.DIVIDE, 1.0, 3.0)),
                () -> assertEquals(0.0000000000000001,
                        calc.calculate(OperationType.MULTIPLY, 0.0000000000000001, 1.0)));
    }

    @Test
    void testExtremeValueOperations() {
        double maxValue = Double.MAX_VALUE;
        double minValue = Double.MIN_VALUE;
        double halfMax = maxValue / 2;
        double epsilon = Double.MIN_NORMAL;

        assertAll(
                () -> assertEquals(maxValue, calc.calculate(OperationType.ADD, maxValue, 0)),
                () -> assertEquals(maxValue, calc.calculate(OperationType.MULTIPLY, halfMax, 2)),
                () -> assertEquals(0.0, calc.calculate(OperationType.SUBTRACT, maxValue, maxValue)),
                () -> assertEquals(1.0, calc.calculate(OperationType.DIVIDE, maxValue, maxValue)),
                () -> assertEquals(0.0, calc.calculate(OperationType.MODULO, maxValue, maxValue)),
                () -> assertEquals(minValue, calc.calculate(OperationType.MULTIPLY, minValue, 1)),
                () -> assertEquals(epsilon, calc.calculate(OperationType.MULTIPLY, epsilon, 1)),
                () -> assertThrows(ArithmeticException.class,
                        () -> calc.calculate(OperationType.MULTIPLY, maxValue, 2)),
                () -> assertEquals(Double.POSITIVE_INFINITY, calc.calculate(OperationType.DIVIDE, maxValue, minValue)));
    }

    @Test
    void testSpecialNumberOperations() {
        assertAll(
                () -> assertTrue(Double.isNaN(calc.calculate(OperationType.DIVIDE, 0.0, 0.0).doubleValue())),
                () -> assertEquals(Double.POSITIVE_INFINITY, calc.calculate(OperationType.DIVIDE, 1.0, 0.0)),
                () -> assertEquals(Double.NEGATIVE_INFINITY, calc.calculate(OperationType.DIVIDE, -1.0, 0.0)),
                () -> assertTrue(
                        Double.isInfinite(calc.calculate(OperationType.MULTIPLY, Double.MAX_VALUE, 2).doubleValue())),
                () -> assertEquals(0.0, calc.calculate(OperationType.MULTIPLY, Double.MIN_VALUE, 0.0)));
    }
}