import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.util.Stack;
import java.util.Scanner;

public class CalculatorClient {
  public static void main(String[] args) {
    try {
      // Look up the remote object from the RMI registry
      Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");

      boolean exit = false;

      while (!exit) {
        Scanner scannerObj = new Scanner(System.in);
        System.out.println("""

            Calculator Operators
            (1) - Push Value
            (2) - Pop
            (3) - Check if stack is empty
            (4) - Minimum of Stack
            (5) - Maximum of Stack
            (6) - LCM (Lowest Common Multiple) of Stack
            (7) - GCD (Greatest Common Divisor) of Stack
            (8) - Delay Pop
            (9) - Exit
            """);

        int userInput = scannerObj.nextInt();

        switch (userInput) {
          case 1:
            System.out.println("\nInput an Integer to be pushed to the stack:");
            int pushedValue = scannerObj.nextInt();
            calculator.pushValue(pushedValue);
            System.out.println("Current stack is: " + calculator.getStack());
            break;
          case 2:
            int poppedValue = calculator.pop();
            System.out.println("\nInteger popped from the stack was: " + poppedValue);
            System.out.println("Current stack is: " + calculator.getStack());
            break;
          case 3:
            if (calculator.isEmpty()) {
              System.out.println("Stack is empty.");
            } else {
              System.out.println("Stack is not empty.");
            }
            break;
          case 4:
            calculator.pushOperation("min");
            System.out.println("Current stack after min operation is: " + calculator.getStack());
            break;
          case 5:
            calculator.pushOperation("max");
            System.out.println("Current stack after max operation is: " + calculator.getStack());
            break;
          case 6:
            calculator.pushOperation("lcm");
            System.out.println("Current stack after lcm operation is: " + calculator.getStack());
            break;
          case 7:
            calculator.pushOperation("gcd");
            System.out.println("Current stack after gcd operation is: " + calculator.getStack());
            break;
          case 8:
            System.out.println("Enter delay value (in milliseconds): ");
            int delayValue = scannerObj.nextInt();
            calculator.pushValue(delayValue);
            System.out.println("Current stack is: " + calculator.getStack());
            break;

          case 9:
            exit = true;

            scannerObj.close();
            break;

        }

      }

    } catch (NotBoundException e) {
      System.err.println("Calculator service not found: " + e.getMessage());
    } catch (MalformedURLException e) {
      System.err.println("Invalid URL: " + e.getMessage());
    } catch (RemoteException e) {
      System.err.println("Remote method invocation failed: " + e.getMessage());
    }
  }
}
