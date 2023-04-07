package com.sankuai.avatar.sdk.manager.impl;

import com.dianping.cat.Cat;
import com.dianping.lion.Environment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.sdk.entity.servicecatalog.ResponseResult;
import com.sankuai.avatar.sdk.manager.ServiceCatalogHttpClient;
import com.sankuai.avatar.sdk.util.HttpClient;
import com.sankuai.inf.patriot.client.Auth;
import com.sankuai.inf.patriot.config.AuthConfig;
import com.sankuai.inf.patriot.process.client.DefaultAuth;
import com.sankuai.oceanus.http.internal.HttpHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jie.li.sh
 * @create 2020-03-17
 **/

@Slf4j
@Service
public class ServiceCatalogHttpClientImpl implements ServiceCatalogHttpClient {
    @MdpConfig("SERVICECATALOG_DOMAIN:http://service.fetc.test.sankuai.com")
    private String serviceCatalogDomain;
    private String serviceCatalogAppkey = "dolphin-web";
    private static Auth auth = null;

    @Autowired
    private ObjectMapper objectMapper;

    private static void setAuth() {
        if (ServiceCatalogHttpClientImpl.auth == null) {
            String appName = Environment.getAppName();
            try {
                ServiceCatalogHttpClientImpl.auth = new DefaultAuth(appName, new AuthConfig());
            } catch (Exception e) {
                log.error(String.format("failed to init auth, localAppKey:[%s]", appName), e);
            }
        }
    }

    @PostConstruct
    public void init() {
        setAuth();
    }

    private String buildPath(String path) {
        return String.format("%s%s", serviceCatalogDomain, path);
    }

    private JsonNode buildResult(String method, URIBuilder uriBuilder, HttpClient.HttpResult httpResult) throws Exception {
        if (httpResult.isSuccess()) {
            ResponseResult result = null;
            try {
                result = objectMapper.readValue(httpResult.getBody(), ResponseResult.class);
                if (result.isSuccess()) {
                    return result.getData();
                }
                {
                    throw new Exception(result.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Cat.logError(e);
                throw new Exception(e.getMessage());
            }
        }
        Exception exception = new Exception(String.format("[SC请求] URL: %s, Method: %s, ResCode: %s",
                uriBuilder.toString(), method, String.valueOf(httpResult.getCode())));
        Cat.logError(exception);
        throw exception;
    }

    private byte[] get(String path, Map<String, Object> params) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(buildPath(path));
        if (params != null) {
            HttpClient.addParameter(uriBuilder, params);
        }
        Map<String, String> headers = new HashMap<>();
        getUniformAuthHeaders(headers, serviceCatalogAppkey);
        HttpClient.HttpResult httpResult = HttpClient.get(uriBuilder.build().toURL().toString(), headers);
        return objectMapper.writeValueAsBytes(buildResult("GET", uriBuilder, httpResult));
    }

    @Override
    public <T> T get(String path, Class<T> clazz) throws Exception {
        return get(path, null, clazz);
    }

    @Override
    public <T> T get(String path, Map<String, Object> params, Class<T> clazz) throws Exception {
        return objectMapper.readValue(get(path, params), clazz);
    }

    @Override
    public <T> T get(String path, Map<String, Object> params, TypeReference<T> typeReference) throws Exception {
        // Jackson 从 2.9.x 升级到 2.10.x:  ObjectMapper 类中反序列化 API 中参数带有 TypeReference 需要明确指定泛型参数类型
        return objectMapper.readValue(get(path, params), typeReference);
    }

    @Override
    public <T> List<T> getListData(String path, Class<T> clazz) throws Exception {
        return getListData(path, null, clazz);
    }

    @Override
    public <T> List<T> getListData(String path, Map<String, Object> params, Class<T> clazz) throws Exception {
        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return objectMapper.readValue(get(path, params), javaType);
    }

    private static Map<String, String> getUniformAuthHeaders(Map<String, String> headers, String remoteAppKey) {
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put(HttpHeader.OCEANUS_RMOTE_APPKEY_HEADER, remoteAppKey);
        headers.put(HttpHeader.OCEANUS_AUTH_HEADER, getSignature(remoteAppKey));
        return headers;
    }

    private static String getSignature(String remoteAppKey) {
        if (StringUtils.isBlank(remoteAppKey)) {
            return "";
        }
        if (null == auth) {
            throw new RuntimeException("failed to init auth");
        }

        return StringUtils.trimToEmpty(auth.getTokenSignature(remoteAppKey));
    }
}
