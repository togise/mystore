package com.togise.http.client

import spock.lang.Specification

class SimpleHttpClientTest extends Specification {

    def "test getProductInfo"() {
        given:
        String url = "http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics"
        SimpleHttpClient redskyHttpClient = new SimpleHttpClient()
        when:
        InputStream stream = redskyHttpClient.get(url)
        then:
        stream.available()
    }

    def "test not found"() {
        String url = "http://redsky.target.com/v2/pdp/tci"
        SimpleHttpClient redskyHttpClient = new SimpleHttpClient()
        when:
        Optional<InputStream> stream = redskyHttpClient.get(url)
        then:
        thrown(SimpleHttpClient.SimpleHttpGetRequest.NotFoundException)
    }
}
