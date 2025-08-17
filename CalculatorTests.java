import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import org.junit.Assert;
import org.junit.Test;
import java.util.Stack;

public class CalculatorTests extends Thread {
  @Test
  public void testPush() {
    try {
      // Look up the remote object from the RMI registry
      Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");

      calculator.pushValue(10);

      Stack<Integer> result = calculator.getStack();
      // Stack<Integer> expected = new Stack<>();
      // expected.push(10);

      Assert.assertTrue(result.contains(10));

    } catch (NotBoundException e) {
      System.err.println("Calculator service not found: " + e.getMessage());
    } catch (MalformedURLException e) {
      System.err.println("Invalid URL: " + e.getMessage());
    } catch (RemoteException e) {
      System.err.println("Remote method invocation failed: " + e.getMessage());
    }
  }

  public void testPushMultipleClients() {

  }
}
