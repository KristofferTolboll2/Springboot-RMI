package com.example.repositories;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.beans.DynamoDBEntry;

import java.util.List;

public interface CrudRepository<T, I, A> {

    public I create(T t);

    public I delete(I i);

    public I update (I i, T t);

    public T get(I identifier);

    public List<T> getAll();

    public List<A> getAllAttributes(I identifier);

    public String createAttribute(A attribute, I identifier);

}
