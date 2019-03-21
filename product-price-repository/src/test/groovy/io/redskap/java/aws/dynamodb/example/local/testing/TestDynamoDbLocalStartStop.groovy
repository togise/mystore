package io.redskap.java.aws.dynamodb.example.local.testing

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model.*
import spock.lang.Specification

/**
 * Reference: https://github.com/aws-samples/aws-dynamodb-examples/blob/master/src/test/java/com/amazonaws/services/dynamodbv2/local/embedded/DynamoDBEmbeddedTest.java
 */
class TestDynamoDbLocalStartStop extends Specification {
    def "test stop start" () {
        AwsDynamoDbLocalTestUtils.initSqLite()
        AmazonDynamoDB ddb = DynamoDBEmbedded.create().amazonDynamoDB()
        String tableName = "Movies"
        String hashKeyName = "film_id"

        when:
        CreateTableResult res = createTable(ddb, tableName, hashKeyName)

        then:
        res.getTableDescription().tableName == tableName
        ddb.listTables().tableNames.size() == 1

    }

    private static CreateTableResult createTable(AmazonDynamoDB ddb, String tableName, String hashKeyName) {
        List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>()
        attributeDefinitions.add(new AttributeDefinition(hashKeyName, ScalarAttributeType.S))

        List<KeySchemaElement> ks = new ArrayList<KeySchemaElement>()
        ks.add(new KeySchemaElement(hashKeyName, KeyType.HASH))

        ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput(1000L, 1000L)

        CreateTableRequest request =
                new CreateTableRequest()
                        .withTableName(tableName)
                        .withAttributeDefinitions(attributeDefinitions)
                        .withKeySchema(ks)
                        .withProvisionedThroughput(provisionedthroughput)

        return ddb.createTable(request)
    }

}