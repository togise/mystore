package com.togise.product.price.repository.dynamodb

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal
import com.togise.product.price.repository.Price
import com.togise.product.price.repository.ProductPrice
import com.togise.product.price.repository.ProductPriceRepository
import io.redskap.java.aws.dynamodb.example.local.testing.AwsDynamoDbLocalTestUtils
import spock.lang.Shared
import spock.lang.Specification

import static com.togise.product.price.repository.Price.Currency.USD

class ProductPriceDynamoDBRepoTest extends Specification {

    @Shared
    DynamoDB dynamoDB

    @Shared
    AmazonDynamoDBLocal amazonDynamoDBLocal

    def setupSpec() {
        AwsDynamoDbLocalTestUtils.initSqLite()
        amazonDynamoDBLocal = DynamoDBEmbedded.create()
        AmazonDynamoDB client = amazonDynamoDBLocal.amazonDynamoDB()
        dynamoDB = new DynamoDB(client)
    }

    def cleanupSpec() {
        amazonDynamoDBLocal.shutdown()
    }

    def "test createTable"() {
        when:
        ProductPriceDynamoDBRepo.createTable(dynamoDB)

        then:
        ProductPriceDynamoDBRepo.isTableExists(dynamoDB)
    }

    def "test table does not exist"() {
        given:
        AwsDynamoDbLocalTestUtils.initSqLite()
        AmazonDynamoDBLocal amazonDynamoDBLocal = DynamoDBEmbedded.create()
        AmazonDynamoDB client = amazonDynamoDBLocal.amazonDynamoDB()
        DynamoDB dynamoDB = new DynamoDB(client)

        when:
        boolean result = ProductPriceDynamoDBRepo.isTableExists(dynamoDB)

        then:
        !result
    }

    def "test put and get"() {
        ProductPriceRepository productRepository = ProductPriceDynamoDBRepo.createNewInstance(dynamoDB)
        ProductPrice product = new ProductPrice(
                new Price(USD, new BigDecimal("22.44")),
                123
        )

        when:
        productRepository.putProductPrice(product)

        then:
        productRepository.getProductPrice(product.id) == product

    }
}
