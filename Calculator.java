import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

public interface Calculator extends Remote {
  // Pushes the argument value onto stack.
  void pushValue(int val) throws RemoteException;

  // Pushes 1 of 4 different operators onto the stack.
  void pushOperation(String operator) throws RemoteException;

  // Pops the top element of stack and returns it.
  int pop() throws RemoteException;

  // Checks if stack is empty, returning true if it is, and false if not.
  boolean isEmpty() throws RemoteException;

  // Server will wait user input in milliseconds before doing a pop operation.
  int delayPop(int millis) throws RemoteException;

  // Retrieves the current stack on the server
  Stack<Integer> getStack() throws RemoteException;
}