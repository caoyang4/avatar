package com.sankuai.avatar.capacity.util;

import com.dianping.lion.Environment;
import com.google.gson.JsonSyntaxException;
import com.sankuai.avatar.capacity.dto.*;
import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.oceanus.http.client.okhttp.OceanusInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by shujian on 2020/2/13.
 */
@Slf4j
public class OctoUtils {
    private static String host = "http://octo.vip.sankuai.com";
    private final HashMap<String, String> headers = new HashMap<String,String>() {{
        put("X-OCTO-Server_Token", "86BBE9FFAF2F3DE0AED5EA856688CD0C45814B59");
    }};

    public ArrayList<String> getThriftAppkeys(){
        String url = String.format("%s%s",host, "/api/appkey/hosts?type=thrift&env=prod");
        ArrayList<String> al = new ArrayList<>();
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                for (OctoDataResponse od : GsonUtils.deserialization(res.getBody(), OctoResponse.class).getData()) {
                    al.add(od.getAppkey());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return al;
    }
    public ArrayList<String> getHttpAppKeys(){
        String url = String.format("%s%s",host, "/api/appkey/hosts?type=thrift&env=prod");
        ArrayList<String> al = new ArrayList<>();
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                for (OctoDataResponse od : GsonUtils.deserialization(res.getBody(), OctoResponse.class).getData()) {
                    al.add(od.getAppkey());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return al;
    }

    public List<OctoProviderDetailResponse> getOctoAppkeyHost(String appKey, String protocol) throws IOException {
        if (appKey == null || StringUtils.isBlank(appKey)){
            return new ArrayList<>();
        }
        String url = host + "/api/provider?appkey="+appKey+"&type="+protocol+"&status=2&pageSize=2000";
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 2, headers, 10000);
        OctoProviderResponse opr = new OctoProviderResponse();
        if (res.isSuccess()) {
            try {
                opr = GsonUtils.deserialization(res.getBody(), OctoProviderResponse.class);
            } catch (JsonSyntaxException j) {
                // 解析不了, 说明没有数据. 大概率是octo 返回了 404
                log.warn("获取octo数据失败, url: " + url + "错误详情: "+ j.getMessage());
                return new ArrayList<>();
            }
        }
        if (opr.getData()!= null){
            return opr.getData();
        } else {
            return new ArrayList<>();
        }
    }

    public HashMap<String, Boolean> getAppkeyUptimeIdcDist(String appkey, HashMap<String, String> hostIdcMap) throws IOException{
        /**
         * 返回值:
         * isMultiIdcDeploy 代表是否多机房部署
         * isMultiHostDeploy 代表是否部署了多机器
         */
        HashMap<String, Boolean> hm = new HashMap<String, Boolean>(){{
            put("isMultiIdcDeploy", true);
            put("isMultiHostDeploy", true);
        }};


        ArrayList<String> urls = new ArrayList<>();
        urls.add(host + "/api/provider?appkey="+appkey+"&type=1&status=2&pageSize=-1");
        urls.add(host + "/api/provider?appkey="+appkey+"&type=2&status=2&pageSize=-1");

        for (String url: urls) {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 2, headers, 3000);
            if (res.isSuccess()){
                OctoProviderResponse opr;
                try {
                    opr = GsonUtils.deserialization(res.getBody(), OctoProviderResponse.class);
                }catch (JsonSyntaxException j){
                    // 解析不了, 说明没有数据. 大概率是octo 返回了 404
                    continue;
                }

                // 是否部署了多机器
                if (opr.getData().size() == 1){
                    hm.remove("isMultiHostDeploy");
                    hm.put("isMultiHostDeploy", false);
                    break;
                }

                // 是否多机房部署
                HashSet<String> hs = new HashSet<>();
                for (OctoProviderDetailResponse o: opr.getData()) {
                    String idc = hostIdcMap.get(o.getName());
                    if (idc != null){
                        hs.add(idc);
                    }
                }
                if (hs.size() == 1){
                    hm.remove("isMultiIdcDeploy");
                    hm.put("isMultiIdcDeploy", false);
                    break;
                }
            }
        }

        return hm;
    }

    public Boolean getAppkeyIsSameCenter(String appkey) {
        String url = String.format("%s/api/provider/group?appkey=%s&env=prod&username=opsweb", host, appkey);
        boolean isSameCenter = false;
        try{
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 2, headers, 3000);
            if (res.isSuccess()){
                Map map = GsonUtils.deserialization(res.getBody(), Map.class);
                if ( (Double)map.get("code")== 200.0) {
                    List<Map> data = (List<Map>) map.get("data");
                    for (Map item:data) {
                        if ( (Double)item.get("category") == 3.0 && (Double)item.get("status") > 0.0) {
                            isSameCenter = true;
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return isSameCenter;
    }

    private static String openHost = "https://octoopenapi.vip.sankuai.com";
    private static void switchOctoDomain(){
        if ("test".equals(Environment.getEnvironment())) {
            openHost = "http://octoopenapi.inf.dev.sankuai.com";
        }
    }

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new OceanusInterceptor("com.sankuai.octo.openapi"))
            .build();

    public static boolean isNotAppkeyCityIdcStrategy(String appkey, boolean isHttp){
        switchOctoDomain();
        String url = openHost + "/v1/provider/group/info";
        String type = isHttp ? "2" : "1";
        String env = "test".equals(Environment.getEnvironment()) ? "2" : "4";
        HttpUrl httpUrl = HttpUrl.parse(url).newBuilder()
                .addQueryParameter("env", env)
                .addQueryParameter("appkey", appkey)
                .addQueryParameter("type", type)
                .build();
        Request req = new Request.Builder().get().url(httpUrl).build();
        try {
            Response res = okHttpClient.newCall(req).execute();
            OctoProviderGroupResponse smr = JsonUtil.json2Bean(res.body().string(), OctoProviderGroupResponse.class);
            if (smr == null || smr.getCode() != 0 || CollectionUtils.isEmpty(smr.getData())) {
                return false;
            }
            OctoProviderGroupResponse.OctoProviderGroup idcGroup = smr.getData().stream().filter(group -> Objects.equals(1, group.getCategory())).findFirst().orElse(null);
            OctoProviderGroupResponse.OctoProviderGroup cityGroup = smr.getData().stream().filter(group -> Objects.equals(5, group.getCategory())).findFirst().orElse(null);
            OctoProviderGroupResponse.OctoProviderGroup centerGroup = smr.getData().stream().filter(group -> Objects.equals(3, group.getCategory())).findFirst().orElse(null);
            if (Objects.nonNull(centerGroup)) {
                return Objects.equals(1, centerGroup.getPriority()) || Objects.equals(1, centerGroup.getStatus());
            }
            if (Objects.nonNull(idcGroup) && Objects.nonNull(cityGroup)) {
                return Objects.equals(0, idcGroup.getStatus()) && Objects.equals(0, cityGroup.getStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
