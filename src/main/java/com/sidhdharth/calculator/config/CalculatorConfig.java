package com.sidhdharth.calculator.config;

import com.sidhdharth.calculator.api.Calculator;
import com.sidhdharth.calculator.core.CalculatorImpl;

public class CalculatorConfig {
    public static Calculator createCalculator(){
        return new CalculatorImpl();
    }
}
