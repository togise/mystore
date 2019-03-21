package com.togise.mystore;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.togise.http.client.SimpleHttpClient;
import com.togise.product.repository.ProductRepository;
import com.togise.product.repository.dynamodb.ProductDynamoDBRepo;
import com.togise.redsky.client.NamingClient;
import com.togise.redsky.client.RedskyClient;
import io.redskap.java.aws.dynamodb.example.local.testing.AwsDynamoDbLocalTestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyStoreApplication.class, args);
	}

	@Bean
	public ProductRepository productRepository() {
		AwsDynamoDbLocalTestUtils.initSqLite();
		AmazonDynamoDBLocal amazonDynamoDBLocal = DynamoDBEmbedded.create();
		AmazonDynamoDB client = amazonDynamoDBLocal.amazonDynamoDB();
		DynamoDB dynamoDB = new DynamoDB(client);
		return ProductDynamoDBRepo.createNewInstance(dynamoDB);
	}

	@Bean
	public NamingClient namingClient() {
		return new RedskyClient(new SimpleHttpClient());
	}

}
