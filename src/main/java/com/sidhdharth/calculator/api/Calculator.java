package com.sidhdharth.calculator.api;

import com.sidhdharth.calculator.chain.OperationChainBuilder;
import com.sidhdharth.calculator.operation.Operation;

public interface Calculator {
    Number calculate(Operation op, Number num1, Number num2);
    OperationChainBuilder start(Number initialValue);
}
