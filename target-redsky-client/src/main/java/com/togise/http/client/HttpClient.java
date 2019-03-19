package com.togise.http.client;

import java.io.InputStream;

public interface HttpClient {

    InputStream get(String url);

}
