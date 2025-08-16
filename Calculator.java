import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

public interface Calculator extends Remote {
  void pushValue(int val) throws RemoteException;

  void pushOperation(String operator) throws RemoteException;

  int pop() throws RemoteException;

  boolean isEmpty() throws RemoteException;

  int delayPop(int millis) throws RemoteException;

  Stack<Integer> getStack() throws RemoteException;
}