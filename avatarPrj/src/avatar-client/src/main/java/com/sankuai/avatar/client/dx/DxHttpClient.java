package com.sankuai.avatar.client.dx;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;

import java.util.Set;

/**
 * V2 通过HTTP方式，支持跨环境
 *
 * @author zhaozhifan02
 */
public interface DxHttpClient {
    /**
     * 推送dx消息
     *
     * @param receivers 接收者
     * @param message   消息
     * @return {@link Boolean}
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean pushDxMessage(Set<String> receivers, String message) throws SdkBusinessErrorException;

    /**
     * 推送审核消息
     *
     * @param receivers 接收人
     * @param message   消息
     * @return Boolean
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean pushDxAuditMessage(Set<String> receivers, String message) throws SdkBusinessErrorException;

    /**
     * 推送群消息
     * @param groupId 群id
     * @param message 通知内容
     * @return Boolean
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean pushGroupMessage(Long groupId, String message) throws SdkBusinessErrorException;
}
