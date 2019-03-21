package com.togise.product.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.togise.product.repository.Price;
import com.togise.product.repository.Product;
import com.togise.product.repository.ProductRepository;

public class ProductDynamoDBRepo implements ProductRepository {

    private static final String ATTR_NAME_ID = "Id";
    private static final String ATTR_NAME_CURRENT_PRICE_CURRENCY = "CurrentPriceCurrency";
    private static final String ATTR_NAME_CURRENT_PRICE = "CurrentPrice";
    private static final String TABLE_NAME = "PRODUCT";
    private final Table table;

    private ProductDynamoDBRepo(final DynamoDB dynamoDB) {
        this.table = dynamoDB.getTable(TABLE_NAME);
    }

    @Override
    public Product getProduct(int id) {
        Item item = table.getItem(ATTR_NAME_ID, id);

        ifItemNotFoundThrowNotFoundException(item, id);

        Price price = new Price(
                Price.Currency.valueOf(item.getString(ATTR_NAME_CURRENT_PRICE_CURRENCY)),
                item.getNumber(ATTR_NAME_CURRENT_PRICE));

        return new Product(price,
                item.getInt(ATTR_NAME_ID));
    }

    @Override
    public int putProduct(Product product) {
        Item item = new Item()
                .withPrimaryKey(ATTR_NAME_ID, product.getId())
                .withString(ATTR_NAME_CURRENT_PRICE_CURRENCY, product.getCurrentPrice().getCurrency().name())
                .withNumber(ATTR_NAME_CURRENT_PRICE, product.getCurrentPrice().getPrice());
        table.putItem(item);
        return product.getId();
    }

    public static ProductRepository createNewInstance(DynamoDB dynamoDB) {

        if(!isTableExists(dynamoDB)) {
            createTable(dynamoDB);
        }
        return new ProductDynamoDBRepo(dynamoDB);
    }

    static void createTable(DynamoDB dynamoDB) {
        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(TABLE_NAME)
                .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L))
                .withAttributeDefinitions(
                        new AttributeDefinition(ATTR_NAME_ID, ScalarAttributeType.N)
                ).withKeySchema(
                        new KeySchemaElement(ATTR_NAME_ID, KeyType.HASH)
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

    private static void ifItemNotFoundThrowNotFoundException(Item item, int id) {
        if(item == null)
            throw new ItemNotFoundException(id);
    }

    public static class ItemNotFoundException extends RuntimeException {
        public ItemNotFoundException(int id) {
            super("Item not found for id " + id);
        }
    }

}
