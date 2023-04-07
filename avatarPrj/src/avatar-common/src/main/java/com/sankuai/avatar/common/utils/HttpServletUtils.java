package com.sankuai.avatar.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * HttpServlet 工具类
 *
 * @author zhaozhifan02
 */
public class HttpServletUtils {

    /**
     * 线上域名
     */
    private static final List<String> MWS_DOMAINS = Arrays.asList("avatar.sankuai.com", "avatar.vip.sankuai.com");

    /**
     * 线下api网关域名
     */
    private static final List<String> TEST_API_GATEWAY_DOMAINS = Collections.singletonList("inf-openapi.apigw.test.sankuai.com");

    /**
     * 线上api网关域名
     */
    private static final List<String> PROD_API_GATEWAY_DOMAINS = Collections.singletonList("inf-openapi.vip.sankuai.com");

    /**
     * 线上mws域名
     */
    private static final String MWS_DOMAIN = "avatar.mws.sankuai.com";

    /**
     * 线下mws域名
     */
    private static final String TEST_DOMAIN = "avatar.mws.cloud.test.sankuai.com";

    public static String getDomainName() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes());
        if (servletRequestAttributes == null) {
            return null;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String domain = request.getServerName();
        if (MWS_DOMAINS.contains(domain) || PROD_API_GATEWAY_DOMAINS.contains(domain)) {
            domain = MWS_DOMAIN;
        }
        if (TEST_API_GATEWAY_DOMAINS.contains(domain)) {
            domain = TEST_DOMAIN;
        }
        return domain;
    }

    public static String getSourceIp() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes());
        if (servletRequestAttributes == null) {
            return null;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getRemoteAddr();
    }
}
