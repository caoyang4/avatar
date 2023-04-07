package com.sankuai.avatar.capacity.util;

import com.dianping.cat.Cat;
import com.sankuai.avatar.capacity.dto.AreaZoneResponse;
import com.sankuai.avatar.capacity.dto.RegionZoneResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-12-26 15:50
 */
public class TerraUtils {

    private static final String Host = "https://terra.sankuai.com";
    private static final HashMap<String, String> headers = new HashMap<String, String>(){{
        put("token", "942e6a8784ec11ed9d0cacde48001122");
    }};

    public static List<RegionZoneResponse> getRegionZone(){
        String url = Host + "/metaInfo/regionZoneInfo";
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers);
            if (res.isSuccess()){
                AreaZoneResponse response = GsonUtils.deserialization(res.getBody(), AreaZoneResponse.class);
                if (Objects.nonNull(response)) {
                    return response.getData();
                }
            }
        }catch (Exception e){
            Cat.logError(e);
        }
        return Collections.emptyList();
    }
}
