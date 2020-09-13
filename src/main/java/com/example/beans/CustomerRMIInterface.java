package com.example.beans;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CustomerRMIInterface extends Remote {
    List<Customer> getAllCustomers() throws RemoteException;
}
