package com.sankuai.avatar.capacity.util;

import com.sankuai.avatar.capacity.dto.MqAccessResponse;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shujian on 2020/2/19.
 */
public class MqUtils {
    private static final String Host = "http://userplat-mafka.vip.sankuai.com";

    public static boolean isAccessToMQ(String appkey) {
        // String uri = "/api/consumer/appkey/consumers";
        String uri = "/api/consumer/appkey/multiple/env/consumers";
        String params = "?appkey=" + appkey + "&envList=" + "prod,base";
        String url = Host + uri + params;
        boolean access = false;
        Map<String, String> headers = new HashMap<>();
        headers.put("MAFKA-ACCESS-KEY", "z26fzt7twwnt8l7800000000001315a1");
        headers.put("MAFKA-SIGN", MafkaApiAuthUtil.getSign("r6hb5jcj598jttwxmlzjvvpnljv9mjhl", uri));
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 1, headers, 3000);
            MqAccessResponse mr = GsonUtils.deserialization(res.getBody(), MqAccessResponse.class);
            if (mr.getCode() == 0 && mr.getData() != null && mr.getData().size() > 0) {
                access = true;
            }
        } catch (Exception e) {
            access = false;
        }
        return access;
    }

    public static boolean isAccessToMQProducer(String appkey) {
        String uri = "/api/topic/appkey/topics";
        String params = "?appkey=" + appkey + "&env=" + "prod";
        String url = Host + uri + params;
        boolean access = false;
        Map<String, String> headers = new HashMap<>();
        headers.put("MAFKA-ACCESS-KEY", "z26fzt7twwnt8l7800000000001315a1");
        headers.put("MAFKA-SIGN", MafkaApiAuthUtil.getSign("r6hb5jcj598jttwxmlzjvvpnljv9mjhl", uri));
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 1, headers, 3000);
            MqTopic mr = GsonUtils.deserialization(res.getBody(), MqTopic.class);
            if (mr.getCode() == 0 && mr.getData() != null && mr.getData().size() > 0) {
                access = true;
            }
        } catch (Exception e) {
            access = false;
        }
        return access;
    }

    @Data
    public static class MqTopic{
        private Integer code;
        private List<String> data;
    }

}
