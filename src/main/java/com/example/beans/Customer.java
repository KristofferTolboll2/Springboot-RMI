package com.example.beans;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DynamoDBTable(tableName = "BankCustomer")
public class Customer extends DynamoDBEntry<Customer> implements Serializable {

    private String id;
    private String name;
    private int age;
    private String email;
    private float balance;

    private List<Transaction> transactionList = new ArrayList<>();

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", transactionList=" + transactionList +
                ", password='" + password + '\'' +
                '}';
    }




    @DynamoDBAttribute(attributeName = "transactions")
    public List<Transaction> getTransactionList() {
        return this.transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    

    public Customer() {
    }

    @Override
    public String generateId() {
        String id = UUID.randomUUID().toString();
        this.setId(id);
        return id;
    }

    public Customer(String name, int age, String email, String password, float balance, List<Transaction> transactionList) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.transactionList = transactionList;
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "balance")
    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}