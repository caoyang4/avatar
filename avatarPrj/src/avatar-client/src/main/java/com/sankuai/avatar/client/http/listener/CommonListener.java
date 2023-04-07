package com.sankuai.avatar.client.http.listener;

import com.sankuai.avatar.client.http.core.HttpResult;
import com.sankuai.avatar.client.http.core.HttpStatusEnum;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 一些常用监听器，大多是验证状态、格式
 *
 * @author xk
 */
public class CommonListener {

    private CommonListener() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * HTTP请求失败状态码解析
     *
     * @return {@link HttpEventListener}
     */
    public static HttpEventListener explainHttpFailureStatusCode() {
        return new HttpEventListener() {
            @Override
            public void httpFailure(HttpResult httpResult) {
                int httpStatusCode = httpResult.getStatusCode();
                if (httpStatusCode == 0){
                    throw new SdkCallException(httpStatusCode, httpResult.getState(),
                            String.format("请求异常：url：%s，错误信息为：%s", httpResult.getRequest().url(), httpResult.getError().getMessage()),
                            httpResult.getError());
                } else {
                    try {
                        HttpStatusEnum httpStatusEnum = HttpStatusEnum.valueOf(httpStatusCode);
                        String errorMessage = String.format("请求失败! Http状态码为: [%s](%s -> %s), 请求返回信息为: %s",
                                httpStatusCode, httpStatusEnum.reasonPhraseUS(),
                                httpStatusEnum.reasonPhraseCN(), httpResult.getResponseString());
                        throw new SdkCallException(httpStatusCode, errorMessage, httpResult.getError());
                    } catch (IllegalArgumentException e){
                        // 未匹配到预置状态码时，直接抛出异常，不进行状态码释义
                        String errorMessage = String.format("请求失败! Http状态码为: [%s], 请求返回信息为: %s",
                                httpStatusCode, httpResult.getResponseString());
                        throw new SdkCallException(httpStatusCode, errorMessage, httpResult.getError());
                    }
                }
            }
        };
    }

    /**
     * 解析http业务状态码
     * HTTP请求成功后业务状态码解析
     * 比如：CODE == 0 或 10000 代表业务逻辑返回正常，否则异常
     * 请注意：jsonPath对应的code码类型必须为整数
     *
     * @param jsonPath     json路径
     * @param businessCode 业务code状态码
     * @return {@link HttpEventListener}
     */
    public static HttpEventListener explainHttpBusinessCode(String jsonPath, Integer businessCode) {
        return new HttpEventListener() {
            @Override
            public void httpAfter(HttpResult httpResult) {
                String responseString = httpResult.getResponseString();
                String jsonData = JsonUtil.readValUsingJsonPath(responseString, jsonPath);
                if (StringUtils.isBlank(jsonData)){
                    throw new SdkBusinessErrorException(Integer.parseInt(jsonData),
                            String.format("请求 %s 业务逻辑返回异常，jsonPath: %s 对应值为空",
                                    httpResult.getRequest().url(), jsonPath));
                }
                if (!Objects.equals(businessCode, Integer.parseInt(jsonData))) {
                    throw new SdkBusinessErrorException(Integer.parseInt(jsonData),
                            String.format("请求 %s 业务逻辑返回异常，返回值为 %s",
                            httpResult.getRequest().url(), responseString));
                }
            }
        };
    }

    /**
     * 验证请求返回体指定字段值不为空
     * 使用场景：验证返回数据的某个字段值是否不为空
     *
     * @param jsonPath json路径
     * @return {@link HttpEventListener}
     */
    public static HttpEventListener isNotBlank(String jsonPath) {
        return new HttpEventListener() {
            @Override
            public void httpAfter(HttpResult httpResult) {
                int httpStatusCode = httpResult.getStatusCode();
                String responseString = httpResult.getResponseString();
                String jsonData = JsonUtil.readValUsingJsonPath(responseString, jsonPath);
                if (StringUtils.isBlank(jsonData)) {
                    throw new SdkBusinessErrorException(httpStatusCode, "请求错误：返回值为空");
                }
            }
        };
    }

    /**
     * 验证请求返回体指定字段值为空
     * 使用场景：验证返回数据的某个字段值是否为空
     *
     * @param jsonPath json路径
     * @return {@link HttpEventListener}
     */
    public static HttpEventListener isBlank(String jsonPath) {
        return new HttpEventListener() {
            @Override
            public void httpAfter(HttpResult httpResult) {
                int httpStatusCode = httpResult.getStatusCode();
                String responseString = httpResult.getResponseString();
                String jsonData = JsonUtil.readValUsingJsonPath(responseString, jsonPath);
                if (StringUtils.isNotBlank(jsonData)) {
                    throw new SdkBusinessErrorException(httpStatusCode, jsonData);
                }
            }
        };
    }

    /**
     * 验证请求返回体指定字段值是否为预期值
     *
     * @param expected 预期值
     * @param jsonPath json路径
     * @return {@link HttpEventListener}
     */
    public static HttpEventListener equals(Object expected, String jsonPath) {
        return new HttpEventListener() {
            @Override
            public void httpAfter(HttpResult httpResult) {
                int httpStatusCode = httpResult.getStatusCode();
                String responseString = httpResult.getResponseString();
                String jsonData = JsonUtil.readValUsingJsonPath(responseString, jsonPath);
                if (!Objects.equals(String.valueOf(expected), jsonData)) {
                    throw new SdkBusinessErrorException(httpStatusCode,
                            String.format("请求错误：实际返回值%s 与预期返回值 %s 不同", jsonData, expected));
                }
            }
        };
    }
}
