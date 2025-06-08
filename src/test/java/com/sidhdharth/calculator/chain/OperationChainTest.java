package com.sidhdharth.calculator.chain;

import com.sidhdharth.calculator.api.Calculator;
import com.sidhdharth.calculator.config.CalculatorConfig;
import com.sidhdharth.calculator.exception.DivisionByZeroException;
import com.sidhdharth.calculator.exception.ModuloByZeroException;
import com.sidhdharth.calculator.operation.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationChainTest {

    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = CalculatorConfig.createCalculator();
    }

    @Test
    void testSimpleChain() {
        Number result = calc.start(5)
                .chain(OperationType.ADD, 3)
                .chain(OperationType.MULTIPLY, 2)
                .getResult();
        // (5 + 3) * 2 = 16
        assertEquals(16.0, result);
    }

    @Test
    void testMultipleChains() {
        Number result = calc.start(10)
                .chain(OperationType.SUBTRACT, 4)
                .chain(OperationType.DIVIDE, 2)
                .chain(OperationType.ADD, 5)
                .getResult();
        // ((10 - 4) / 2) + 5 = 8
        assertEquals(8.0, result);
    }

    @Test
    void testChainWithZero() {
        Number result = calc.start(0)
                .chain(OperationType.ADD, 0)
                .getResult();
        assertEquals(0.0, result);
    }

    @Test
    void testChainWithModulo() {
        Number result = calc.start(10)
                .chain(OperationType.MODULO, 3)
                .chain(OperationType.MULTIPLY, 2)
                .getResult();
        // (10 % 3) * 2 = 2
        assertEquals(2.0, result);
    }

    @Test
    void testChainWithNegativeNumbers() {
        Number result = calc.start(-5)
                .chain(OperationType.ADD, 3)
                .chain(OperationType.MULTIPLY, -2)
                .getResult();
        // (-5 + 3) * -2 = 4
        assertEquals(4.0, result);
    }

    @Test
    void testChainWithDivisionByZero() {
        assertThrows(DivisionByZeroException.class, () -> calc.start(10)
                .chain(OperationType.DIVIDE, 0)
                .getResult());
    }

    @Test
    void testChainWithModuloByZero() {
        assertThrows(ModuloByZeroException.class, () -> calc.start(10)
                .chain(OperationType.MODULO, 0)
                .getResult());
    }

    @Test
    void testComplexChain() {
        Number result = calc.start(100)
                .chain(OperationType.DIVIDE, 2)
                .chain(OperationType.SUBTRACT, 10)
                .chain(OperationType.MULTIPLY, 3)
                .chain(OperationType.MODULO, 7)
                .chain(OperationType.ADD, 5)
                .getResult();
        // ((((100 / 2) - 10) * 3) % 7) + 5 = 6
        // 100 / 2 = 50
        // 50 - 10 = 40
        // 40 * 3 = 120
        // 120 % 7 = 1
        // 1 + 5 = 6
        assertEquals(6.0, result);
    }

    @Test
    void testComplexMixedSequence() {
        Number result = calc.start(10.5)
                .chain(OperationType.MULTIPLY, 2.5)
                .chain(OperationType.ADD, -5.25)
                .chain(OperationType.DIVIDE, 2.0)
                .chain(OperationType.MODULO, 3.0)
                .chain(OperationType.SUBTRACT, 1.5)
                .getResult();
        // (((((10.5 * 2.5) + -5.25) / 2.0) % 3.0) - 1.5
        // 10.5 * 2.5 = 26.25
        // 26.25 + -5.25 = 21.0
        // 21.0 / 2.0 = 10.5
        // 10.5 % 3.0 = 1.5
        // 1.5 - 1.5 = 0.0
        assertEquals(0.0, result);
    }

    @Test
    void testChainWithZeroOperations() {
        assertAll(
                () -> {
                    Number result = calc.start(5)
                            .chain(OperationType.ADD, 0)
                            .chain(OperationType.MULTIPLY, 0)
                            .chain(OperationType.ADD, 10)
                            .getResult();
                    assertEquals(10.0, result);
                },
                () -> {
                    Number result = calc.start(0)
                            .chain(OperationType.MULTIPLY, 5)
                            .chain(OperationType.ADD, 0)
                            .chain(OperationType.DIVIDE, 2)
                            .getResult();
                    assertEquals(0.0, result);
                });
    }

    @Test
    void testChainWithExtremelyLargeValues() {
        double maxValue = Double.MAX_VALUE;
        Number result = calc.start(maxValue)
                .chain(OperationType.DIVIDE, 2)
                .chain(OperationType.MULTIPLY, 2)
                .chain(OperationType.SUBTRACT, maxValue)
                .getResult();
        assertEquals(0.0, result);
    }
}
