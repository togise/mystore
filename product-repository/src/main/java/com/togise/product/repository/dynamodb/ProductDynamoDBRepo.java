package com.togise.product.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.*;
import com.togise.product.repository.Product;
import com.togise.product.repository.ProductRepository;

public class ProductDynamoDBRepo implements ProductRepository {

    private static final String TABLE_NAME = "PRODUCT";
    private final DynamoDB dynamoDB;
    private final Table table;

    private ProductDynamoDBRepo(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
        this.table = dynamoDB.getTable(TABLE_NAME);
    }

    @Override
    public Product getProduct(int id) {
        return null;
    }

    private static ProductRepository createNewInstance(DynamoDB dynamoDB) {

        if(isTableExists(dynamoDB)) {
            createTable(dynamoDB);
        }
        return new ProductDynamoDBRepo(dynamoDB);
    }

    static void createTable(DynamoDB dynamoDB) {
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(TABLE_NAME)
                .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L))
                .withAttributeDefinitions(
                        new AttributeDefinition("id", ScalarAttributeType.N)
                ).withKeySchema(
                        new KeySchemaElement("id", KeyType.HASH)
                );
        dynamoDB.createTable(createTableRequest);
    }

    static boolean isTableExists(DynamoDB dynamoDB) {
        try {
            dynamoDB.getTable(TABLE_NAME).describe();
        } catch (ResourceNotFoundException e) {
            return false;
        }
        return true;
    }


}
