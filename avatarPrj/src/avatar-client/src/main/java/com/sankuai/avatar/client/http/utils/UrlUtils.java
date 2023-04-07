package com.sankuai.avatar.client.http.utils;

import com.sankuai.avatar.common.exception.client.SdkCallException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author qinwei05
 * @date 2022/11/15 15:57
 */

public class UrlUtils {

    private UrlUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final char SPLIT = '/';

    public static String getRequestURI(String url) {
        int length = url.length();
        StringBuilder sb = new StringBuilder(length);

        for (int index = 0; index < length; ) {
            char c = url.charAt(index);

            if (c == SPLIT && index < length - 1) {
                sb.append(c);

                StringBuilder nextSection = new StringBuilder();
                boolean isNumber = false;
                boolean first = true;

                for (int j = index + 1; j < length; j++) {
                    char next = url.charAt(j);

                    if ((first || isNumber) && next != SPLIT) {
                        isNumber = isNumber(next);
                        first = false;
                    }

                    if (next == SPLIT) {
                        if (isNumber) {
                            sb.append("{num}");
                        } else {
                            sb.append(nextSection);
                        }
                        index = j;
                        break;
                    } else if (j == length - 1) {
                        if (isNumber) {
                            sb.append("{num}");
                        } else {
                            nextSection.append(next);
                            sb.append(nextSection);
                        }
                        index = j + 1;
                        break;
                    } else {
                        nextSection.append(next);
                    }
                }
            } else {
                sb.append(c);
                index++;
            }
        }

        return sb.toString();
    }

    private static boolean isNumber(char c) {
        return (c >= '0' && c <= '9') || c == '.' || c == '-' || c == ',';
    }

    public static String getFullUrl(String baseUrl, String urlPath) {
        String fullUrl;
        if (urlPath == null) {
            if (baseUrl != null) {
                fullUrl = baseUrl;
            } else {
                throw new SdkCallException("在设置 BaseUrl 之前，您必须指定具体路径才能发起请求！");
            }
        } else {
            return buildFullUrl(baseUrl, urlPath);
        }
        return fullUrl;
    }

    private static String buildFullUrl(String baseUrl, String urlPath) {
        String fullUrl;
        urlPath = urlPath.trim();
        boolean isFullPath = urlPath.startsWith("https://") || urlPath.startsWith("http://");
        if (isFullPath) {
            fullUrl = urlPath;
        } else if (baseUrl != null) {
            fullUrl = baseUrl + urlPath;
        } else {
            throw new SdkCallException("在设置 BaseUrl 之前，您必须使用全路径URL发起请求，当前URL为：'" + urlPath + "'");
        }
        return fullUrl;
    }

    private static StringBuilder buildpathParams(StringBuilder sb, String urlPath, Map<String, String> pathParams){
        if (pathParams != null) {
            pathParams.forEach((name, value) -> {
                String target = "{" + name + "}";
                int start = sb.indexOf(target);
                if (start >= 0) {
                    String newValue = value != null ? value : "";
                    sb.replace(start, start + target.length(), newValue);
                } else {
                    throw new SdkCallException("PathPara [ " + name + " ] 不存在于 url [ " + urlPath + " ]");
                }
            });
        }
        return sb;
    }

    private static StringBuilder buildUrlParams(StringBuilder sb, Map<String, String> urlParams){
        if (urlParams == null){
            return sb;
        }
        // contains("?")
        if (sb.indexOf("?") >= 0) {
            int lastIndex = sb.length() - 1;
            // !endsWith("?")
            if (sb.lastIndexOf("?") < lastIndex) {
                if (sb.lastIndexOf("=") < sb.lastIndexOf("?") + 2) {
                    throw new SdkCallException("url 格式错误，'?' 后没有发现 '='");
                }
                // !endsWith("&")
                if (sb.lastIndexOf("&") < lastIndex) {
                    sb.append('&');
                }
            }
        } else {
            sb.append('?');
        }
        urlParams.forEach((name, value) -> {
            if (value == null) {
                return;
            }
            sb.append(name).append('=').append(value).append('&');
        });
        sb.delete(sb.length() - 1, sb.length());
        return sb;
    }

    public static String buildUrl(String urlPath, Map<String, String> pathParams, Map<String, String> urlParams) {
        if (StringUtils.isBlank(urlPath)) {
            throw new SdkCallException("url 不能为空！");
        }
        StringBuilder sb = new StringBuilder(urlPath);
        StringBuilder sbWithPathParams = buildpathParams(sb, urlPath, pathParams);
        StringBuilder sbWithUrlParams = buildUrlParams(sbWithPathParams, urlParams);
        return sbWithUrlParams.toString();
    }
}
