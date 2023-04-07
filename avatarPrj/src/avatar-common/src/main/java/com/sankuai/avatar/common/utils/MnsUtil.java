package com.sankuai.avatar.common.utils;

import com.sankuai.avatar.common.dto.CommonResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dianping.cat.Cat;
import com.sankuai.inf.octo.mns.MnsInvoker;
import com.sankuai.sgagent.thrift.model.ProtocolRequest;
import com.sankuai.sgagent.thrift.model.SGService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author chenxinli
 */
public class MnsUtil {
    private static String mnsHost = "http://mns.sankuai.com";

    private static String localAppKey = "com.sankuai.avatar.web";

    public ArrayList<String> getAllOctoAppKeys() {
        String url = String.format("%s%s", mnsHost, "/api/allappkeys");
        Map<String, String> headers = new HashMap<>(3);
        ArrayList<String> al = new ArrayList<>();
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3, headers, 30000);
            if (res.isSuccess()) {
                al.addAll(GsonUtils.deserialization(res.getBody(), CommonResponse.class).getData());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return al;
    }

    public List<SGService> getOriginOctoNodes(String appKey, String protocol){
        ProtocolRequest request = new ProtocolRequest();
        request.setProtocol(protocol)
                .setLocalAppkey(localAppKey)
                .setRemoteAppkey(appKey);
        return MnsInvoker.getOriginServiceList(request);
    }
}
