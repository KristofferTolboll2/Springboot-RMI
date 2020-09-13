import com.example.beans.Customer;
import com.example.beans.CustomerRMIInterface;
import com.example.controllers.CustomerController;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class RMIClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String remoteEngine = "rmi://localhost/customer";

        CustomerRMIInterface customerController = (CustomerRMIInterface) Naming.lookup(remoteEngine);

        List<Customer> customers = customerController.getAllCustomers();
        customers.forEach(entry -> System.out.println(entry.toString()));
    }
}
