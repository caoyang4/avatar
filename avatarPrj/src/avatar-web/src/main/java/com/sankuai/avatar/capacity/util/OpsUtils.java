package com.sankuai.avatar.capacity.util;

import com.google.gson.annotations.SerializedName;
import com.sankuai.avatar.capacity.dto.OpsCapacity;
import com.sankuai.avatar.capacity.dto.OpsCapacityResponse;
import com.sankuai.avatar.capacity.dto.OpsPlusResponse;
import com.sankuai.avatar.capacity.dto.OpsSrvResponse;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.SetName;
import com.sankuai.rocket.host.instance.openapi.model.HostDTO;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by shujian on 2020/2/13.
 */
public class OpsUtils {
    private static String paasOwts = "meituan.blade";
    private static final String Host = "http://ops.vip.sankuai.com";
    private static final HashMap<String, String> headers = new HashMap<String, String>() {{
        put("Authorization", "Bearer 64dcd902998fbf2d53412a6b8950c1e233047312");
    }};

    private static final Logger logger = LoggerFactory.getLogger(OpsUtils.class);

    public static ArrayList<String> getOwts() throws IOException {
        ArrayList<String> _owts = new ArrayList<>();
        String url = Host + "/api/v0.2/owts";
        HTTPUtils.HttpResult httpResult = HTTPUtils.retryGet(url, 3, headers, 30000);

        if (httpResult.isSuccess()) {
            Map map = GsonUtils.deserialization(httpResult.getBody(), Map.class);
            if (map.get("error") == null) {
                List<Map> owts = (List<Map>) map.get("owts");
                owts.forEach(item -> _owts.add((String) item.get("key")));
            }
        }
        return _owts;
    }

    public static List<String> getPaasOwtList() throws IOException {
        String url = Host + "/api/v0.2/owts";
        HTTPUtils.HttpResult httpResult = HTTPUtils.retryGet(url, 3, headers, 30000);
        List<Map> owts = new ArrayList<>();
        if (httpResult.isSuccess()) {
            Map map = GsonUtils.deserialization(httpResult.getBody(), Map.class);
            if (map.get("error") == null) {
                owts = (List<Map>) map.get("owts");
            }
        }
        return owts.stream().filter(p -> "paas".equals((String) p.get("kind"))).map(p -> (String) p.get("key")).collect(Collectors.toList());
    }

    public static List<String> getTagByHost(String host) throws IOException {
        String url = Host + "/api/stree/host/tag?host=" + host;
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
        if (res.isSuccess()) {
            Map map = GsonUtils.deserialization(res.getBody(), Map.class);
            Map data = null;
            if (map.containsKey("data")) {
                data = (Map) map.get("data");
            }

            if (data != null) {
                return (List<String>) data.get(host);
            }
        }
        return null;
    }

    public static String getHostnameByIp(String ip) throws IOException {
        String url = Host + "/api/v0.2/hosts/" + ip;
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
        if (res.isSuccess()) {
            Map map = GsonUtils.deserialization(res.getBody(), Map.class);
            Map host = null;
            if (map.containsKey("host")) {
                host = (Map) map.get("host");
            }

            if (host != null) {
                return (String) host.get("name");
            }
        }
        return null;
    }

