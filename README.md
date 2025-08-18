# Distributed_System_Assignment_1

1. To compile all the java files, type "make" in terminal and press enter.

2. To launch the server type "java CalculatorServer" into terminal after running the makefile and press enter.

3. To run the client type "java CalculatorClient" into terminal after running the makefile and press enter.

4. To simulate multiple clients, open another instance of terminal and repeat step 3.

5. After running an instance of a client, you will be displayed with the option of the calculator,
   each method being represented by a number. Type in the number that correlates to the desired method and press enter.

6. After choosing a method, it will prompt the user again for another input (if the operation needs one), and/or print out
   the result after doing the operation. This will bring the user back to the same interface as step 5.

7. To run test cases, type "java -jar junit-platform-console-standalone-6.0.0-M2.jar execute --class-path . --select-class CalculatorTests" in the terminal and press enter.
