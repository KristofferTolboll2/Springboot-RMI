import com.example.controllers.CustomerController;
import com.example.repositories.DynamoDBCustomerRepository;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIRegistry {

    public static Registry registry;
    public RMIRegistry() throws Exception { }

    public static void main(String[] args) {
        try
        {
            System.out.println("RMI server localhost starts");

            // Create a server registry at default port 1099
            registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry created ");

            DynamoDBCustomerRepository dynamoDBCustomerRepository = new DynamoDBCustomerRepository();
            // Create engine of remote services, running on the server
            CustomerController customerController = new CustomerController(dynamoDBCustomerRepository);

            System.out.println(customerController.getAllCustomers());

            // Give a name to this engine
            String engineName = "customer";

            // Register the engine by the name, which later will be given to the clients
            Naming.rebind("//localhost/" + engineName, customerController);
            System.out.println("Engine " + engineName + " bound in registry");
        }
        catch (Exception e)
        {
            System.err.println("Exception:" + e);
        }
    }
}


