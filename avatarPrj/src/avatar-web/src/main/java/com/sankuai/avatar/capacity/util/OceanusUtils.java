package com.sankuai.avatar.capacity.util;

import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.sankuai.avatar.capacity.dto.OceanusAppkeyResponse;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shujian on 2020/2/27.
 *
 */
public class OceanusUtils {
    private static String host = "http://oceanus.sankuai.com";
    private static final Logger logger = LoggerFactory.getLogger(OceanusUtils.class);

    private static final HashMap<String, String> headers = new HashMap<String,String>() {{
        put("Authorization", "oceanus.ops");
        put("Content-Type", "application/json");
    }};

    public static List<String> getOceanusAppkeys(){
        // 这个接口不靠谱, 数据不一定同步
        String url = host + "/api/pool/";
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                OceanusAppkeysResponse oar = GsonUtils.deserialization(res.getBody(), OceanusAppkeysResponse.class);
                if (oar.getCode() == 200) {
                    return oar.getResult().getObjects();
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    @Data
    private class OceanusAppkeysResponse{
        private String msg;
        private Integer code;
        private OceanusAppkeysResponseResult result;
    }

    @Data
    private class OceanusAppkeysResponseResult{
        @SerializedName("objects")
        private List<String> objects;
    }


    public static boolean getIsAppkeyAccessOceanus(String appkey) {
        if (appkey.isEmpty()){
            return false;
        }
        String url = host + "/api/pool/" + appkey.trim();

        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                OceanusAppkeyResponse oar;
                try {
                    oar = GsonUtils.deserialization(res.getBody(), OceanusAppkeyResponse.class);
                    if (oar.getCode() == 200) {
                        return !oar.getResult().getSite().isEmpty();
                    }
                }catch (JsonSyntaxException e){
                    // {"msg": "服务(appkey)不存在或服务节点未同步，请稍后重试。", "code": 204, "result": "服务(appkey)不存在或服务节点未同步，请稍后重试。"}
                    logger.warn("解析 oceanus 数据出现异常, 当前内容为: "+ res.getBody());
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }


}
