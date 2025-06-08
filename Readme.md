# Calculator

A Java-based calculator implementation that demonstrates object-oriented design principles, with a focus on extensibility and maintainability.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [Implementation Details](#implementation-details)
- [Usage Examples](#usage-examples)
- [Error Handling](#error-handling)
- [Testing](#testing)
- [Future Enhancements](#future-enhancements)
- [Assumptions and Design Decisions](#assumptions-and-design-decisions)
- [Adding New Operations](#adding-new-operations)

## Features

- Basic arithmetic operations (addition, subtraction, multiplication, division, modulo)
- Operation chaining for complex calculations
- Robust error handling
- Console-based user interface
- Support for both single operations and chained calculations
- Type-safe operation handling using enums
- Comprehensive test coverage

## Technologies Used

- Java 17
- Maven for dependency management
- JUnit 5 for testing
- Git for version control

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- Git

### Installation

1. Clone the repository:

```bash
git clone https://github.com/sid-pandya/Java_Calculator.git
cd calculator
```

2. Build the project:

```bash
mvn clean install
```

### Running the Application

1. Run the console application:

```bash
mvn exec:java -Dexec.mainClass="com.sidhdharth.calculator.ui.ConsoleCalculatorApp"
```

2. Follow the on-screen prompts to perform calculations:
   - Choose between single operation or chained operations mode
   - Enter numbers and operation symbols as prompted
   - View results immediately

## Implementation Details

### Core Components

1. **Operation Interface and Types**

   - `Operation` interface defines the contract for all operations
     ```java
     public interface Operation {
         Number apply(Number num1, Number num2);
     }
     ```
   - `OperationType` enum implements common operations with built-in validation
     - Each operation is self-contained with its own validation logic
     - Supports multiple symbols for the same operation (e.g., "\*", "x", "X" for multiplication)
     - Handles edge cases like division by zero and modulo by zero
     - Implements proper number type handling and conversion

2. **Calculator Implementation**

   - `Calculator` interface defines the core calculator API
     ```java
     public interface Calculator {
         Number calculate(Operation op, Number num1, Number num2);
         OperationChainBuilder start(Number initialValue);
     }
     ```
   - `CalculatorImpl` provides the implementation
     - Implements both single operation and chained operation support
     - Handles null checks and input validation
     - Returns results as Number type for flexibility
   - `CalculatorConfig` manages calculator instance creation
     - Provides a factory method for creating calculator instances
     - Enables easy switching between different calculator implementations

3. **Operation Chaining**

   - Fluent interface for chaining multiple operations
     ```java
     public interface OperationChainBuilder {
         OperationChainBuilder chain(Operation op, Number operand);
         Number getResult();
     }
     ```
   - `OperationChain` implementation
     - Maintains calculation state between operations
     - Supports complex calculations in a readable format
     - Handles intermediate results efficiently
   - Benefits:
     - Improved code readability
     - Reduced boilerplate code
     - Natural expression of calculation sequences

4. **Error Handling System**

   - Hierarchical exception structure
     - `CalculatorException` as the base exception class
     - Specific exceptions for different error cases:
       - `DivisionByZeroException`
       - `ModuloByZeroException`
       - `InvalidOperationException`
       - `NullOperandException`
   - Comprehensive error messages
   - Proper exception propagation

5. **Console User Interface**
   - Interactive command-line interface
   - Two operation modes:
     1. Single operation mode
     2. Chained operations mode
   - User-friendly input prompts
   - Clear result display
   - Error message presentation

## Usage Examples

1. **Single Operation**

```java
Calculator calc = CalculatorConfig.createCalculator();
Number result = calc.calculate(OperationType.ADD, 5, 3); // Returns 8
```

2. **Chained Operations**

```java
Number result = calc.start(5)
    .chain(OperationType.ADD, 3)
    .chain(OperationType.MULTIPLY, 2)
    .getResult(); // Returns 16
```

3. **Console Application**

```bash
=== Calculator ===
Select mode:
1) Single operation
2) Chained operations
3) Exit
```

## Error Handling

The calculator includes comprehensive error handling for:

- Division by zero
- Modulo by zero
- Invalid operations
- Null operands
- Invalid input formats

### Exception Hierarchy

```
CalculatorException
├── DivisionByZeroException
├── ModuloByZeroException
├── InvalidOperationException
├── InvalidInputException
└── NullOperandException
```

### Data Validation

- Input validation for numbers and operations
- Null checks for all parameters
- Operation symbol validation
- Range checking for numeric inputs
- Type safety through the Number class

## Testing

The project includes extensive unit tests covering:

- Basic arithmetic operations
- Operation chaining
- Error cases
- Edge cases with large numbers
- Decimal number operations
- Negative number operations
- Zero operations

### Running Tests

```bash
mvn test
```

### Test Coverage

- Unit tests for all calculator operations
- Integration tests for operation chaining
- Exception handling tests
- Edge case validation
- Performance tests for large calculations

## Screenshots

### Console Interface

```
=== Calculator ===
Select mode:
1) Single operation
2) Chained operations
3) Exit
> 1
Please enter the first number: 5
Enter operator (+, -, *, /, %): +
Please enter the second number: 3
The result is: 8
```

### Chained Operations

```
=== Calculator ===
Select mode:
1) Single operation
2) Chained operations
3) Exit
> 2
Please enter the first number: 5
Current result: 5.0
Enter operation (+, -, *, /, %) or 'end' to finish: +
Please enter the second number: 3
Current result: 8.0
Enter operation (+, -, *, /, %) or 'end' to finish: *
Please enter the second number: 2
Current result: 16.0
Enter operation (+, -, *, /, %) or 'end' to finish: end
The final result is: 16.0
```

## Adding New Operations

### Example: Adding Power Operation

To add a new power operation (x^y) to the calculator, follow these steps:

1. **Add the Operation to OperationType Enum**

   ```java
   public enum OperationType implements Operation {
       // ... existing operations ...

       /**
        * Power operation (x^y)
        */
       POWER((num1, num2) -> {
           double base = num1.doubleValue();
           double exponent = num2.doubleValue();

           // Handle special cases
           if (base == 0 && exponent < 0) {
               throw new InvalidOperationException("Cannot raise 0 to a negative power");
           }

           double result = Math.pow(base, exponent);
           return result == -0.0 ? 0.0 : result;
       }, "^", "**"),

       // ... rest of the enum
   }
   ```

2. **Add Exception Handling (if needed)**

   ```java
   public class PowerOperationException extends CalculatorException {
       public PowerOperationException(String message) {
           super("Power operation error: " + message);
       }
   }
   ```

3. **Update Console UI to Support New Operation**

   ```java
   // In ConsoleCalculatorApp.java
   private static void displayAvailableOperations() {
       System.out.println("Available operations: " + OperationType.allSymbols());
   }
   ```

4. **Add Unit Tests**
   ```java
   @Test
   void testPowerOperation() {
       Calculator calc = CalculatorConfig.createCalculator();

       // Test basic power operations
       assertEquals(8.0, calc.calculate(OperationType.POWER, 2, 3));
       assertEquals(1.0, calc.calculate(OperationType.POWER, 5, 0));
       assertEquals(0.25, calc.calculate(OperationType.POWER, 2, -2));

       // Test edge cases
       assertThrows(InvalidOperationException.class,
           () -> calc.calculate(OperationType.POWER, 0, -1));
   }
   ```

### Usage Examples

1. **Basic Power Operation**

   ```java
   Calculator calc = CalculatorConfig.createCalculator();
   Number result = calc.calculate(OperationType.POWER, 2, 3); // Returns 8
   ```

2. **Chained Operations with Power**

   ```java
   Number result = calc.start(2)
       .chain(OperationType.POWER, 3)
       .chain(OperationType.MULTIPLY, 2)
       .getResult(); // Returns 16
   ```

3. **Console Usage**
   ```
   === Calculator ===
   Please enter the first number: 2
   Enter operator (+, -, *, /, %, ^): ^
   Please enter the second number: 3
   The result is: 8
   ```

## Future Enhancements

1. **Advanced Mathematical Operations**

   - Trigonometric functions (sin, cos, tan)
   - Logarithmic functions (log, ln)
   - Exponential functions
   - Complex number support

2. **User Interface Improvements**

   - Graphical User Interface (GUI)
     - Modern, responsive design
     - Scientific calculator layout
     - Keyboard shortcuts
   - Web-based interface with RESTful API

3. **Memory and History Features**

   - Operation history tracking
   - Result history with timestamps
   - Export/import calculation history

4. **Performance Optimizations**

   - Operation result caching
   - Parallel computation support
   - Calculation result precision control

5. **Extended Functionality**
   - Unit conversion
   - Currency conversion with real-time rates
   - Custom function definition
   - Variable support

## Assumptions and Design Decisions

### Core Design Decisions

1. **Operation Implementation**

   - Chose enum-based implementation (`OperationType`) over class-based for operations because:
     - Operations are fixed and well-defined
     - Provides type safety at compile time
     - Simplifies operation lookup and validation
     - Reduces boilerplate code
     - Makes it easier to add new operations

2. **Number Type Handling**

   - Used `Number` class instead of specific numeric types because:
     - Provides flexibility in handling different numeric types
     - Allows for both integer and floating-point calculations
     - Maintains precision where needed
     - Simplifies type conversion

3. **Operation Chaining**

   - Implemented fluent interface pattern for operation chaining because:
     - Improves code readability
     - Reduces cognitive load when performing multiple operations
     - Makes complex calculations more maintainable
     - Follows builder pattern best practices

4. **Error Handling**
   - Created a custom exception hierarchy because:
     - Provides specific error types for different scenarios
     - Enables better error handling and recovery
     - Improves debugging and maintenance
     - Makes error messages more user-friendly

### Assumptions

1. **Input Handling**

   - Assumes numeric inputs are within Java's Number type limits
   - Assumes operation symbols are single characters or specific strings
   - Assumes users will provide valid numeric inputs
   - Assumes console input is the primary interface

2. **Performance**

   - Assumes calculations are performed sequentially
   - Assumes memory usage is not a critical constraint
   - Assumes real-time performance is not required
   - Assumes single-user operation

3. **Extensibility**

   - Assumes new operations will follow the same pattern as existing ones
   - Assumes operations are binary (take two operands)
   - Assumes operations are commutative where applicable
   - Assumes no need for operation precedence rules in chained operations

4. **User Interface**
   - Assumes console-based interface is sufficient for basic usage
   - Assumes users are familiar with basic calculator operations
   - Assumes English language interface is acceptable
   - Assumes keyboard input is the primary input method

### Trade-offs

1. **Simplicity vs. Flexibility**

   - Chose simpler enum-based implementation over more flexible but complex class-based system
   - Prioritized ease of use and maintenance over maximum extensibility

2. **Performance vs. Readability**

   - Prioritized code readability and maintainability over micro-optimizations
   - Accepted potential performance overhead of Number type for better type safety

3. **Error Handling vs. Code Complexity**

   - Added comprehensive error handling at the cost of additional code complexity
   - Chose specific exceptions over generic ones for better error handling

4. **Console UI vs. GUI**
   - Started with console interface for simplicity and quick implementation
   - Left room for future GUI implementation without changing core logic