    public static boolean isServiceDeployed(String srv) {
        String url = Host + "/api/v0.2/srvs/" + srv + "/plus?detail=1";
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 3000);
            if (res.isSuccess()) {
                Map<String, ?> plus = GsonUtils.deserialization(res.getBody(), Map.class);
                if (plus.containsKey("plus")) {
                    List<Map<String, ?>> plusInfo = (List<Map<String, ?>>) plus.get("plus");
                    if (plusInfo == null || plusInfo.isEmpty()) {
                        return true;
                    }
                    for (Map<String, ?> m : plusInfo) {
                        if (m.containsKey("Id")) {
                            int id = ((Double) m.get("Id")).intValue();
                            if (PlusUtils.isDeployed(id)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Map<String, ?> getSrvByAppKey(String appKey) {
        String url = Host + String.format("/api/v0.2/appkeys/%s/srvs", appKey.trim());
        Map<String, ?> srvMap = new HashMap<>();
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                OpsSrvResponse opsSrvResponse = GsonUtils.deserialization(res.getBody(), OpsSrvResponse.class);
                List<Map<String, ?>> mapList = opsSrvResponse.getSrvs().stream().filter(s -> s.get("appkey").equals(appKey)).collect(Collectors.toList());
                if (mapList.size() > 0) {
                    srvMap = mapList.get(0);
                }
            }
        } catch (IOException e) {
            logger.error("获取 ops 数据出现异常, 无法获取到 srv 信息....");
            e.printStackTrace();
        }
        return srvMap;
    }

    public static List<AppKeyNode> getSingleOpsCapacityService(String appKey) {
        List<AppKeyNode> appKeyNodeList = Collections.synchronizedList(new ArrayList<>());
        String url = Host + String.format("/api/v0.2/appkeys/%s/srvs", appKey.trim());
        PageResultHostDTO resultHostDTO = RocketUtil.httpGetHostDataByAppKey(appKey);
        if (resultHostDTO.getData() == null) {
            return appKeyNodeList;
        }
        Map<String, List<HostDTO>> listMap = resultHostDTO.getData().stream().collect(Collectors.groupingBy(HostDTO::getCell));
        if (listMap.size() == 0 || !listMap.containsKey("")) {
            listMap.put("", new ArrayList<>());
        }
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                listMap.keySet().parallelStream().forEach(
                        cell -> {
                            OpsSrvResponse opsSrvResponse = GsonUtils.deserialization(res.getBody(), OpsSrvResponse.class);
                            if (opsSrvResponse.getSrvs() != null && opsSrvResponse.getSrvs().size() > 0) {
                                opsSrvResponse.getSrvs().stream().filter(o -> o.get("appkey").equals(appKey)).forEach(
                                        srv -> {
                                            String key = (String) srv.get("key");
                                            String owt = OpsUtils.getOwtFromSrv(key);
                                            Boolean singleHostRestart = (Boolean) srv.get("single_host_restart");
                                            AppKeyNode appKeyNode = new AppKeyNode(appKey, key, owt, (String) srv.get("rank"), singleHostRestart);
                                            appKeyNode.setSetName(new SetName(cell));
                                            appKeyNodeList.add(appKeyNode);
                                        }
                                );
                            }
                        }
                );
                return appKeyNodeList;
            }
        } catch (IOException e) {
            logger.error("获取 ops 数据出现异常, 无法获取到 srv 信息....");
            e.printStackTrace();
        }
        return appKeyNodeList;
    }

    public static List<String> getOpsCapacityAppKey() {
        List<String> appKeyList = new ArrayList<>();
        String url = Host + "/api/v0.2/srvs";
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                OpsSrvResponse osr = GsonUtils.deserialization(res.getBody(), OpsSrvResponse.class);
                if (osr != null && osr.getError() == null) {
                    List<String> paasOwtList = Arrays.asList(paasOwts.split(","));
                    osr.getSrvs().forEach(
                            (srv) -> {
                                String key = (String) srv.get("key");
                                String owt = OpsUtils.getOwtFromSrv(key);
                                if (paasOwtList.contains(owt)) {
                                    return;
                                }
                                String updateBy = (String) srv.get("capacity_update_by");
                                if ("token.avatar".equals(updateBy) || "".equals(updateBy) || "ops自动计算".equals(updateBy) || "OPS".equals(updateBy)) {
                                    String appKey = (String) srv.get("appkey");
                                    appKeyList.add(appKey);
                                }
                            }
                    );
                }
            }
        } catch (IOException e) {
            logger.error("获取 ops 数据出现异常, 无法获取到 srv 信息....");
            e.printStackTrace();
        }
        return appKeyList;
    }

    public static List<AppKeyNode> getOpsCapacityAppKeyNodeWithoutSet() {
        List<AppKeyNode> appKeyNodeList = new ArrayList<>();
        String url = Host + "/api/v0.2/srvs";
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                OpsSrvResponse osr = GsonUtils.deserialization(res.getBody(), OpsSrvResponse.class);
                if (osr != null && osr.getError() == null) {
                    List<String> paasOwtList = Arrays.asList(paasOwts.split(","));
                    Collections.synchronizedList(osr.getSrvs()).parallelStream().forEach(
                            (srv) -> {
                                String key = (String) srv.get("key");
                                String owt = OpsUtils.getOwtFromSrv(key);
                                if (paasOwtList.contains(owt)) {
                                    return;
                                }
                                String updateBy = (String) srv.get("capacity_update_by");
                                if ("token.avatar".equals(updateBy) || "".equals(updateBy) || "ops自动计算".equals(updateBy) || "OPS".equals(updateBy)) {
                                    String appKey = (String) srv.get("appkey");
                                    Boolean singleHostRestart = (Boolean) srv.get("single_host_restart");
                                    AppKeyNode appKeyNode = new AppKeyNode(appKey, key, owt, (String) srv.get("rank"), singleHostRestart);
                                    appKeyNodeList.add(appKeyNode);
                                }
                            }
                    );
                }
            }
        } catch (IOException e) {
            logger.error("获取 ops 数据出现异常, 无法获取到 srv 信息....");
            e.printStackTrace();
        }
        return appKeyNodeList;
    }

    public static List<Map<String, ?>> getFullSrv() {
        String url = Host + "/api/v0.2/srvs";
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                OpsSrvResponse osr = GsonUtils.deserialization(res.getBody(), OpsSrvResponse.class);
                if (osr != null && osr.getError() == null) {
                    return osr.getSrvs();
                }
            }
        } catch (IOException e) {
            logger.error("获取 ops 数据出现异常, 无法获取到 srv 信息....");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static boolean isAppkeyJbox(String srv) {
        String url = Host + String.format("/api/v0.2/srvs/%s/applied_plus", srv);
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 2, headers, 3000);
            if (res.isSuccess()) {
                OpsPlusResponse opr = GsonUtils.deserialization(res.getBody(), OpsPlusResponse.class);
                if (opr == null || opr.getError() != null || opr.getPlus() == null || opr.getPlus().isEmpty()) {
                    return false;
                }
                String srvType = (String) opr.getPlus().get(0).getOrDefault("ExternalReleaseType","");
                return "jbox".equals(srvType);
            }
        } catch (IOException e) {
            logger.error("获取 ops 数据出现异常, 无法获取到 srv 信息....");
            e.printStackTrace();
        }
        return false;
    }

    public static Double getAppKeyStandardLevel(String appKey) {
        String url = Host + String.format("/api/v0.2/appkeys/%s/need_capacity", appKey.trim());
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                OpsCapacityResponse opsCapacityResponse = GsonUtils.deserialization(res.getBody(), OpsCapacityResponse.class);
                if (opsCapacityResponse.getData().get("need_capacity") == null) {
                    return 0.0;
                }
                return (Double) opsCapacityResponse.getData().get("need_capacity");
            }
        } catch (IOException e) {
            logger.error("获取 ops 数据出现异常, 无法获取appKey达标等级, appKey: " + appKey);
            e.printStackTrace();
        }
        return 0.0;
    }

    public static boolean updateServiceCapacity(String srv, OpsCapacity opsCapacity) {
        String url = Host + "/api/v0.2/srvs/" + srv.trim();
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryPut(url, 3, GsonUtils.serialization(opsCapacity), headers);
            if (res.isSuccess()) {
                updateResponse ur = GsonUtils.deserialization(res.getBody(), updateResponse.class);
                if (ur.getError() == null) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static String getOwtFromSrv(String srv) {
        String[] srvArray = srv.split("\\.");
        return srvArray[0] + "." + srvArray[1];
    }

    @Data
    public class updateResponse {
        @SerializedName("error")
        private Object error;
        @SerializedName("rv")
        private Map<String, ?> srv;
    }

}
