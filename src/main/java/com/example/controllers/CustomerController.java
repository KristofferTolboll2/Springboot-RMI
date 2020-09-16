package com.example.controllers;


import com.example.beans.Customer;
import com.example.beans.CustomerRMIInterface;
import com.example.beans.DynamoDBEntry;
import com.example.beans.Transaction;
import com.example.repositories.DynamoDBCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

@RestController
public class CustomerController extends UnicastRemoteObject  implements CustomerRMIInterface {


    final
    DynamoDBCustomerRepository dynamoDBRepository;

    public CustomerController(DynamoDBCustomerRepository dynamoDBRepository) throws RemoteException {
        this.dynamoDBRepository = dynamoDBRepository;
    }



    @RequestMapping("/")
    public String getUser(){
        return "Hello from " + CustomerController.class.toString();
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomers(){
        return dynamoDBRepository.getAll();
    }

    @PostMapping(value = "/customer")
    public String insertCustomer(@RequestBody Customer customer){
        customer.generateId();
        String jsonDocument = dynamoDBRepository.create(customer);
        return jsonDocument;
    }

    @GetMapping(value = "/customer/{id}")
    public Customer getCustomer(@PathVariable String id){
        return dynamoDBRepository.get(id);
    }

    @DeleteMapping(value = "/customer/{id}")
    public String deleteCustomer(@PathVariable String id){
        String response = dynamoDBRepository.delete(id);
        return response;
    }

    @GetMapping(value = "/customer/{id}/transaction")
    public List<Transaction> getTransactions(@PathVariable String id){
        return dynamoDBRepository.getAllAttributes(id);
    }

    @PostMapping(value = "/customer/{id}/transaction")
    public String createTransaction(@PathVariable String id, @RequestBody Transaction transction){
        String response = dynamoDBRepository.createAttribute(transction, id);
        return response;
    }


}
