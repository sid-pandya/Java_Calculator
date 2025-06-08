package com.sidhdharth.calculator.chain;

import com.sidhdharth.calculator.operation.Operation;

public interface OperationChainBuilder {
    OperationChainBuilder chain(Operation op, Number operand);
    Number getResult();
}
