package com.togise.http.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class SimpleHttpClient implements HttpClient {

    private static final String GET = "GET";

    private final int readTimeout;
    private final int connectTimeout;

    public SimpleHttpClient() {
        this(1000, 5000);
    }

    private SimpleHttpClient(int readTimeout, int connectTimeout) {
        this.readTimeout = readTimeout;
        this.connectTimeout = connectTimeout;
    }

    @Override
    public InputStream get(String url) {
        SimpleHttpGetRequest request = SimpleHttpUrlConnection.connect(url, readTimeout, connectTimeout).makeGetRequest();
        if(request.isRequestRedirected()) {
            return get(request.getRedirectUrl());
        }
        if(request.isRequestError()) {
            throw request.newHttpRequestException();
        } else {
            return request.handleSuccess();
        }
    }


    private static class SimpleHttpUrlConnection {

        private final HttpURLConnection connection;

        private SimpleHttpUrlConnection(HttpURLConnection connection) {
            this.connection = connection;
        }

        private static SimpleHttpUrlConnection connect(String url, int readTimeout, int connectTimeout) {
            try {

                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(connectTimeout);
                connection.setReadTimeout(readTimeout);
                return new SimpleHttpUrlConnection(connection);
            } catch (IOException e) {
                throw new HttpConnectionException(url, e);
            }
        }

        private SimpleHttpGetRequest makeGetRequest() {
            return new SimpleHttpGetRequest(connection);
        }

        private static class HttpConnectionException extends RuntimeException {
            private HttpConnectionException(String url, Throwable cause) {
                super("Failed to open connection to Target Redsky API for " + url, cause);
            }
        }
    }


    public static class SimpleHttpGetRequest {

        private final HttpURLConnection connection;

        private SimpleHttpGetRequest(HttpURLConnection connection) {
            this.connection = connection;
            try {
                connection.setRequestMethod(GET);
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
        }

        private InputStream handleSuccess() {
            try {
                if(getHttpStatusCode() == HTTP_NOT_FOUND) {
                    throw new NotFoundException();
                } else {
                    return connection.getInputStream();
                }
            } catch (IOException e) {
                throw new HttpRequestException(connection.getURL(), e);
            }
        }

        private boolean isRequestRedirected() {
            return getHttpStatusCode() >= 300;
        }

        private boolean isRequestError() {
            return connection.getErrorStream() != null && getHttpStatusCode() != HTTP_NOT_FOUND;
        }

        private int getHttpStatusCode() {
            try {
                return connection.getResponseCode();
            } catch (IOException e) {
                throw new HttpRequestException(connection.getURL(), e);
            }
        }

        private RuntimeException newHttpRequestException() {
            try (Scanner s = new Scanner(getErrorStream()).useDelimiter("\\A")) {
                String errorMessage = s.hasNext() ? s.next() : "";
                return new HttpRequestException(connection.getResponseCode(), connection.getURL(), errorMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private InputStream getErrorStream() {
            try(InputStream inputStream = connection.getErrorStream()) {
                if(inputStream != null && inputStream.available() > 0)
                    return inputStream;
                else
                    throw new IllegalStateException("Could not determine the state of the response for request url " + connection.getURL());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private String getRedirectUrl() {
            String url = connection.getHeaderField("Location");
            if(url != null)
                return url;
            else
                throw new NotFoundException();
        }

        public static class NotFoundException extends RuntimeException {

        }

        private static class HttpRequestException extends RuntimeException {

            private HttpRequestException(String errorMessage, Throwable cause) {
                super(errorMessage, cause);
            }

            private HttpRequestException(int statusCode, URL url, String errorResponse) {
                super("Failed request with " + statusCode + " when making a request to url " + url +
                        "Error message: \n" + errorResponse);
            }

            private HttpRequestException(URL url, Throwable cause) {
                super("Request failed when making a request to " + url, cause);
            }
        }
    }




}
