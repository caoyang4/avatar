package com.sankuai.avatar.capacity.util;

import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.capacity.dto.Whitelist;
import com.sankuai.avatar.capacity.dto.WhitelistResponse;
import com.sankuai.oceanus.http.client.okhttp.OceanusInterceptor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxinli
 */
@Component
public class AvatarUtils {

    @MdpConfig("AVATAR_DOMAIN:https://avatar.vip.sankuai.com")
    private static String Host;

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new OceanusInterceptor("com.sankuai.avatar.web"))
            .build();


    public static List<Whitelist> getAppKeyWhitelist(String appKey) throws Exception {
        String url = Host + "/api/v2/avatar/whitelist";
        HttpUrl httpUrl = HttpUrl.parse(url).newBuilder()
                .addQueryParameter("appkey", appKey)
                .build();
        Request req = new Request.Builder().get().url(httpUrl).build();
        Response res = okHttpClient.newCall(req).execute();

        if (res.isSuccessful()) {
            WhitelistResponse whitelistResponse = GsonUtils.deserialization(res.body().string().toString(), WhitelistResponse.class);
            if (whitelistResponse.getCode() == 0) {
                return whitelistResponse.getData().getItems();
            }
        }
        return new ArrayList<>();
    }
}
