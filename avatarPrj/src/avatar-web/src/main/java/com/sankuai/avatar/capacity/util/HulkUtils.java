package com.sankuai.avatar.capacity.util;

import com.google.gson.internal.LinkedTreeMap;
import com.sankuai.avatar.capacity.dto.HulkPolicyResponse;
import com.sankuai.avatar.capacity.dto.HulkScaleResponse;
import com.sankuai.avatar.capacity.dto.HulkServiceStrategyResponse;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by shujian on 2020/2/18.
 *
 */
public class HulkUtils {
    private static final String Host = "http://bannerapi.inf.vip.sankuai.com";
    private static final Map<String, String> headers = new HashMap<String, String>(){{
        put("Content-Type", "application/json");
        put("auth-token", "xOnKotzT8RBnIgBMtUp2VDVpqzQ3aLo1");
    }};

    private static final Map<String, String> headers2 = new HashMap<String, String>(){{
        put("operatorId", "avatarNew");
        put("auth-token", "a8e520b90f8f4611bc32bc05943de325");
    }};


    public static ArrayList<String> getHulkPolicy() throws Exception{
        ArrayList<String> al = new ArrayList<>();
        String url = String.format("%s/api/policy/hulk-appkeys?env=prod", Host);
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers);

        if (res.isSuccess()){
            HulkPolicyResponse hpr = GsonUtils.deserialization(res.getBody(), HulkPolicyResponse.class);
            if (hpr == null){
                throw new Exception("解析 json 失败, 请确认.... Body 数据为: "+  res.getBody());
            }
            hpr.getData().forEach(hpd->al.add(hpd.getAppkey()));
        }else{
            throw new Exception("获取 hulk policy 数据失败......");
        }
        return al;
    }

    public static Map<String, Boolean> getAppkeyElastic(String appkey) {
        String url = String.format("%s/bannerapi/provide/serviceStrategyMapping/detail?env=prod&appkey=%s", Host, appkey);
        HTTPUtils.HttpResult res = null;
        Map<String, Boolean> map =  new HashMap<>();
        try {
            res = HTTPUtils.retryGet(url, 3, headers2);
            if (res.isSuccess()) {
                HulkServiceStrategyResponse hss = GsonUtils.deserialization(res.getBody(), HulkServiceStrategyResponse.class);
                if(hss.getData() != null && hss.getCode() == 0){
                    List<Map<String, ?>> pairs = hss.getData().getGroupWithTagsStrategiesPairs();
                    if(pairs == null){ return map; }
                    for (Map<String, ?> pair : pairs) {
                        LinkedTreeMap groupInfo= (LinkedTreeMap) ((LinkedTreeMap) pair.get("groupDetailModel")).get("groupInfo");
                        String cell = (String) groupInfo.get("cell");
                        boolean elastic = pair.get("monitorStrategies") != null || pair.get("timingStrategies") != null || pair.get("trackingStrategies") != null;
                        if(map.containsKey(cell) && map.get(cell)){continue;}
                        map.put(cell, elastic);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static  Map<String, HulkScaleResponse.ScaleState> getElasticInfo(String appkey, String set){
        String url = String.format("%s/bannerapi/provide/scaling/detail?env=prod&appkey=%s&set=%s", Host, appkey, set);
        try {
            HTTPUtils.HttpResult result = HTTPUtils.retryGet(url, 3, headers2);
            if (result.isSuccess()) {
                HulkScaleResponse scaleResponse = GsonUtils.deserialization(result.getBody(), HulkScaleResponse.class);
                Map<String, HulkScaleResponse.ScaleState> scaleStates = scaleResponse.getScaleStates();
                return scaleStates;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean isSetElastic(String appkey, String set){
        Map<String, HulkScaleResponse.ScaleState> scaleStates = getElasticInfo(appkey, set);
        return scaleStates != null
                && scaleStates.get("scale") != null && scaleStates.get("scale").getState()
                && scaleStates.get("strategy") != null && scaleStates.get("strategy").getState()
                && scaleStates.get("quota") != null && scaleStates.get("quota").getState();
    }
    public static String getNoElasticMsg(String appkey, String set){
        Map<String, HulkScaleResponse.ScaleState> scaleStates = getElasticInfo(appkey, set);
        if (scaleStates == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String state : Arrays.asList("scale", "strategy", "quota")) {
            if (scaleStates.get(state) != null && !scaleStates.get(state).getState() && StringUtils.isNotEmpty(scaleStates.get(state).getMsg())) {
                builder.append(scaleStates.get(state).getMsg()).append("，");
            }
        }
        if (StringUtils.isNotEmpty(builder.toString())) {
            builder.append("判断弹性未有效接入；");
        }
        return builder.toString();
    }
}
