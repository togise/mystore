package com.togise.product.repository.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal
import com.togise.product.repository.Price
import com.togise.product.repository.Product
import com.togise.product.repository.ProductRepository
import io.redskap.java.aws.dynamodb.example.local.testing.AwsDynamoDbLocalTestUtils
import spock.lang.Shared
import spock.lang.Specification

import static com.togise.product.repository.Price.Currency.USD

class ProductDynamoDBRepoTest extends Specification {

    @Shared
    DynamoDB dynamoDB

    def setupSpec() {
        AwsDynamoDbLocalTestUtils.initSqLite()
        AmazonDynamoDBLocal amazonDynamoDBLocal = DynamoDBEmbedded.create()
        AmazonDynamoDB client = amazonDynamoDBLocal.amazonDynamoDB()
        dynamoDB = new DynamoDB(client)
    }

    def "test createTable"() {
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

    def "test put and get"() {
        ProductRepository productRepository = ProductDynamoDBRepo.createNewInstance(dynamoDB)
        Product product = new Product(
                new Price(USD, new BigDecimal("22.44")),
                "test product name",
                123
        )

        when:
        productRepository.putProduct(product)

        then:
        productRepository.getProduct(product.id) == product

    }
}
