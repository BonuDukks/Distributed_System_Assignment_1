import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

public interface CalculatorMultiStack extends Remote {

  // Gives the client a user ID and intialises their stack.
  int intialiseClient() throws RemoteException;

  // Pushes the argument value onto stack.
  void pushValue(int val, int userId) throws RemoteException;

  // Pushes 1 of 4 different operators onto the stack.
  void pushOperation(String operator, int userId) throws RemoteException;

  // Pops the top element of stack and returns it.
  int pop(int userId) throws RemoteException;

  // Checks if stack is empty, returning true if it is, and false if not.
  boolean isEmpty(int userId) throws RemoteException;

  // Server will wait user input in milliseconds before doing a pop operation.
  int delayPop(int millis, int userId) throws RemoteException;

  // Retrieves the current stack on the server
  Stack<Integer> getStack(int userId) throws RemoteException;
}
