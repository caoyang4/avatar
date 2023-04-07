package com.sankuai.avatar.web.util;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by shujian on 2020/2/13.
 *
 */
public class OpsUtils {
    private static final String Host = "http://ops.vip.sankuai.com";
    private static final HashMap<String, String> headers = new HashMap<String, String>(){{
        put("Authorization", "Bearer 64dcd902998fbf2d53412a6b8950c1e233047312");
    }};

    public static List<String> getPaasOwts() throws IOException{
        List<String> _paasOwts = new ArrayList<>();
        String url = Host + "/api/v0.2/owts";
        HTTPUtils.HttpResult httpResult = HTTPUtils.retryGet(url, 3, headers, 30000);

        if (httpResult.isSuccess()){
            Map map = GsonUtils.deserialization(httpResult.getBody(), Map.class);
            if (map.get("error") == null){
                List<Map> owts = (List<Map>) map.get("owts");
                for (Map owt : owts){
                    if (!StringUtils.isBlank((String) owt.get("key")) && Objects.equals(owt.get("kind"),"paas")){
                        _paasOwts.add((String)owt.get("key"));
                    }
                }
            }
        }
        return _paasOwts;
    }

    private static List<String> getAppkeysBySrv(String srv){
        String url = Host + "/api/v0.2/srvs/" + srv + "/appkeys";
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers);
            if (res.isSuccess()) {
                Map<String, List<String>> appkeyInfo = GsonUtils.deserialization(res.getBody(), Map.class);
                if (appkeyInfo.containsKey("appkeys") && appkeyInfo.containsKey("error") && appkeyInfo.get("error") == null) {
                    return new ArrayList<>(appkeyInfo.get("appkeys"));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;

    }

    public static List<String> getAppkeySrv(String appkey) {
        String url = Host + "/api/v0.2/appkeys/" + appkey + "/srvs";
        List<String> srvs = new ArrayList<>();
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers);
            if (res.isSuccess()) {
                Map<String, List<Map>> srvInfo = GsonUtils.deserialization(res.getBody(), Map.class);
                if (srvInfo.containsKey("srvs") && srvInfo.containsKey("error") && srvInfo.get("error") == null) {
                    for (Map srv : srvInfo.get("srvs")){
                        if (StringUtils.isNotBlank((String) srv.get("appkey")) && Objects.equals(srv.get("appkey"), appkey)) {
                            srvs.add((String) srv.get("key"));
                        }
                    }
                }
            }
        } catch (Exception e){
            return srvs;
        }
        return srvs;

    }

    public static ArrayList<String> getUserFavorAppkey(String mis){
        String url = Host + "/api/v0.2/users/" + mis + "/subscriptions";
        ArrayList<String> srvList = new ArrayList<>();
        ArrayList<String> appkeyList = new ArrayList<>();
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers);
            if(res.isSuccess()){
                Map<String, List<String>> srv = GsonUtils.deserialization(res.getBody(), Map.class);
                if (srv.containsKey("srvs") && srv.containsKey("error") && srv.get("error") == null){
                    List<String> userFavorSrv = srv.get("srvs");
                    if (userFavorSrv != null && userFavorSrv.size() > 0){
                        srvList.addAll(userFavorSrv);
                    }
                }
                if (srvList.size() > 0){
                    for (String s : srvList){
                        List<String> appkeys = getAppkeysBySrv(s);
                        if (appkeys != null && appkeys.size() > 0){
                            appkeyList.addAll(appkeys);
                        }
                    }
                }
                return appkeyList;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;

    }



    @Data
    public class updateResponse{
        @SerializedName("error")
        private Object error;
        @SerializedName("srv")
        private Map<String,?> srv;
    }

}
