import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Stack;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {

  // Intialises server stack
  private Stack<Integer> serverStack = new Stack<>();

  // Uses euclidean algorithm to calculate the gcd (greatest common divisor) of 2
  // numbers. Ensures that both numbers are positive and that dividing by 0
  // doesn't occur
  private static int getGCD(int a, int b) {
    int biggerNumb = Math.abs(Math.max(a, b));
    int smallerNumb = Math.abs(Math.min(a, b));

    if (smallerNumb == 0) {
      return biggerNumb;
    }

    while (smallerNumb != 0) {
      int tempNumb = smallerNumb;
      smallerNumb = biggerNumb % smallerNumb;
      biggerNumb = tempNumb;
    }

    return biggerNumb;

  }

  // Calculates the lowest common multiple (lcm) of two numbers by using the
  // formula |a*b|/gcd(a,b)
  private static int getLCM(int a, int b) {
    int product = Math.abs(a * b);
    int gcd = getGCD(a, b);
    int lcm = product / gcd;
    return lcm;
  }

  // Creates and exports remote object
  public CalculatorImplementation() throws RemoteException {
    super();
  }

  // Push value onto stack
  public void pushValue(int val) throws RemoteException {
    serverStack.push(val);
  }

  // Uses a switch case to handle user input of the four operators. "min pops all
  // the values and pushes the min back. "max" pops all values and pushes the max
  // back. "lcm" pops all values and pushes the lcm back. "gcd" pops all values
  // and pushes the gcd back
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

        // If only 1 value on stack, breaks so no error occurs.
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

        // If only 1 value on stack, breaks so no error occurs.
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

  // Pops the top element from the stack and returns it back to client
  public int pop() throws RemoteException {
    return serverStack.pop();
  }

  // Checks if the stack is empty, returns true if it is, false otherwise.
  public boolean isEmpty() throws RemoteException {
    return serverStack.empty();
  }

  // Delay the pop operation by the specified input in milliseconds.
  public int delayPop(int millis) throws RemoteException {

    try {
      Thread.sleep(millis);
    } catch (Exception e) {
      System.out.println(e);
    }
    return pop();

  }

  // Returns the current server stack.
  public Stack<Integer> getStack() throws RemoteException {
    return serverStack;
  }

}
