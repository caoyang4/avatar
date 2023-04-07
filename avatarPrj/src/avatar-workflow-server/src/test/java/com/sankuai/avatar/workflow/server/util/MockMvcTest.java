package com.sankuai.avatar.workflow.server.util;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.common.vo.ApiResponse;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * MockMvcTest 测试类，用于测试controller层接口, 模拟真实请求
 *
 * @author xk
 */

@Component
public class MockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 默认结果匹配器, 仅验证结果是否正常
     */
    private static List<ResultMatcher> resultMatcherList;

    /**
     * 默认结果处理器, 仅打印结果内容
     */
    private static List<ResultHandler> resultHandlerList;

    static {
        /*
        默认验证策略: http_status = 200 && code = 0 && message="操作成功" && error=""
         */
        resultMatcherList = Arrays.asList(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.code").value(0),
                MockMvcResultMatchers.jsonPath("$.message").value("操作成功"),
                MockMvcResultMatchers.jsonPath("$.error").doesNotExist());

        /*
        打印结果到标准输出
         */
        resultHandlerList = Collections.singletonList(MockMvcResultHandlers.print());
    }

    /**
     * 组装请求对象
     *
     * @param method  方法
     * @param url     url
     * @param content 接口传参
     * @return {@link MockHttpServletRequestBuilder}
     */
    private MockHttpServletRequestBuilder makeRequest(String method, String url, String content){
        switch (method){
            case "GET":
                return MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(AuthHeaderUtil.authHeader("GET", url));
            case "POST":
                return MockMvcRequestBuilders
                        .post(url)
                        .content(Objects.requireNonNull(content))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(AuthHeaderUtil.authHeader("POST", url));
            case "PUT":
                return MockMvcRequestBuilders
                        .put(url)
                        .content(Objects.requireNonNull(content))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(AuthHeaderUtil.authHeader("PUT", url));
            case "DELETE":
                return MockMvcRequestBuilders
                        .delete(url)
                        .content(Objects.requireNonNull(content))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(AuthHeaderUtil.authHeader("DELETE", url));
            default:
                throw new RuntimeException("仅支持GET、POST、PUT、DELETE方法");
        }
    }

    /**
     * 处理返回的请求结果，执行验证器、打印结果、反序列化等
     *
     * @param resultActions     结果行动
     * @param resultMatcherList 匹配器结果列表
     * @param resultHandlerList 结果处理程序列表
     * @param clz               类型
     * @return {@link T}
     * @throws Exception 异常
     */
    private <T> T resultProcessing(ResultActions resultActions, List<ResultMatcher> resultMatcherList, List<ResultHandler> resultHandlerList, Class<T> clz) throws Exception {
        for (ResultHandler resultHandler : resultHandlerList) {
            resultActions.andDo(resultHandler);
        }

        for (ResultMatcher resultMatcher : resultMatcherList) {
            resultActions.andExpect(resultMatcher);
        }

        MvcResult mvcResult = resultActions.andReturn();
        Assert.assertNotNull(mvcResult);
        ApiResponse apiResponse = JsonUtil.json2Bean(mvcResult.getResponse().getContentAsString(), ApiResponse.class);
        Assert.assertNotNull(apiResponse);
        return JsonUtil.json2Bean(JsonUtil.bean2Json(apiResponse.getData()), clz);
    }

    /**
     * 执行模拟请求
     *
     * @param method            方法
     * @param url               url
     * @param param             参数
     * @param clz               返回泛型
     * @param resultMatcherList 匹配器结果列表
     * @param resultHandlerList 结果处理程序列表
     * @return {@link T}
     * @throws Exception 异常
     */
    private <T> T executeRequest(
            String method,
            String url,
            String param,
            Class<T> clz,
            List<ResultMatcher> resultMatcherList,
            List<ResultHandler> resultHandlerList) throws Exception {
        ResultActions resultActions = this.mockMvc.perform(makeRequest(method, url, param));
        return resultProcessing(resultActions, resultMatcherList, resultHandlerList, clz);
    }

    /**
     * get请求
     *
     * @param url url
     * @param clz clz
     * @return {@link T}
     * @throws Exception 异常
     */
    public <T> T get(String url, Class<T> clz) throws Exception {
        return executeRequest("GET", url, null, clz, resultMatcherList, resultHandlerList);
    }

    /**
     * post请求
     *
     * @param url   url
     * @param param 参数
     * @param clz   clz
     * @return {@link T}
     * @throws Exception 异常
     */
    public  <T> T post(String url, Object param, Class<T> clz) throws Exception {
        return executeRequest("POST", url, JsonUtil.bean2Json(param), clz, resultMatcherList, resultHandlerList);
    }

    /**
     * put请求
     *
     * @param url   url
     * @param param 参数
     * @param clz   clz
     * @return {@link T}
     * @throws Exception 异常
     */
    public  <T> T put(String url, Object param, Class<T> clz) throws Exception {
        return executeRequest("PUT", url, JsonUtil.bean2Json(param), clz, resultMatcherList, resultHandlerList);
    }

    /**
     * delete请求
     *
     * @param url   url
     * @param param 参数
     * @param clz   clz
     * @return {@link T}
     * @throws Exception 异常
     */
    public  <T> T delete(String url, Object param, Class<T> clz) throws Exception {
        return executeRequest("DELETE", url, JsonUtil.bean2Json(param), clz, resultMatcherList, resultHandlerList);
    }

}
