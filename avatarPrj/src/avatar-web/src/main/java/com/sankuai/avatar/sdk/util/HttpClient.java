package com.sankuai.avatar.sdk.util;

import org.apache.http.client.utils.URIBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpClient {
    public static HttpResult get(String url) throws IOException {
        return get(url, null);
    }

    public static HttpResult get(String url, Map<String, String> headerParams) throws IOException {
        return get(url, headerParams, 10000, 2000);
    }
    public static HttpResult get(String url, Map<String, String> headerParams, int readTimeout, int connectionTimeout) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = getConnection(url, headerParams, readTimeout, connectionTimeout);
            if (200 == connection.getResponseCode()) {
                String response = drain(connection.getInputStream(), "UTF-8");
                return new HttpResult(HttpURLConnection.HTTP_OK, response);
            } else {
                String message = drain(connection.getErrorStream(), "UTF-8");
                return new HttpResult(connection.getResponseCode(), message);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static HttpURLConnection getConnection(String url, Map<String, String> headerParams, int readTimeout, int connectTimeout) throws IOException {
        URL getUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.setReadTimeout(readTimeout);
        connection.setConnectTimeout(connectTimeout);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Charset", "UTF-8");
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return connection;
    }

    private static String drain(InputStream stream, String encoding) throws IOException {
        if (stream == null) {
            return null;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream, encoding));
            StringBuilder result = new StringBuilder(256);
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static class HttpResult {
        private int code;
        private String body;

        public HttpResult(int code, String body) {
            this.code = code;
            this.body = body;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public boolean isSuccess() {
            return code == HttpURLConnection.HTTP_OK;
        }
    }

    public static URIBuilder addParameter(URIBuilder uriBuilder, Map<String, Object> paramMap) {
        for (Map.Entry<String, Object> entry: paramMap.entrySet()) {
            try {
                uriBuilder.addParameter(entry.getKey(), entry.getValue().toString());
            }
            catch (NullPointerException e) {
                continue;
            }
        }
        return uriBuilder;
    }
}
