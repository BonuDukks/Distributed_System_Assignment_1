import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Stack;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {

  private Stack<Integer> serverStack = new Stack<>();

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

}
