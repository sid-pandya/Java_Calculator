package com.sidhdharth.calculator.chain;

import com.sidhdharth.calculator.api.Calculator;
import com.sidhdharth.calculator.operation.Operation;

public class OperationChain implements OperationChainBuilder {
    private Number value;
    private final Calculator calculator;

    public OperationChain(Number initialValue,Calculator calculator) {
        this.value = initialValue;
        this.calculator = calculator;
    }

    @Override
    public OperationChainBuilder chain(Operation op, Number operand){
        this.value = calculator.calculate(op, this.value, operand);
        return this;
    }

    @Override
    public Number getResult() {
        return this.value;
    }

}
