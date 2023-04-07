package com.sankuai.avatar.capacity.util;

import com.sankuai.rocket.host.instance.api.service.HostHttpService;
import com.sankuai.rocket.host.instance.openapi.ApiClient;
import com.sankuai.rocket.host.instance.openapi.ApiException;
import com.sankuai.rocket.host.instance.openapi.ApiResponse;
import com.sankuai.rocket.host.instance.openapi.model.HostDTO;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import com.sankuai.rocket.host.instance.openapi.model.WebResponseHostDTO;
import com.sankuai.rocket.host.instance.openapi.model.WebResponsePageResultHostDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * rocket
 *
 * @author chenxinli
 */
public class RocketUtil {
    private static final HostHttpService hostHttpService = new HostHttpService("prod", "g7n_3`PDqkp2LNm6");

    private static final String FIELDS = "name,ipLan,idc,env,cell,cpuCount,memSize,ctime,vendor";

    private static final Logger logger = LoggerFactory.getLogger(RocketUtil.class);

    public static PageResultHostDTO httpGetHostDataByAppKey(String appKey) {
        ApiClient apiClient = hostHttpService.getApiClient();
        apiClient.setConnectTimeout(3000);
        apiClient.setReadTimeout(10000);
        try {
            WebResponsePageResultHostDTO response = hostHttpService.queryHostListByAppkey(appKey, 0, 5000, "prod", FIELDS, true);
            if (response.getError() != null) {
                // handle error
                logger.warn(appKey + "获取主机列表失败, " + response.getError().getMsg());
                return new PageResultHostDTO();
            }
            return response.getData();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return new PageResultHostDTO();
    }

    public static Boolean hasSet(String appKey) {
        return !listSet(appKey).stream().allMatch(""::equals);
    }

    public static Set<String> listSet(String appKey){
        List<HostDTO> data = RocketUtil.httpGetHostDataByAppKey(appKey).getData();
        if (data != null){
            return data.stream().map(HostDTO::getCell).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    public static HostDTO httpGetHostData(String hostName) {
        ApiClient apiClient = hostHttpService.getApiClient();
        apiClient.setConnectTimeout(3000);
        apiClient.setReadTimeout(1000);
        try {
            ApiResponse<WebResponseHostDTO> response = hostHttpService.getHostWithHttpInfo(hostName, "ctime");
            return response.getData().getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
