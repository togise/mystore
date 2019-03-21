package com.togise.redsky.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.togise.http.client.HttpClient;

import java.io.IOException;
import java.io.InputStream;

public class RedskyClient implements NamingClient {

    private static final String DEFAULT_URL = "http://redsky.target.com/v2/pdp/tcin/$s?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    private final HttpClient httpClient;
    private final String baseRequestUrl;
    private final ObjectMapper objectMapper;

    public RedskyClient(HttpClient client) {
        this(client, DEFAULT_URL, new ObjectMapper());
    }

    RedskyClient(HttpClient client, String baseRequestUrl, ObjectMapper objectMapper) {
        this.httpClient = client;
        this.baseRequestUrl = baseRequestUrl;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getProductName(int id) {
        try (InputStream inputStream = this.httpClient.get(String.format(baseRequestUrl, id))){
            JsonNode node = objectMapper.readTree(inputStream);
            return node.get("product")
                    .get("item")
                    .get("product_description")
                    .get("title")
                    .asText();
        } catch (IOException e) {
            throw new RedskyClientJsonParseException(e);
        }
    }

    static class RedskyClientJsonParseException extends RuntimeException {
        private RedskyClientJsonParseException(IOException e) {
            super(e);
        }
    }
}
