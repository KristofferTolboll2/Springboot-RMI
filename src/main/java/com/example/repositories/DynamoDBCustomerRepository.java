package com.example.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.example.beans.Customer;
import com.example.beans.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class DynamoDBCustomerRepository implements CrudRepository<Customer, String, Transaction>{

    private AmazonDynamoDB dbInstance;


    public DynamoDBCustomerRepository() {
        this.dbInstance = DynamoDBProvider.createInstance();
    }

    @Override
    public String create(Customer customer) {
        DynamoDB dynamoDB = new DynamoDB(this.dbInstance);
        Table table = dynamoDB.getTable("BankCustomer");

        System.out.println(customer);

        Item item = new Item()
                .withPrimaryKey("id", customer.getId())
                .withString("name", customer.getName())
                .withString("password", customer.getPassword())
                .withString("email", customer.getEmail())
                .withFloat("balance", customer.getBalance())
                .withList("transactions", customer.getTransactionList());

        table.putItem(item);
        System.out.println("Inserted item " + item.toJSON());
        return item.toJSON();
    }

    @Override
    public String delete(String id) {
        DynamoDB dynamoDB = new DynamoDB(this.dbInstance);
        Table table = dynamoDB.getTable("BankCustomer");

        DeleteItemSpec dir = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("id", id));

        try {
            System.out.println("attempting to delete item with id: " + id);
            table.deleteItem(dir);
            System.out.println("Successfully deleted item");

        }catch (Exception e){
            System.err.println(e.getMessage());
            return e.getMessage();
        }
        String response = "Successfully deleted entry with id: " + id;
        return response;


    }


    //this function,can actually be used as a generic function both for updation
    //and for creation
    @Override
    public String update(String s, Customer customer) throws Exception {
        //we configure the mapper to update
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(this.dbInstance, new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.UPDATE));
        if(s.length() > 0){
            throw new Exception("Id is not provided correctlly");
        }
        customer.setId(s);
        dynamoDBMapper.save(customer);
        return "Successfully updated body of user " + s;
    }



    @Override
    public Customer get(String identifier) {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(this.dbInstance);
        Customer customer = dynamoDBMapper.load(Customer.class, identifier);
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        // Change to your Table_Name (you can load dynamically from lambda env as well)
        DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder().withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement("BankCustomer")).build();

        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(this.dbInstance, mapperConfig);

        PaginatedList<Customer> customers =  dynamoDBMapper.scan(Customer.class, new DynamoDBScanExpression());
        return new ArrayList<>(customers);
    }

    @Override
    public List<Transaction> getAllAttributes(String identifier) {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(this.dbInstance);
        Customer customer = dynamoDBMapper.load(Customer.class, identifier);
        return customer.getTransactionList();
    }

    @Override
    public String createAttribute(Transaction attribute, String identifier) {
        DynamoDB dynamoDB = new DynamoDB(this.dbInstance);
        Table table = dynamoDB.getTable("BankCustomer");

        attribute.generateSendDate();
        attribute.generateId();

        HashMap<String, java.io.Serializable> transactionData = new HashMap<String, java.io.Serializable>();
        transactionData.put("id", attribute.getId());
        transactionData.put("amount", attribute.getAmount());
        transactionData.put("message", attribute.getMessage());
        transactionData.put("receiver", attribute.getReceiver());
        transactionData.put("sendDate", attribute.getSendDate());

        ValueMap map = new ValueMap().withList(":transaction", Arrays.asList(transactionData));

        System.out.println(map);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("id", identifier)
                .withUpdateExpression("SET transactions = list_append(if_not_exists(transactions, :transaction), :transaction)")
                .withValueMap(map);


        UpdateItemOutcome result = table.updateItem(updateItemSpec);
        return result.toString();
    }


}
