package com.sankuai.avatar.capacity.util;

import com.sankuai.avatar.capacity.dto.ScMetaResponse;
import com.sankuai.oceanus.http.client.okhttp.OceanusInterceptor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author caoyang
 * @create 2022-08-23 15:20
 */
public class ScUtils {

    private static final String HOST = "https://sc.sankuai.com";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new OceanusInterceptor("com.sankuai.sc.openservice"))
            .build();

    public static boolean isJboxAppkey(String appkey){
        String url = HOST + "/open/api/v2/appkeys/meta";
        HttpUrl httpUrl = HttpUrl.parse(url).newBuilder()
                .addQueryParameter("appKeys", appkey)
                .build();
        Request req = new Request.Builder().get().url(httpUrl).build();
        try {
            Response res = okHttpClient.newCall(req).execute();
            ScMetaResponse smr = GsonUtils.deserialization(res.body().string(), ScMetaResponse.class);
            if (smr == null || smr.getCode() != 0 || smr.getData() == null || smr.getData().isEmpty()) {
                return false;
            }
            String st = (String) smr.getData().get(0).getOrDefault("serviceType","");
            return "JBox: Java".equals(st);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
