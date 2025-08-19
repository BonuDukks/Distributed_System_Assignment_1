public class CalculatorMultiStackServer {
  public static void main(String[] args) {
    try {
      // Create and bind the server object to the RMI registry
      CalculatorMultiStackImplementation calculatorMultiStack = new CalculatorMultiStackImplementation();
      java.rmi.registry.LocateRegistry.createRegistry(1099);
      java.rmi.Naming.rebind("CalculatorMultiStack", calculatorMultiStack);

      System.out.println("Server is running...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
