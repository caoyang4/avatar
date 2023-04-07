package com.sankuai.avatar.capacity.util;

import com.dianping.cat.Cat;
import com.sankuai.avatar.capacity.dto.DayuSetResponse;
import com.sankuai.avatar.capacity.dto.DayuSetSiteResponse;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-12-27 15:10
 */
public class DayuUtils {

//    private static final String Host = "http://dayu.fetc.test.sankuai.com";
    private static final String Host = "https://dayu.sankuai.com";
    private static final HashMap<String, String> headers = new HashMap<String, String>(){{
        put("username", "avatar");
        put("dayu-token", "1017088D4C0B11ECB5BF0A580A247DD6");
    }};

    public static String getBackupCell(String cell){
        String url = Host + "/api/v2/external/ls/cell-info?env=prod&cell=" + cell;
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 2, headers);
            if (res.isSuccess()){
                DayuSetResponse response = GsonUtils.deserialization(res.getBody(), DayuSetResponse.class);
                if (Objects.nonNull(response) && Objects.nonNull(response.getData())) {
                    return response.getData().getBackupCell();
                }
            }
        }catch (Exception e){
            Cat.logError(e);
        }
        return null;
    }

    public static String getSetIdc(String cell){
        String url = Host + "/api/v2/external/ls/cell-info?env=prod&cell=" + cell;
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 2, headers);
            if (res.isSuccess()){
                DayuSetResponse response = GsonUtils.deserialization(res.getBody(), DayuSetResponse.class);
                if (Objects.nonNull(response) && Objects.nonNull(response.getData())) {
                    return response.getData().getAz();
                }
            }
        }catch (Exception e){
            Cat.logError(e);
        }
        return null;
    }

    public static boolean isExistLogicSite(String cell){
        String url = Host + "/api/v2/external/ls/ls-info?env=prod&cell=" + cell;
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 2, headers);
            if (res.isSuccess()){
                DayuSetSiteResponse response = GsonUtils.deserialization(res.getBody(), DayuSetSiteResponse.class);
                return Objects.nonNull(response) && Objects.nonNull(response.getData());
            }
        }catch (Exception e){
            Cat.logError(e);
        }
        return false;
    }

}
