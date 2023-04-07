package com.sankuai.avatar.capacity.util;

import com.dianping.lion.client.util.AuthUtil;
import com.sankuai.avatar.capacity.dto.BladeResponse;

import java.io.IOException;
import java.util.*;

/**
 * Created by shujian on 2020/2/28.
 *
 */
public class BladeUtils {
    private static final String Host = "https://resource-blade.vip.sankuai.com";

    public static Double getBladeStandardLevel(String appKeys){
        String uri = "/api/v1/cluster/get_objective_grade";
        String url = String.format("%s%s?appkeys=%s", Host,uri,appKeys).trim();
        String date = AuthUtil.getAuthDate(new Date());
        String authorization = AuthUtil.getAuthorization(uri, "GET", date, "avatar", "fpxmlj2fj4tjg01cn9vesuj2lsgjs8da");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", authorization);
        headers.put("Date", date);
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 10000);
            if (res.isSuccess()){
                BladeResponse bladeResponse = GsonUtils.deserialization(res.getBody(), BladeResponse.class);
                if (bladeResponse.getCode() == 0 && bladeResponse.getData().size() > 0){
                    HashMap<String, ?> map = bladeResponse.getData().get(0);
                    if (map.get("object_capacity") != null){
                        return (Double) map.get("object_capacity");
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return 0.0;
    }
}
