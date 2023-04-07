package com.sankuai.avatar.capacity.util;

import com.sankuai.avatar.capacity.dto.MgwResponse;
import com.sankuai.avatar.capacity.dto.MgwRsResponse;
import com.sankuai.avatar.capacity.dto.MgwVsResponse;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.rocket.host.instance.openapi.model.HostDTO;

import java.io.IOException;
import java.util.*;

/**
 * Created by shujian on 2020/2/19.
 *
 */
public class MgwUtils {
    private static final String Host = "http://mip.sankuai.com";
    private static final HashMap<String, String> headers = new HashMap<String, String>(){{
        put("Authorization", "Bearer 90z5d4zsc0c1vkb7fcggrqbib3wyuspy");
    }};

    private static final HashMap<String, String> headers2 = new HashMap<String, String>(){{
        put("Authorization", "token e71a4680d97cafe941f217c511f0250f");
    }};

    public static Set<String> getMgwCluster() throws IOException{
        Set<String> clusters = new HashSet<>();
        String url = Host + "/mgw/cluster/all/";

        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers);
        if (res.isSuccess()){
            MgwResponse mr = GsonUtils.deserialization(res.getBody(), MgwResponse.class);
            if (mr.getStatus().equals("success") && mr.getData() != null && !mr.getData().isEmpty()){
                mr.getData().forEach(item->clusters.add((String) item.get("name")));
            }
        }
        return clusters;
    }

    public static Set<String>  getRsByCluster(String cluster) throws IOException{
        HashSet<String> rsList = new HashSet<>();
        String url = Host+"/mgw/cluster/items/?cluster="+cluster+"&level=rs";

        // 这里可能超时
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3 , headers, 10000);
        if (res.isSuccess()){
            MgwRsResponse omr = GsonUtils.deserialization(res.getBody(), MgwRsResponse.class);
            if (omr.getStatus().equals("success") && omr.getData() != null && !omr.getData().isEmpty()){
                Map<String, List<String>> vips = (Map<String, List<String>>) omr.getData().get(cluster);
                vips.forEach((k, v)->{v.forEach(rs->rsList.add(rs.split(":")[0]));});
            }
        }
        return rsList;
    }

    public static List<MgwVsResponse.MgwVs> getMgwVs(String appkey) {
        List<MgwVsResponse.MgwVs> mgwVs = new ArrayList<>();
        String url = Host + String.format("/v2/mgw/vs?page_size=300&vs_env=prod&appkey=%s", appkey);
        HTTPUtils.HttpResult res = null;
        try {
            res = HTTPUtils.retryGet(url, 3, headers2);
            if (res.isSuccess()){
                MgwVsResponse mvr = GsonUtils.deserialization(res.getBody(), MgwVsResponse.class);
                if (mvr.getStatus().equals("success") && mvr.getData() != null && !mvr.getData().getItems().isEmpty()){
                    return mvr.getData().getItems();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mgwVs;
    }

    public static Set<String> getMgwRip(String appkey){
        List<MgwVsResponse.MgwVs> mgwVsList = getMgwVs(appkey);
        Set<String> rips = new HashSet<>();
        for (MgwVsResponse.MgwVs mgwVs : mgwVsList) {
            String url = Host + String.format("/v2/mgw/rs/by_vs/full?with_hc_status=1&vport=%s&protocol=%s&vip=%s", mgwVs.getVport(), mgwVs.getProtocol(), mgwVs.getVip());
            HTTPUtils.HttpResult res = null;
            try {
                res = HTTPUtils.retryGet(url, 3, headers2);
                if (res.isSuccess()){
                    MgwResponse mr = GsonUtils.deserialization(res.getBody(), MgwResponse.class);
                    if (mr.getStatus().equals("success") && mr.getData() != null && !mr.getData().isEmpty()){
                        mr.getData().forEach(item -> rips.add((String) item.get("rip")));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rips;
    }

    public static boolean isMiddleWareMgw(AppKeyNode appKeyNode){
        // https://km.sankuai.com/page/1359353548
        String appkey = appKeyNode.getAppkey();
        Set<String> rips = MgwUtils.getMgwRip(appkey);
        if (rips.size() < 2) {
            return false;
        }
        List<com.sankuai.avatar.capacity.node.Host> hostList = appKeyNode.getHostList();
        Set<String> hosts = new HashSet<>();
        if (hostList.isEmpty()) {
            List<HostDTO> hosDtoList = RocketUtil.httpGetHostDataByAppKey(appkey).getData();
            if (hosDtoList != null) {
                hosDtoList.forEach(host -> hosts.add(host.getIpLan()));
            }
        } else {
            hostList.forEach(host -> hosts.add(host.getIp()));
        }
        hosts.retainAll(rips);
        return hosts.size() > 1;
    }
}