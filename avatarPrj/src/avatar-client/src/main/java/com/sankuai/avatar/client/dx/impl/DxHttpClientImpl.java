package com.sankuai.avatar.client.dx.impl;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.sankuai.avatar.client.dx.DxHttpClient;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.xm.pub.push.Pusher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author zhaozhifan02
 */
@Slf4j
@Component
public class DxHttpClientImpl implements DxHttpClient {

    @Resource(name = "userPusher")
    private Pusher userPusher;

    @Resource(name = "groupPusher")
    private Pusher groupPusher;

    @Resource(name = "userAuditPusher")
    private Pusher userAuditPusher;

    /**
     * dx返回接口业务状态码, 0 为正确
     */
    private final String DX_RESULT_CODE = "rescode";
    /**
     * dx返回值key
     */
    private final String DX_RESULT_DATA = "data";
    /**
     * dx接口返回值校验key，使用user接口时
     */
    private final String DX_RESULT_MIDS = "mids";
    /**
     * dx接口返回值校验key，使用group接口时
     */
    private final String DX_RESULT_MID = "mid";


    @Override
    public Boolean pushDxMessage(Set<String> receivers, String message) throws SdkBusinessErrorException {
        if (CollectionUtils.isEmpty(receivers)) {
            log.info("Receivers is empty");
            return false;
        }
        receivers.forEach(user -> pushToUser(user, message));
        return true;
    }

    @Override
    public Boolean pushDxAuditMessage(Set<String> receivers, String message) throws SdkBusinessErrorException {
        if (CollectionUtils.isEmpty(receivers)) {
            log.info("Receivers is empty");
            return false;
        }
        receivers.forEach(user -> pushToAuditUser(user, message));
        return true;
    }

    @Override
    public Boolean pushGroupMessage(Long groupId, String message) throws SdkBusinessErrorException {
        return checkResult(groupPusher.pushToRoom(message, groupId), String.valueOf(groupId));
    }

    /**
     * 推动用户
     *
     * @param user    用户
     * @param message 消息
     * @return boolean
     */
    private boolean pushToUser(String user, String message) {
        try {
            JSONObject result = userPusher.push(message, user);
            return checkResult(result, user);
        } catch (Exception e) {
            log.error("Push to dx(mis: {}) failed: ", user, e);
            Cat.logError(e);
            throw new SdkBusinessErrorException("Avatar公众号发送消息至异常：" + e.getMessage());
        }
    }

    /**
     * 推送审核消息
     * @param user 用户
     * @param message 消息内容
     * @return boolean
     */
    private boolean pushToAuditUser(String user, String message) {
        try {
            JSONObject result = userAuditPusher.push(message, user);
            return checkResult(result, user);
        } catch (Exception e) {
            log.error("Push to dx(mis: {}) failed: ", user, e);
            Cat.logError(e);
            throw new SdkBusinessErrorException("Avatar-审核公众号发送消息至异常：" + e.getMessage());
        }
    }

    /**
     * 检查消息发送结果
     *
     * @param result 结果
     * @param user   用户
     * @return {@link Boolean}
     */
    private Boolean checkResult(JSONObject result, String user) {
        // code or data error
        if (result == null || result.getIntValue(DX_RESULT_CODE) != 0 || result.getJSONObject(DX_RESULT_DATA) == null) {
            Cat.logError(String.format("Push to dx(mis/group: %s) error, result is %s", user, result), null);
            return false;
        }
        // key error
        if (result.getJSONObject(DX_RESULT_DATA).containsKey(DX_RESULT_MIDS) && result.getJSONObject(DX_RESULT_DATA).getJSONArray(DX_RESULT_MIDS) == null) {
            Cat.logError(String.format("Push to dx(mis/group: %s) error, result: %s", user, result), null);
            return false;
        }
        // key error
        if (result.getJSONObject(DX_RESULT_DATA).containsKey(DX_RESULT_MID) && result.getJSONObject(DX_RESULT_DATA).getBigInteger(DX_RESULT_MID) == null) {
            Cat.logError(String.format("Push to dx(mis/group: %s) error, result: %s", user, result), null);
            return false;
        }
        return true;
    }
}
