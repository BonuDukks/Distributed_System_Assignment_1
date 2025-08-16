import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.util.Stack;

public class CalculatorClient {
  public static void main(String[] args) {
    try {
      // Look up the remote object from the RMI registry
      Calculator goal = (Calculator) Naming.lookup("rmi://localhost:1099/goal");

      // Test the sum method with sample values
      int result = goal.sum(10, 20);
      System.out.println("10 + 20 = " + result);

      // Test the push and get method
      goal.push(result);
      goal.push(5);
      Stack<Integer> serverStack = goal.getStack();
      System.out.println(serverStack);

      // Test the pop method
      goal.pop();
      serverStack = goal.getStack();
      System.out.println(serverStack);

    } catch (NotBoundException e) {
      System.err.println("Calculator service not found: " + e.getMessage());
    } catch (MalformedURLException e) {
      System.err.println("Invalid URL: " + e.getMessage());
    } catch (RemoteException e) {
      System.err.println("Remote method invocation failed: " + e.getMessage());
    }
  }
}
