package com.example.repositories;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDBProvider {

    public static AmazonDynamoDB createInstance() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(new AWSCredentials() {
                    //should be read in from .env file
                    @Override
                    public String getAWSAccessKeyId() {
                        return "AKIAJPCDG5VBKVMUIB2Q";
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return "NN4whvMUTKb2gRvWeRa+1Xu9Hm2K67hzePzUkqPP";
                    }
                }))
                .build();
    }

}
