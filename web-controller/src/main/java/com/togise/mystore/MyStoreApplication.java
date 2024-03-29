package com.togise.mystore;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.togise.http.client.SimpleHttpClient;
import com.togise.product.price.repository.Price;
import com.togise.product.price.repository.ProductPrice;
import com.togise.product.price.repository.ProductPriceRepository;
import com.togise.product.price.repository.dynamodb.ProductPriceDynamoDBRepo;
import com.togise.redsky.client.NamingClient;
import com.togise.redsky.client.RedskyClient;
import io.redskap.java.aws.dynamodb.example.local.testing.AwsDynamoDbLocalTestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static com.togise.product.price.repository.Price.Currency.USD;

@SpringBootApplication
public class MyStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyStoreApplication.class, args);
	}

	@Bean
	public ProductPriceRepository productRepository() {
		AwsDynamoDbLocalTestUtils.initSqLite();
		AmazonDynamoDBLocal amazonDynamoDBLocal = DynamoDBEmbedded.create();
		AmazonDynamoDB client = amazonDynamoDBLocal.amazonDynamoDB();
		DynamoDB dynamoDB = new DynamoDB(client);
		ProductPriceRepository repository = ProductPriceDynamoDBRepo.createNewInstance(dynamoDB);
		loadFakeData(repository);
		return repository;
	}

	@Bean
	public NamingClient namingClient() {
		return new RedskyClient(new SimpleHttpClient());
	}

	private void loadFakeData(ProductPriceRepository repository) {
		ProductPrice product = new ProductPrice(
				new Price(USD, new BigDecimal("22.44")),
				13860428
		);
		repository.putProductPrice(product);
	}

}
