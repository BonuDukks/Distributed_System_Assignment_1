import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import org.junit.Assert;
import org.junit.Test;
import java.util.Stack;

public class CalculatorTests {
  @Test
  public void testPushSingleClient() {
    try {
      // Look up the remote object from the RMI registry
      Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");

      calculator.pushValue(10);
      calculator.pushValue(-5);

      Stack<Integer> result = calculator.getStack();

      Assert.assertTrue(result.contains(10));
      Assert.assertTrue(result.contains(-5));

      while (!calculator.isEmpty()) {
        calculator.pop();
      }

    } catch (NotBoundException e) {
      System.err.println("Calculator service not found: " + e.getMessage());
    } catch (MalformedURLException e) {
      System.err.println("Invalid URL: " + e.getMessage());
    } catch (RemoteException e) {
      System.err.println("Remote method invocation failed: " + e.getMessage());
    }
  }

  @Test
  public void testPushMultipleClients() throws Exception {
    // Create two "client" threads
    Thread client1 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pushValue(5);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pushValue(7);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pushValue(10);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client2.start();
    client3.start();

    client1.join();
    client2.join();
    client3.join();

    Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
    Stack<Integer> result = calculator.getStack();

    Assert.assertTrue(result.contains(5));
    Assert.assertTrue(result.contains(7));
    Assert.assertTrue(result.contains(10));

    while (!calculator.isEmpty()) {
      calculator.pop();
    }
  }

  @Test
  public void testPopSingleClient() {
    try {
      // Look up the remote object from the RMI registry
      Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");

      calculator.pushValue(10);
      calculator.pushValue(-5);
      int poppedValue = calculator.pop();

      Stack<Integer> result = calculator.getStack();
      Stack<Integer> expected = new Stack<>();
      expected.push(10);

      Assert.assertEquals(expected, result);
      Assert.assertEquals(-5, poppedValue);

      while (!calculator.isEmpty()) {
        calculator.pop();
      }

    } catch (NotBoundException e) {
      System.err.println("Calculator service not found: " + e.getMessage());
    } catch (MalformedURLException e) {
      System.err.println("Invalid URL: " + e.getMessage());
    } catch (RemoteException e) {
      System.err.println("Remote method invocation failed: " + e.getMessage());
    }
  }

  @Test
  public void testPopMultipleClients() throws Exception {
    // Create two "client" threads
    Thread client1 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pushValue(5);
        calculator.pushValue(10);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pop();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pop();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client1.join();

    client2.start();
    client3.start();

    client3.join();
    client2.join();

    Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");

    Assert.assertTrue(calculator.isEmpty());

    while (!calculator.isEmpty()) {
      calculator.pop();
    }

  }

  @Test
  public void testPushOperatorSingleClient() {
    try {

      // Stack<Integer> expected = new Stack<>();
      // expected.push(10);

      // Look up the remote object from the RMI registry
      Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");

      calculator.pushValue(1000);
      calculator.pushValue(2);
      calculator.pushValue(14209);
      calculator.pushOperation("max");

      int maxResult = calculator.pop();

      Assert.assertEquals(14209, maxResult);

      calculator.pushValue(301);
      calculator.pushValue(-900);
      calculator.pushValue(-899);
      calculator.pushOperation("min");

      int minResult = calculator.pop();

      Assert.assertEquals(-900, minResult);

      calculator.pushValue(11);
      calculator.pushValue(10);
      calculator.pushValue(-2);
      calculator.pushOperation("lcm");

      int lcmResult = calculator.pop();

      Assert.assertEquals(110, lcmResult);

      calculator.pushValue(10);
      calculator.pushValue(-5);
      calculator.pushValue(1);
      calculator.pushOperation("gcd");

      int gcdResult = calculator.pop();

      Assert.assertEquals(1, gcdResult);

      while (!calculator.isEmpty()) {
        calculator.pop();
      }

    } catch (NotBoundException e) {
      System.err.println("Calculator service not found: " + e.getMessage());
    } catch (MalformedURLException e) {
      System.err.println("Invalid URL: " + e.getMessage());
    } catch (RemoteException e) {
      System.err.println("Remote method invocation failed: " + e.getMessage());
    }
  }

  @Test
  public void testGCDMultipleClients() throws Exception {
    // Create two "client" threads
    Thread client1 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pushValue(27);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pushValue(18);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pushOperation("gcd");
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client2.start();

    client1.join();
    client2.join();

    client3.start();
    client3.join();

    Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
    int gcdResult = calculator.pop();

    Assert.assertEquals(9, gcdResult);

    while (!calculator.isEmpty()) {
      calculator.pop();
    }

  }

  @Test
  public void testPopDelayMultipleClient() throws Exception {
    // Create two "client" threads
    Thread client1 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pushValue(27);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.delayPop(7000);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
        calculator.pushValue(10);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client2.start();
    client3.start();

    client1.join();
    client2.join();
    client3.join();

    Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");
    int delayPopResult = calculator.pop();

    Assert.assertEquals(27, delayPopResult);

    while (!calculator.isEmpty()) {
      calculator.pop();
    }

  }

  @Test
  public void testIsEmpty() {
    try {

      Calculator calculator = (Calculator) Naming.lookup("rmi://localhost:1099/Calculator");

      Assert.assertTrue(calculator.isEmpty());

      calculator.pushValue(0);

      Assert.assertFalse(calculator.isEmpty());

      while (!calculator.isEmpty()) {
        calculator.pop();
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