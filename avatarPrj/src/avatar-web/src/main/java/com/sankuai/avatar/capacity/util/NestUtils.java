package com.sankuai.avatar.capacity.util;

import com.sankuai.avatar.capacity.dto.NestResponse;
import com.sankuai.oceanus.http.client.okhttp.OceanusInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shujian on 2020/2/18.
 */
public class NestUtils {
    private static final String Host = "http://nest.sankuai.com";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new OceanusInterceptor("com.sankuai.nest.portal"))
            .build();

    public static Map getDisasterRecoveryLevels() throws IOException {
        String url = Host + "/openApi/disasterRecoveryLevels";
        Request req = new Request.Builder().get().url(url).build();
        Response res = okHttpClient.newCall(req).execute();

        if (res.isSuccessful()) {
            NestResponse nr = GsonUtils.deserialization(res.body().string().toString(), NestResponse.class);

            if (nr.getCode() == 200 && "ok".equals(nr.getState())) {
                return nr.getData();
            }
        }
        return new HashMap<String, Double>();
    }
}
