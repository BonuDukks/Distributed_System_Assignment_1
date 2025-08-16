import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Stack;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {

  private Stack<Integer> serverStack = new Stack<>();

  private static int getGCD(int a, int b) {
    int biggerNumb = Math.max(a, b);
    int smallerNumb = Math.min(a, b);

    while (smallerNumb != 0) {
      int tempNumb = smallerNumb;
      smallerNumb = biggerNumb % smallerNumb;
      biggerNumb = tempNumb;
    }

    return biggerNumb;

  }

  private static int getLCM(int a, int b) {
    int product = Math.abs(a * b);
    int gcd = getGCD(a, b);
    int lcm = product / gcd;
    return lcm;
  }

  public CalculatorImplementation() throws RemoteException {
    super();
  }

  public void pushValue(int val) throws RemoteException {
    serverStack.push(val);
  }

  public void pushOperation(String operator) throws RemoteException {
    switch (operator) {
      case "min":
        int min = serverStack.peek();
        while (!serverStack.empty()) {
          int poppedValue = serverStack.pop();
          if (poppedValue < min) {
            min = poppedValue;
          }
        }
        serverStack.push(min);

      case "max":
        int max = serverStack.peek();
        while (!serverStack.empty()) {
          int poppedValue = serverStack.pop();
          if (poppedValue > max) {
            max = poppedValue;
          }
        }
        serverStack.push(max);

      case "lcm":
        if (serverStack.size() == 1) {
          break;
        }

        int firstNumb = serverStack.pop();
        int secondNumb = serverStack.pop();

        int lcmResult = getLCM(firstNumb, secondNumb);

        while (!serverStack.empty()) {
          lcmResult = getLCM(serverStack.pop(), lcmResult);
        }

        serverStack.push(lcmResult);

      case "gcd":
        if (serverStack.size() == 1) {
          break;
        }

        int firstValue = serverStack.pop();
        int secondValue = serverStack.pop();

        int result = getGCD(firstValue, secondValue);

        while (!serverStack.empty()) {
          result = getGCD(serverStack.pop(), result);
        }

        serverStack.push(result);
    }
  }

  public int pop() throws RemoteException {
    return serverStack.pop();
  }

  public boolean isEmpty() throws RemoteException {
    return serverStack.empty();
  }

  public int delayPop(int millis) throws RemoteException {

    try {
      Thread.sleep(millis);
    } catch (Exception e) {
      System.out.println(e);
    }
    return pop();

  }

  public Stack<Integer> getStack() throws RemoteException {
    return serverStack;
  }

}
