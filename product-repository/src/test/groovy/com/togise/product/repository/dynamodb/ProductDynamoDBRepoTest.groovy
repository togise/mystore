package com.togise.product.repository.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal
import com.togise.product.repository.ProductRepository
import io.redskap.java.aws.dynamodb.example.local.testing.AwsDynamoDbLocalTestUtils
import spock.lang.Specification

class ProductDynamoDBRepoTest extends Specification {
    def "test createTable"() {
        given:
        AwsDynamoDbLocalTestUtils.initSqLite()
        AmazonDynamoDBLocal amazonDynamoDBLocal = DynamoDBEmbedded.create()
        AmazonDynamoDB client = amazonDynamoDBLocal.amazonDynamoDB()
        DynamoDB dynamoDB = new DynamoDB(client)

        when:
        ProductRepository productRepository = ProductDynamoDBRepo.createTable(dynamoDB)

        then:
        ProductDynamoDBRepo.isTableExists(dynamoDB)
    }

    def "test table does not exist"() {
        given:
        AwsDynamoDbLocalTestUtils.initSqLite()
        AmazonDynamoDBLocal amazonDynamoDBLocal = DynamoDBEmbedded.create()
        AmazonDynamoDB client = amazonDynamoDBLocal.amazonDynamoDB()
        DynamoDB dynamoDB = new DynamoDB(client)

        when:
        boolean result = ProductDynamoDBRepo.isTableExists(dynamoDB)

        then:
        !result
    }
}
