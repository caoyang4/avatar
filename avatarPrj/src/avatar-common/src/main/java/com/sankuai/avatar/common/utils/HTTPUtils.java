package com.sankuai.avatar.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by shujian on 2020/2/12.
 *
 */


@Slf4j
public class HTTPUtils {
    public static HttpResult get(String url) throws IOException {
        return get(url, null);
    }

    public static HttpResult get(String url, Map<String, String> headerParams, int readTimeout, int connectionTimeout) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = getConnection(url, headerParams, readTimeout, connectionTimeout);
            if (200 == connection.getResponseCode()) {
                String response = drain(connection.getInputStream(), "UTF-8");
                //完全正常的调用
                return new HttpResult(HttpURLConnection.HTTP_OK, response);
            } else {
                String message = drain(connection.getErrorStream(), "UTF-8");
                //错误的调用
                return new HttpResult(connection.getResponseCode(), message);
            }
        } catch (IOException e) {
            //异常调用
            throw e;
        } finally {
            //处理transaction，以及资源。
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static HttpResult get(String url, Map<String, String> headerParams) throws IOException {
        return get(url, headerParams, 3000, 1000);
    }

    public static HttpResult retryGet(String url, int retryThreshold) throws IOException {
        return retryGet(url, retryThreshold, null);
    }

    public static HttpResult retryGet(String url, int retryThreshold, Map<String, String> headerParams) throws IOException {
        return retryGet(url, retryThreshold, headerParams, 3000, 1000);
    }

    public static HttpResult retryGet(String url, int retryThreshold, Map<String, String> headerParams, int readTimeout) throws IOException{
        return retryGet(url, retryThreshold, headerParams, readTimeout, 3000);
    }

    public static HttpResult retryGet(String url, int retryThreshold, Map<String, String> headerParams, int readTimeout, int connectionTimeout) throws IOException {
        HttpResult result = null;
        int retry = 0;
        while (retry < retryThreshold) {
            retry++;
            try {
                result = get(url, headerParams, readTimeout, connectionTimeout);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                if (retry >= retryThreshold) {
                    log.error("http get failed. url:{} msg:{}", url, e.getMessage());
                    throw e;
                } else {
                     log.warn("http get retry failed. url:{} retry:{} msg:{}", url, retry, e.getMessage());
                }
            }
        }
        return result;
    }

    public static HttpResult post(String url, String content) throws IOException {
        return post(url, content, null, 3000, 1000);
    }

    public static HttpResult post(String url, String content, Map<String, String> headerParams, int readTimeout, int connectTimeout) throws IOException {
        HttpURLConnection connection = null;

        try {
            connection = postConnection(url, content, headerParams, readTimeout, connectTimeout);
            if (200 == connection.getResponseCode()) {
                String response = drain(connection.getInputStream(), "UTF-8");
                return new HttpResult(HttpURLConnection.HTTP_OK, response);
            } else {
                String message = drain(connection.getErrorStream(), "UTF-8");
                return new HttpResult(connection.getResponseCode(), message);
            }
        } catch (IOException e) {
            //异常调用
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static HttpResult retryPost(String url, int retryThreshold, String content) throws IOException {
        return retryPost(url, retryThreshold, content, null, 3000, 1000);
    }

    public static HttpResult retryPost(String url, int retryThreshold, String content, Map<String, String> headerParams, int readTimeout, int connectTimeout) throws IOException {
        HttpResult result = null;
        int retry = 0;
        while (retry < retryThreshold) {
            retry++;
            try {
                result = post(url, content, headerParams, readTimeout, connectTimeout);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                if (retry >= retryThreshold) {
                    log.error("http post failed. url:{} msg:{}", url, e.getMessage());
                    throw e;
                } else {
                     log.warn("http post retry failed. url:{} retry:{} msg:{}", url, retry, e.getMessage());
                }
            }
        }
        return result;
    }

    public static HttpResult put(String url, String content, Map<String, String> headerParams, int readTimeout, int connectTimeout) throws IOException {
        HttpURLConnection connection = null;

        try {
            connection = putConnection(url, content, headerParams, readTimeout, connectTimeout);
            if (200 == connection.getResponseCode()) {
                String response = drain(connection.getInputStream(), "UTF-8");
                return new HttpResult(HttpURLConnection.HTTP_OK, response);
            } else {
                String message = drain(connection.getErrorStream(), "UTF-8");
                return new HttpResult(connection.getResponseCode(), message);
            }
        } catch (IOException e) {
            //异常调用
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static HttpResult retryPut(String url, int retryThreshold, String content, Map<String, String> headerParams) throws IOException {
        return retryPut(url, retryThreshold, content, headerParams, 30000, 30000);
    }

    public static HttpResult retryPut(String url, int retryThreshold, String content, Map<String, String> headerParams, int readTimeout, int connectTimeout) throws IOException {
        HttpResult result = null;
        int retry = 0;
        while (retry < retryThreshold) {
            retry++;
            try {
                result = put(url, content, headerParams, readTimeout, connectTimeout);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                if (retry >= retryThreshold) {
                    log.error("http put failed. url:{} msg:{}", url, e.getMessage());
                    throw e;
                } else {
                     log.warn("http put retry failed. url:{} retry:{} msg:{}", url, retry, e.getMessage());
                }
            }
        }
        return result;
    }

    private static HttpURLConnection getConnection(String url, Map<String, String> headerParams, int readTimeout, int connectTimeout) throws IOException {
        URL getUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.setReadTimeout(readTimeout);
        connection.setConnectTimeout(connectTimeout);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "utf-8");
        //connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Charset", "UTF-8");
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return connection;
    }

    private static HttpURLConnection putConnection(String url, String content, Map<String, String> headerParams, int readTimeout, int connectTimeout) throws IOException {
        URL putUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) putUrl.openConnection();
        connection.setReadTimeout(readTimeout);
        connection.setConnectTimeout(connectTimeout);
        connection.setRequestMethod("PUT");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");
        connection.setRequestProperty("Charset", "UTF-8");
        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(connection.getOutputStream());
            dos.write(content.getBytes("UTF-8"));
            dos.flush();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            if (dos != null) {
                dos.close();
            }
        }
        return connection;
    }

    private static HttpURLConnection postConnection(String url, String content, Map<String, String> headerParams, int readTimeout, int connectTimeout) throws IOException {
        URL postUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setReadTimeout(readTimeout);
        connection.setConnectTimeout(connectTimeout);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");
        connection.setRequestProperty("Charset", "UTF-8");

        if (headerParams != null) {
            for (Map.Entry<String, String> entry : headerParams.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(connection.getOutputStream());
            dos.write(content.getBytes("UTF-8"));
            dos.flush();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            if (dos != null) {
                dos.close();
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
}
