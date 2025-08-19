import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.util.Stack;
import java.util.Scanner;

public class CalculatorMultiStackClient {
  public static void main(String[] args) {
    try {
      // Look up the remote object from the RMI registry
      CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
          .lookup("rmi://localhost:1099/CalculatorMultiStack");

      // Server intialises a stack exclusive to the client and returns a user ID to be
      // passed into other functions.
      int userId = calculatorMultiStack.intialiseClient();

      // Boolean used for while loop to constantly prompt the user.
      boolean exit = false;

      // Prompts the users with a menu to select the calculator operator they want to
      // use. Handle the input using a switch case, which will prompt them again (if
      // applicable) and call the method on the remote object.
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
            calculatorMultiStack.pushValue(pushedValue, userId);
            System.out.println("Current stack is: " + calculatorMultiStack.getStack(userId));
            break;
          case 2:
            int poppedValue = calculatorMultiStack.pop(userId);
            System.out.println("\nInteger popped from the stack was: " + poppedValue);
            System.out.println("Current stack is: " + calculatorMultiStack.getStack(userId));
            break;
          case 3:
            if (calculatorMultiStack.isEmpty(userId)) {
              System.out.println("\nStack is empty.");
            } else {
              System.out.println("\nStack is not empty.");
            }
            break;
          case 4:
            calculatorMultiStack.pushOperation("min", userId);
            System.out.println("\nCurrent stack after min operation is: " + calculatorMultiStack.getStack(userId));
            break;
          case 5:
            calculatorMultiStack.pushOperation("max", userId);
            System.out.println("\nCurrent stack after max operation is: " + calculatorMultiStack.getStack(userId));
            break;
          case 6:
            calculatorMultiStack.pushOperation("lcm", userId);
            System.out.println("\nCurrent stack after lcm operation is: " + calculatorMultiStack.getStack(userId));
            break;
          case 7:
            calculatorMultiStack.pushOperation("gcd", userId);
            System.out.println("\nCurrent stack after gcd operation is: " + calculatorMultiStack.getStack(userId));
            break;
          case 8:
            System.out.println("\nEnter delay value (in milliseconds): ");
            int delayValue = scannerObj.nextInt();
            calculatorMultiStack.delayPop(delayValue, userId);
            System.out.println("Current stack is: " + calculatorMultiStack.getStack(userId));
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
