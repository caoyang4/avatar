package com.sankuai.avatar.client.http.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author qinwei05 (ว ˙o˙)ง
 * @date 2022/10/18 16:29
 * @version 1.0
 */

public class TimeoutInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        int connectTimeout = chain.connectTimeoutMillis();
        int readTimeout = chain.readTimeoutMillis();
        int writeTimeout = chain.writeTimeoutMillis();

        String connectNew = request.header("CustomConnectTimeout");
        String readNew = request.header("CustomReadTimeout");
        String writeNew = request.header("CustomWriteTimeout");

        // removeHeader(Custom Header)
        Request.Builder requestNewBuilder = request.newBuilder();
        if (!TextUtils.isEmpty(connectNew)) {
            connectTimeout = Integer.parseInt(connectNew);
            requestNewBuilder.removeHeader("CustomConnectTimeout");
        }
        if (!TextUtils.isEmpty(readNew)) {
            readTimeout = Integer.parseInt(readNew);
            requestNewBuilder.removeHeader("CustomReadTimeout");
        }
        if (!TextUtils.isEmpty(writeNew)) {
            writeTimeout = Integer.parseInt(writeNew);
            requestNewBuilder.removeHeader("CustomWriteTimeout");
        }
        return chain.withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .proceed(requestNewBuilder.build());
    }
}
