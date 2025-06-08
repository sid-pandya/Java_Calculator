package com.sidhdharth.calculator.core;

import com.sidhdharth.calculator.api.Calculator;
import com.sidhdharth.calculator.chain.OperationChain;
import com.sidhdharth.calculator.chain.OperationChainBuilder;
import com.sidhdharth.calculator.exception.NullOperandException;
import com.sidhdharth.calculator.operation.Operation;

/**
 * Implementation of the Calculator interface that provides basic arithmetic
 * operations
 * and supports operation chaining.
 */
public class CalculatorImpl implements Calculator {
    /**
     * Performs a calculation using the specified operation and operands.
     *
     * @param op   The operation to perform
     * @param num1 The first operand
     * @param num2 The second operand
     * @return The result of the calculation
     * @throws NullOperandException if any of the parameters are null
     */
    @Override
    public Number calculate(Operation op, Number num1, Number num2) {
        if (op == null || num1 == null || num2 == null) {
            throw new NullOperandException();
        }
        return op.apply(num1, num2);
    }

    /**
     * Starts a new operation chain with the specified initial value.
     *
     * @param initialValue The initial value for the chain
     * @return A new OperationChainBuilder instance
     * @throws NullOperandException if the initial value is null
     */
    @Override
    public OperationChainBuilder start(Number initialValue) {
        if (initialValue == null) {
            throw new NullOperandException();
        }
        return new OperationChain(initialValue, this);
    }
}
