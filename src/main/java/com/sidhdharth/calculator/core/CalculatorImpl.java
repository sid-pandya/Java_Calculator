package com.sidhdharth.calculator.core;

import com.sidhdharth.calculator.api.Calculator;
import com.sidhdharth.calculator.chain.OperationChain;
import com.sidhdharth.calculator.chain.OperationChainBuilder;
import com.sidhdharth.calculator.exception.NullOperandException;
import com.sidhdharth.calculator.operation.Operation;

public class CalculatorImpl implements Calculator {

    @Override
    public Number calculate(Operation op, Number num1, Number num2) {
        if (op == null || num1 == null || num2 == null) {
            throw new NullOperandException();
        }
        return op.apply(num1, num2);
    }

    @Override
    public OperationChainBuilder start(Number initialValue) {
        if (initialValue == null) {
            throw new NullOperandException();
        }
        return new OperationChain(initialValue, this);
    }
}
