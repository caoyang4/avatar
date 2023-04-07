package com.sankuai.avatar.client.http.utils;

import com.dianping.lion.Environment;
import com.google.common.collect.Maps;
import com.sankuai.inf.patriot.client.Auth;
import com.sankuai.inf.patriot.config.AuthConfig;
import com.sankuai.inf.patriot.process.client.DefaultAuth;
import com.sankuai.oceanus.http.internal.HttpHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author qinwei05
 * @date 2022/11/22 11:53
 */
@Slf4j
public class OceanusAuthUtils {

    private static Auth auth;

    static {
        String appName = Environment.getAppName();
        try {
            auth = new DefaultAuth(appName, new AuthConfig());
        } catch (Exception e) {
            log.error("Failed to init auth", e);
        }
    }

    private static String getSignature(String remoteAppKey) {
        if (StringUtils.isBlank(remoteAppKey)) {
            return "";
        }

        if (auth == null) {
            throw new RuntimeException("failed to init auth");
        }
        return StringUtils.trimToEmpty(auth.getTokenSignature(remoteAppKey));
    }

    public static Map<String, String> getUniformAuthHeaders(String remoteAppKey) {
        Map<String, String> headers = Maps.newHashMap();
        headers.put(HttpHeader.OCEANUS_RMOTE_APPKEY_HEADER, remoteAppKey);
        headers.put(HttpHeader.OCEANUS_AUTH_HEADER, getSignature(remoteAppKey));
        return headers;
    }
}
