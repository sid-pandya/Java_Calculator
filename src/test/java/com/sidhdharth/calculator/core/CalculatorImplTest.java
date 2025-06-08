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
        // Obtain the default implementation
        calc = CalculatorConfig.createCalculator();
    }

    @Test
    void testAddition() {
        Number result = calc.calculate(OperationType.ADD, 2, 3);
        assertEquals(5.0, result);
    }

    @Test
    void testSubtraction() {
        assertEquals(4.0, calc.calculate(OperationType.SUBTRACT, 7, 3));
    }

    @Test
    void testMultiplication() {
        assertEquals(12.0, calc.calculate(OperationType.MULTIPLY, 4, 3));
    }

    @Test
    void testDivision() {
        assertEquals(2.5, calc.calculate(OperationType.DIVIDE, 5, 2));
    }

    @Test
    void testModulo() {
        assertEquals(1.0, calc.calculate(OperationType.MODULO, 5, 2));
    }

    @Test
    void testDivideByZero() {
        assertThrows(DivisionByZeroException.class,
                () -> calc.calculate(OperationType.DIVIDE, 5, 0));
    }

    @Test
    void testModuloByZero() {
        assertThrows(ModuloByZeroException.class,
                () -> calc.calculate(OperationType.MODULO, 5, 0));
    }

    @Test
    void testNullOperation() {
        assertThrows(NullOperandException.class,
                () -> calc.calculate(null, 1, 2));
    }

    @Test
    void testNullOperands() {
        assertAll(
                () -> assertThrows(NullOperandException.class, () -> calc.calculate(OperationType.ADD, null, 2)),
                () -> assertThrows(NullOperandException.class, () -> calc.calculate(OperationType.ADD, 1, null)));
    }

    @Test
    void testInvalidOperationSymbol() {
        assertThrows(InvalidOperationException.class,
                () -> OperationType.fromSymbol("invalid"));
    }

    @Test
    void testOperationWithNegativeNumbers() {
        assertAll(
                () -> assertEquals(-1.0, calc.calculate(OperationType.ADD, -2, 1)),
                () -> assertEquals(-3.0, calc.calculate(OperationType.SUBTRACT, -2, 1)),
                () -> assertEquals(-2.0, calc.calculate(OperationType.MULTIPLY, -2, 1)),
                () -> assertEquals(-2.0, calc.calculate(OperationType.DIVIDE, -2, 1)),
                () -> assertEquals(0.0, calc.calculate(OperationType.MODULO, -2, 1)));
    }

    @Test
    void testOperationWithZero() {
        assertAll(
                () -> assertEquals(1.0, calc.calculate(OperationType.ADD, 0, 1)),
                () -> assertEquals(-1.0, calc.calculate(OperationType.SUBTRACT, 0, 1)),
                () -> assertEquals(0.0, calc.calculate(OperationType.MULTIPLY, 0, 1)),
                () -> assertEquals(0.0, calc.calculate(OperationType.DIVIDE, 0, 1)),
                () -> assertEquals(0.0, calc.calculate(OperationType.MODULO, 0, 1)));
    }

    @Test
    void testOperationWithDecimalNumbers() {
        assertAll(
                () -> assertEquals(3.14, calc.calculate(OperationType.ADD, 1.57, 1.57)),
                () -> assertEquals(0.5, calc.calculate(OperationType.SUBTRACT, 1.5, 1.0)),
                () -> assertEquals(2.5, calc.calculate(OperationType.MULTIPLY, 1.25, 2.0)),
                () -> assertEquals(0.5, calc.calculate(OperationType.DIVIDE, 1.0, 2.0)),
                () -> assertEquals(0.5, calc.calculate(OperationType.MODULO, 1.5, 1.0)));
    }

    @Test
    void testOperationWithExtremelyLargeValues() {
        double maxValue = Double.MAX_VALUE;
        double halfMax = maxValue / 2;

        assertAll(
                () -> assertEquals(maxValue, calc.calculate(OperationType.ADD, maxValue, 0)),
                () -> assertEquals(maxValue, calc.calculate(OperationType.MULTIPLY, halfMax, 2)),
                () -> assertEquals(0.0, calc.calculate(OperationType.SUBTRACT, maxValue, maxValue)),
                () -> assertEquals(1.0, calc.calculate(OperationType.DIVIDE, maxValue, maxValue)),
                () -> assertEquals(0.0, calc.calculate(OperationType.MODULO, maxValue, maxValue)));
    }

    @Test
    void testInvalidSymbols() {
        assertAll(
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol("")),
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol(" ")),
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol("++")),
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol("add")),
                () -> assertThrows(InvalidOperationException.class, () -> OperationType.fromSymbol("123")));
    }
}
