package com.sankuai.avatar.capacity.util;

import com.sankuai.avatar.common.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by shujian on 2020/2/28.
 *
 */
public class PlusUtils {
    private static final String Host = "http://plus.sankuai.com";

    public static boolean isDeployed(int id){
        String url = String.format("%s/release/%d/joblist?env=prod&limit=1", Host, id);
        boolean isDeployed = false;
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3);
            if (res.isSuccess()){
                List result = GsonUtils.deserialization(res.getBody(), List.class);
                if (result.size() > 0){
                    isDeployed = true;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return isDeployed;
    }

    public static boolean isHostDeployed(String host, String ip, int days){
        String dateStr = DateUtils.dateToString(DateUtils.getAfterDaysFromNow(-30), "yyyyMMddHH:mm:ss");
        String url = String.format("%s/api/%s/jobrecord?ip=%s&from=%s", Host, host, ip, dateStr);
        try {
            HTTPUtils.HttpResult res = HTTPUtils.retryGet(url, 3);
            if (res.isSuccess()){
                List result = GsonUtils.deserialization(res.getBody(), List.class);
                return CollectionUtils.isNotEmpty(result);
            }
        } catch (IOException ignored){
        }
        return false;
    }

}
