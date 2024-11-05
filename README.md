# MyAlgoProject

### Overview


MyAlgoLogic is an implementation of an algorithmic trading logic that creates, manages and cancels buy orders based on the state of the order book. The class contains a logic to evaluate current market condtions, decide when to place new buy orders and manage cancellations when certain criteria are met. This logic is integrated into a testing framework with MyAlgoTest for unit testing and MyAlgoBackTest for backtesting the algorithm against historical or simulated market data.



### Files


MyAlgoLogic.java: Contains the trading algorithm logic that evaluates the market state and executes orders.

MyAlgoTest.java: Contains unit tests for MyAlgoLogic to verify functionality such as order creation.

MyAlgoBackTest.java: Contains backtesting logic to evaluate the performance and behaviour of MyAlgoLogic over simulated market data.



### Key Tests


Sends a simulated market data tick and verifies that three child orders are created

Asserts that there are no active child orders after the test completes.



### MyAlgoBackTest


Simulates market conditions by sending initial market data and a secondary tick that represents a market shift.

Asserts that the algorithm creates three child orders, then verifies the total filled quantity and other order management behaviour in response to the market data changes.



### Running the Tests


Use a java testing framework such as JUnit.

Run MyAlgoTest to verify basic order placement and management.

Run MyAlgoBackTest to validate behaviour in backtesting scenarios and simulate realistic market interactions.
