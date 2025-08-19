import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import org.junit.Assert;
import org.junit.Test;
import java.util.Stack;

public class CalculatorMultiStackTests {
  @Test
  public void testPushMultipleClients() throws Exception {
    Thread client1 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(5, userId);

        Assert.assertTrue(calculatorMultiStack.getStack(userId).contains(5));
        Assert.assertFalse(calculatorMultiStack.getStack(userId).contains(7));
        Assert.assertFalse(calculatorMultiStack.getStack(userId).contains(10));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(7, userId);

        Assert.assertTrue(calculatorMultiStack.getStack(userId).contains(7));
        Assert.assertFalse(calculatorMultiStack.getStack(userId).contains(5));
        Assert.assertFalse(calculatorMultiStack.getStack(userId).contains(10));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(10, userId);

        Assert.assertTrue(calculatorMultiStack.getStack(userId).contains(10));
        Assert.assertFalse(calculatorMultiStack.getStack(userId).contains(7));
        Assert.assertFalse(calculatorMultiStack.getStack(userId).contains(5));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client1.join();

    client2.start();
    client2.join();

    client3.start();
    client3.join();
  }

  @Test
  public void testPopMultipleClients() throws Exception {

    Thread client1 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(5, userId);
        calculatorMultiStack.pushValue(10, userId);

        Assert.assertTrue(calculatorMultiStack.getStack(userId).contains(5));
        Assert.assertTrue(calculatorMultiStack.getStack(userId).contains(10));
        Assert.assertEquals(2, calculatorMultiStack.getStack(userId).size());

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");

        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(99, userId);
        calculatorMultiStack.pop(userId);

        Assert.assertFalse(calculatorMultiStack.getStack(userId).contains(5));
        Assert.assertFalse(calculatorMultiStack.getStack(userId).contains(10));
        Assert.assertTrue(calculatorMultiStack.getStack(userId).isEmpty());

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");

        int userId = calculatorMultiStack.intialiseClient();

        calculatorMultiStack.pushValue(-5, userId);
        calculatorMultiStack.pushValue(-4, userId);
        calculatorMultiStack.pop(userId);

        Assert.assertTrue(calculatorMultiStack.getStack(userId).contains(-5));
        Assert.assertFalse(calculatorMultiStack.getStack(userId).contains(10));
        Assert.assertEquals(1, calculatorMultiStack.getStack(userId).size());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client1.join();

    client2.start();
    client2.join();

    client3.start();
    client3.join();

  }

  @Test
  public void testGCDMultipleClients() throws Exception {

    Thread client1 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");

        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(27, userId);
        calculatorMultiStack.pushValue(18, userId);
        calculatorMultiStack.pushValue(81, userId);
        calculatorMultiStack.pushOperation("gcd", userId);

        Assert.assertEquals(9, calculatorMultiStack.pop(userId));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(20, userId);
        calculatorMultiStack.pushValue(16, userId);
        calculatorMultiStack.pushValue(8, userId);
        calculatorMultiStack.pushOperation("gcd", userId);

        Assert.assertEquals(4, calculatorMultiStack.pop(userId));
      } catch (Exception e) {

        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(20, userId);
        calculatorMultiStack.pushValue(40, userId);
        calculatorMultiStack.pushValue(80, userId);
        calculatorMultiStack.pushOperation("gcd", userId);

        Assert.assertEquals(20, calculatorMultiStack.pop(userId));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client1.join();

    client2.start();
    client2.join();

    client3.start();
    client3.join();

  }

  @Test
  public void testLCMMultipleClients() throws Exception {

    Thread client1 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");

        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(2, userId);
        calculatorMultiStack.pushValue(4, userId);
        calculatorMultiStack.pushValue(6, userId);
        calculatorMultiStack.pushOperation("lcm", userId);

        Assert.assertEquals(12, calculatorMultiStack.pop(userId));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(-20, userId);
        calculatorMultiStack.pushValue(10, userId);
        calculatorMultiStack.pushValue(100, userId);
        calculatorMultiStack.pushOperation("lcm", userId);

        Assert.assertEquals(100, calculatorMultiStack.pop(userId));
      } catch (Exception e) {

        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(6, userId);
        calculatorMultiStack.pushValue(7, userId);
        calculatorMultiStack.pushValue(2, userId);
        calculatorMultiStack.pushOperation("lcm", userId);

        Assert.assertEquals(42, calculatorMultiStack.pop(userId));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client1.join();

    client2.start();
    client2.join();

    client3.start();
    client3.join();

  }

  @Test
  public void testMinMultipleClients() throws Exception {

    Thread client1 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");

        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(-2, userId);
        calculatorMultiStack.pushValue(-4, userId);
        calculatorMultiStack.pushValue(6, userId);
        calculatorMultiStack.pushOperation("min", userId);

        int poppedValue = calculatorMultiStack.pop(userId);

        Assert.assertEquals(-4, poppedValue);

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(-20, userId);
        calculatorMultiStack.pushValue(10, userId);
        calculatorMultiStack.pushValue(100, userId);
        calculatorMultiStack.pushOperation("min", userId);

        Assert.assertEquals(-20, calculatorMultiStack.pop(userId));
      } catch (Exception e) {

        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(6, userId);
        calculatorMultiStack.pushValue(7, userId);
        calculatorMultiStack.pushValue(2, userId);
        calculatorMultiStack.pushOperation("min", userId);

        Assert.assertEquals(2, calculatorMultiStack.pop(userId));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client1.join();

    client2.start();
    client2.join();

    client3.start();
    client3.join();

  }

  @Test
  public void testMaxMultipleClients() throws Exception {

    Thread client1 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");

        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(2, userId);
        calculatorMultiStack.pushValue(4, userId);
        calculatorMultiStack.pushValue(6, userId);
        calculatorMultiStack.pushOperation("max", userId);

        Assert.assertEquals(6, calculatorMultiStack.pop(userId));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(-20, userId);
        calculatorMultiStack.pushValue(10, userId);
        calculatorMultiStack.pushValue(100, userId);
        calculatorMultiStack.pushOperation("max", userId);

        Assert.assertEquals(100, calculatorMultiStack.pop(userId));
      } catch (Exception e) {

        e.printStackTrace();
      }
    });

    Thread client3 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(-6, userId);
        calculatorMultiStack.pushValue(-7, userId);
        calculatorMultiStack.pushValue(-2, userId);
        calculatorMultiStack.pushOperation("max", userId);

        Assert.assertEquals(-2, calculatorMultiStack.pop(userId));

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client1.join();

    client2.start();
    client2.join();

    client3.start();
    client3.join();

  }

  @Test
  public void testIsEmptyMultipleClients() throws Exception {
    Thread client1 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(5, userId);

        Assert.assertFalse(calculatorMultiStack.getStack(userId).isEmpty());

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread client2 = new Thread(() -> {
      try {
        CalculatorMultiStack calculatorMultiStack = (CalculatorMultiStack) Naming
            .lookup("rmi://localhost:1099/CalculatorMultiStack");
        int userId = calculatorMultiStack.intialiseClient();
        calculatorMultiStack.pushValue(7, userId);
        calculatorMultiStack.pop(userId);

        Assert.assertTrue(calculatorMultiStack.getStack(userId).isEmpty());

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    client1.start();
    client1.join();

    client2.start();
    client2.join();

  }
}