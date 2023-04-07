package com.sankuai.avatar.capacity.util;

import com.sankuai.avatar.capacity.dto.CraneResponse;

import java.util.ArrayList;

/**
 * Created by shujian on 2020/2/18.
 */
public class CraneUtils {
    private static final String Host = "http://crane.sankuai.com";

    public static ArrayList getCraneAppkys() throws Exception {
        String url = Host + "/api/r/taskTypeUnderAppKeys";
        ArrayList<String> al = new ArrayList<>();
        HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3);

        if (res.isSuccess()) {
            CraneResponse cr = GsonUtils.deserialization(res.getBody(), CraneResponse.class);
            if (cr.getStatus() == 0) {
                cr.getData().forEach(
                        (k, v) -> {
                            if (v.size() == 1 && v.get(0) == 4) {
                                // drop this appkey
                            } else {
                                al.add(k);
                            }
                        }
                );
            }
        } else {
            throw new Exception("获取 crane appkey 失败....");
        }
        return al;
    }
}
