import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Stack;
import java.util.HashMap;

public class CalculatorMultiStackImplementation extends UnicastRemoteObject implements CalculatorMultiStack {

  // Intialise variable that gives clients an ID
  private int userId = 0;

  // Intialises HashMap that contains stack for each user.
  private HashMap<Integer, Stack<Integer>> newServerStack = new HashMap<>();

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
  public CalculatorMultiStackImplementation() throws RemoteException {
    super();
  }

  // Function that new clients call, which increments the user ID on the server,
  // maps a stack to their given user ID, and returns the ID used back to client
  // to pass in for other functions.
  public int intialiseClient() throws RemoteException {
    userId++;
    Stack<Integer> userStack = new Stack<>();
    newServerStack.put(userId, userStack);
    return userId;
  }

  // Push value onto stack, uses passed in user ID to search for client's stack in
  // HashMap
  public void pushValue(int val, int userId) throws RemoteException {
    newServerStack.get(userId).push(val);
  }

  // Uses a switch case to handle user input of the four operators. "min pops all
  // the values and pushes the min back. "max" pops all values and pushes the max
  // back. "lcm" pops all values and pushes the lcm back. "gcd" pops all values
  // and pushes the gcd back. Uses passed in user ID to search for client's stack
  // in HashMap
  public void pushOperation(String operator, int userId) throws RemoteException {

    Stack<Integer> userStack = newServerStack.get(userId);

    if (userStack.size() == 1) {
      return;
    }

    switch (operator) {
      case "min":
        int min = userStack.peek();
        while (!userStack.empty()) {
          int poppedValue = userStack.pop();
          if (poppedValue < min) {
            min = poppedValue;
          }
        }
        userStack.push(min);

      case "max":
        int max = userStack.peek();
        while (!userStack.empty()) {
          int poppedValue = userStack.pop();
          if (poppedValue > max) {
            max = poppedValue;
          }
        }
        userStack.push(max);

      case "lcm":

        // If only 1 value on stack, breaks so no error occurs.
        if (userStack.size() == 1) {
          break;
        }

        int firstNumb = userStack.pop();
        int secondNumb = userStack.pop();

        int lcmResult = getLCM(firstNumb, secondNumb);

        while (!userStack.empty()) {
          lcmResult = getLCM(userStack.pop(), lcmResult);
        }

        userStack.push(lcmResult);

      case "gcd":

        // If only 1 value on stack, breaks so no error occurs.
        if (userStack.size() == 1) {
          break;
        }

        int firstValue = userStack.pop();
        int secondValue = userStack.pop();

        int result = getGCD(firstValue, secondValue);

        while (!userStack.empty()) {
          result = getGCD(userStack.pop(), result);
        }

        userStack.push(result);
    }
  }

  // Pops the top element from the stack and returns it back to client.
  // Uses passed in user ID to search for client's stack in HashMap
  public int pop(int userId) throws RemoteException {
    return newServerStack.get(userId).pop();
  }

  // Checks if the stack is empty, returns true if it is, false otherwise.
  // Uses passed in user ID to search for client's stack in HashMap
  public boolean isEmpty(int userId) throws RemoteException {
    return newServerStack.get(userId).empty();
  }

  // Delay the pop operation by the specified input in milliseconds.
  // Uses passed in user ID to search for client's stack in HashMap
  public int delayPop(int millis, int userId) throws RemoteException {

    try {
      Thread.sleep(millis);
    } catch (Exception e) {
      System.out.println(e);
    }
    return pop(userId);

  }

  // Returns the current server stack.
  // Uses passed in user ID to search for client's stack in HashMap
  public Stack<Integer> getStack(int userId) throws RemoteException {
    return newServerStack.get(userId);
  }

}
