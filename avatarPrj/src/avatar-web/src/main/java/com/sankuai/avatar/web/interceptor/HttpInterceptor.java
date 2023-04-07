package com.sankuai.avatar.web.interceptor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankuai.oceanus.http.filter.OceanusHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Author: qinwei05
 */

@Component
@Slf4j
public class HttpInterceptor implements HandlerInterceptor {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        request.setAttribute("cat-page-uri", request.getMethod() + " " + pattern);
        String appkey;
        if (Objects.nonNull(request.getHeader("oceanus-auth"))
            && StringUtils.isNotEmpty(appkey = OceanusHttpUtils.getClientAppkey())
            && StringUtils.isNotEmpty(pattern)) {
            Transaction t = Cat.newTransaction("URL.OpenAPI", String.format("%s::%s::%s ", appkey, request.getMethod(), StringUtils.strip(pattern, "/")));
            request.setAttribute("transaction", t);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            // 外部服务调用打点
            if (Objects.nonNull(request.getHeader("oceanus-auth"))
                && StringUtils.isNotEmpty(OceanusHttpUtils.getClientAppkey())
                && StringUtils.isNotEmpty(pattern)) {
                Transaction t = (Transaction) request.getAttribute("transaction");
                t.complete();
                request.removeAttribute("transaction");
            }
            // 404 Url不存在打点
            if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
                if (!Arrays.asList("/error", "/status", "/index.html").contains(request.getRequestURI())) {
                    Cat.logEvent("URL.404_NOT_FOUND", request.getMethod() + " " + request.getRequestURI());
                }
            }
            // 5XX 异常打点
            else if (response.getStatus() >= HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
                Cat.logEvent("URL.5XX_ERROR", response.getStatus() + " " + request.getMethod() + " " + pattern);
            }
        } catch (Exception e) {
            Cat.logError(e);
        }
    }

    private static String getClientAppkey(String tokenSignature){
        if (StringUtils.isBlank(tokenSignature)) {
            return "";
        }
        String clientAppkey = "";
        try {
            String[] str = tokenSignature.split("\\.");
            if (str.length!=3) {
                return "";
            }
            JsonNode jsonNode = mapper.readTree(new String(DatatypeConverter.parseBase64Binary(str[1])));
            clientAppkey = jsonNode.path("ns").asText();
        } catch (Exception e){
            log.debug("process token-signature failed, tokenSignature is : " + tokenSignature, e);
        }
        return clientAppkey;
    }
}

