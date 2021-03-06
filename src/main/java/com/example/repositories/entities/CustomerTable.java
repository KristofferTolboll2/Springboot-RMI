package com.example.repositories.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "BankCustomers")
public class CustomerTable {

    private String id;
    private String name;
    private int age;
    private String email;
    private float balance;

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId(){
        return id;
    }

    @DynamoDBAttribute
    public String getName(){
        return name;
    }

    @DynamoDBAttribute
    public int getAge(){
        return age;
    }

    @DynamoDBAttribute
    public String getEmail(){
        return email;
    }

    @DynamoDBAttribute
    public float getBalance(){
        return balance;
    }


}
