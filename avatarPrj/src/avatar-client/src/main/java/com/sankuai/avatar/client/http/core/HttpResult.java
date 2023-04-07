package com.sankuai.avatar.client.http.core;

import com.sankuai.avatar.common.constant.State;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * http结果
 * Http 执行结果
 *
 * @author qinwei05
 * @date 2022/11/10
 */
@SuppressWarnings("unused")
public interface HttpResult extends Convert {

	/**
	 * 构造一个HttpResult对象
	 *
	 * @param response 响应
	 * @return {@link HttpResult}
	 */
	static HttpResult of(Response response) {
		return new HttpResultImpl(response);
	}

	/**
	 * 获取原始 Response
	 *
	 * @return {@link Response}
	 */
	Response getResponse();

	/**
	 * 得到响应字符串
	 *
	 * @return {@link String}
	 */
	String getResponseString();

	/**
	 * 设置响应
	 *
	 * @param response 响应
	 */
	void setResponse(Response response);

	/**
	 * 获取原始 Request
	 *
	 * @return {@link Request}
	 */
	Request getRequest();

	/**
	 * 设置请求
	 *
	 * @param request 请求
	 */
	void setRequest(Request request);

	/**
	 * 获取http请求方法
	 *
	 * @return {@link String}
	 */
	String getHttpMethod();

	/**
	 * 获取执行状态
	 * @return 执行状态
	 */
	State getState();

	/**
	 * 获取HTTP状态码
	 * @return HTTP状态码
	 */
	int getStatusCode();

	/**
	 * 获取HTTP请求是否成功
	 * @return 是否响应成功，状态码在 [200..300) 之间
	 */
	boolean isSuccessful();

	/**
	 * 获取全部Header
	 * @return 所有响应头
	 */
	Map<String, List<String>> allHeaders();

	/**
	 * 获取KEY对应Header
	 * @param name 头名称
	 * @return 响应头
	 */
	String getHeader(String name);

	/**
	 * 获取请求错误异常
	 *
	 * @return {@link IOException}
	 */
	IOException getError();

}
