public class CalculatorServer {
  public static void main(String[] args) {
    try {
      // Create and bind the server object to the RMI registry
      CalculatorImplementation calculator = new CalculatorImplementation();
      java.rmi.registry.LocateRegistry.createRegistry(1099);
      java.rmi.Naming.rebind("Calculator", calculator);

      System.out.println("Server is running...");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
