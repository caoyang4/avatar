package com.sankuai.avatar.client.http.core;

import com.jayway.jsonpath.TypeRef;
import com.sankuai.avatar.common.constant.State;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.utils.JsonUtil;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author xk
 */
public class HttpResultImpl implements HttpResult {

    private static final Charset charset = StandardCharsets.UTF_8;

    private State state;

    private IOException error;

    private Response response;

    /**
     * 响应体字符串
     * OkHttp底层response.body().string() 只能调用一次，为了可以重复使用，保存response String <br>
     *  <a href="https://github.com/square/okhttp/issues/1240#issuecomment-329197762">issues</a>
     */
    private String responseString;

    private Request request;

    public HttpResultImpl(Request request){
        this.request = request;
    }

    public HttpResultImpl(Response response){
        this.response = response;
    }

    public void exception(State state, IOException error) {
        this.state = state;
        this.error = error;
    }

    @Override
    public Response getResponse() {
        return response;
    }

    @Override
    public String getResponseString() {
        if (StringUtils.isBlank(this.responseString)){
            this.responseString = responseToString();
        }
        return this.responseString;
    }

    private String responseToString(){
        try {
            if (this.response == null) {
                return "";
            }
            ResponseBody body = this.response.body();
            if (body != null) {
                return new String(body.bytes(), charset);
            }
        } catch (IOException e) {
            this.response.close();
            throw new SdkCallException("报文体转化字符串出错", e);
        }
        return "";
    }

    @Override
    public void setResponse(Response response) {
        this.state = State.SUCCESS;
        this.response = response;
    }

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String getHttpMethod() {
        return this.request.method();
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public int getStatusCode() {
        if (response != null) {
            return response.code();
        }
        return 0;
    }

    @Override
    public boolean isSuccessful() {
        if (response != null) {
            return this.response.isSuccessful();
        }
        return false;
    }

    @Override
    public Map<String, List<String>> allHeaders() {
        return this.request.headers().toMultimap();
    }

    @Override
    public String getHeader(String name) {
        return this.request.header(name);
    }

    @Override
    public IOException getError() {
        return error;
    }

    @Override
    public <T> T toBean(Class<T> type, String jsonPath) {
        return JsonUtil.jsonPath2Bean(getResponseString(), type, jsonPath);
    }

    @Override
    public <T> List<T> toBeanList(Class<T> type, String jsonPath) {
        return JsonUtil.jsonPath2List(getResponseString(), type, jsonPath);
    }

    @Override
    public <T> T toBean(Class<T> type) {
        return JsonUtil.json2Bean(getResponseString(), type);
    }

    @Override
    public <T> T toBean(TypeRef<T> beanType) {
        return JsonUtil.jsonPath2NestedBean(getResponseString(), beanType);
    }

    @Override
    public <T> T toBean(TypeRef<T> type, String jsonPath) {
        return JsonUtil.jsonPath2NestedBean(getResponseString(), type, jsonPath);
    }
}
