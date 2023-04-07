package com.sankuai.avatar;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.web.exception.SupportErrorException;
import com.sankuai.avatar.web.util.AuthHeaderUtil;
import com.sankuai.avatar.web.vo.ApiResponse;
import junit.framework.TestCase;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
 * MockMvc 测试基类
 * @author caoyang
 * @create 2022-11-03 17:41
 */
public abstract class MockMvcTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    static List<ResultMatcher> resultMatcherList;
    static List<ResultHandler> resultHandlerList;
    static {
        resultMatcherList = Arrays.asList(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.code").value(0),
                MockMvcResultMatchers.jsonPath("$.message").value("操作成功"),
                MockMvcResultMatchers.jsonPath("$.error").doesNotExist());
        resultHandlerList = Collections.singletonList(MockMvcResultHandlers.print());
    }

    /**
     * 组装请求
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
                throw new SupportErrorException("仅支持GET、POST、PUT、DELETE方法");

        }
    }

    /**
     * get api接口响应
     *
     * @param url               url
     * @param clz               接口返回数据类型
     * @param resultMatcherList 匹配器结果列表
     * @param resultHandlerList 结果处理程序列表
     * @return {@link T}
     * @throws Exception 异常
     */
    protected <T> T getMock(String url,
                            Class<T> clz,
                            List<ResultMatcher> resultMatcherList,
                            List<ResultHandler> resultHandlerList) throws Exception {
        ResultActions resultActions = this.mockMvc.perform(makeRequest("GET", url, null));
        return toApiResponse(resultActions, resultMatcherList, resultHandlerList, clz);
    }

    protected <T> T getMock(String url, Class<T> clz, List<ResultMatcher> resultMatcherList) throws Exception {
        return getMock(url, clz, resultMatcherList, resultHandlerList);
    }

    protected <T> T getMock(String url, Class<T> clz) throws Exception {
        return getMock(url, clz, resultMatcherList, resultHandlerList);
    }

    /**
     * post api接口响应
     *
     * @param url               url
     * @param param             接口传参
     * @param clz               接口数据返回类型
     * @param resultMatcherList 匹配器结果列表
     * @param resultHandlerList 结果处理程序列表
     * @return {@link T}
     * @throws Exception 异常
     */
    protected <K,T> T postMock(String url, K param, Class<T> clz,
                               List<ResultMatcher> resultMatcherList,
                               List<ResultHandler> resultHandlerList) throws Exception {
        ResultActions resultActions = this.mockMvc.perform(makeRequest("POST", url, JsonUtil.bean2Json(param)));
        return toApiResponse(resultActions, resultMatcherList, resultHandlerList, clz);
    }

    protected <K,T> T postMock(String url, K param, Class<T> clz,
                               List<ResultMatcher> resultMatcherList) throws Exception {
        return postMock(url, param, clz, resultMatcherList, resultHandlerList);
    }

    protected <K,T> T postMock(String url, K param, Class<T> clz) throws Exception {
        return postMock(url, param, clz, resultMatcherList, resultHandlerList);
    }

    /**
     * PUT接口请求
     *
     * @param url               url
     * @param param             参数
     * @param clz               类型
     * @param resultMatcherList 匹配器结果列表
     * @param resultHandlerList 结果处理程序列表
     * @return {@link T}
     * @throws Exception 异常
     */
    protected <K,T> T putMock(String url, K param, Class<T> clz,
                               List<ResultMatcher> resultMatcherList,
                               List<ResultHandler> resultHandlerList) throws Exception {
        ResultActions resultActions = this.mockMvc.perform(makeRequest("PUT", url, JsonUtil.bean2Json(param)));
        return toApiResponse(resultActions, resultMatcherList, resultHandlerList, clz);
    }

    protected <K,T> T putMock(String url, K param, Class<T> clz,
                               List<ResultMatcher> resultMatcherList) throws Exception {
        return putMock(url, param, clz, resultMatcherList, resultHandlerList);
    }

    protected <K,T> T putMock(String url, K param, Class<T> clz) throws Exception {
        return putMock(url, param, clz, resultMatcherList, resultHandlerList);
    }

    /**
     * DELETE接口请求
     *
     * @param url               url
     * @param param             参数
     * @param clz               类型
     * @param resultMatcherList 匹配器结果列表
     * @param resultHandlerList 结果处理程序列表
     * @return {@link T}
     * @throws Exception 异常
     */
    protected <K,T> T deleteMock(String url, K param, Class<T> clz,
                               List<ResultMatcher> resultMatcherList,
                               List<ResultHandler> resultHandlerList) throws Exception {
        ResultActions resultActions = this.mockMvc.perform(makeRequest("DELETE", url, JsonUtil.bean2Json(param)));
        return toApiResponse(resultActions, resultMatcherList, resultHandlerList, clz);
    }

    protected <K,T> T deleteMock(String url, K param, Class<T> clz,
                               List<ResultMatcher> resultMatcherList) throws Exception {
        return deleteMock(url, param, clz, resultMatcherList, resultHandlerList);
    }

    protected <K,T> T deleteMock(String url, K param, Class<T> clz) throws Exception {
        return deleteMock(url, param, clz, resultMatcherList, resultHandlerList);
    }



    /**
     * api响应json串转为换接口返回的数据对象
     *
     * @param resultActions     结果行动
     * @param resultMatcherList 匹配器结果列表
     * @param resultHandlerList 结果处理程序列表
     * @param clz               类型
     * @return {@link T}
     * @throws Exception 异常
     */
    private <T> T toApiResponse(ResultActions resultActions,
                                List<ResultMatcher> resultMatcherList,
                                List<ResultHandler> resultHandlerList,
                                Class<T> clz) throws Exception {
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

}
