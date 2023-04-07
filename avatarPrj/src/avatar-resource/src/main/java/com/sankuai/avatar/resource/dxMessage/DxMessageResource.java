package com.sankuai.avatar.resource.dxMessage;

import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;

import java.util.List;

/**
 * 大象消息资源管理
 * @author caoyang
 * @create 2022-11-24 16:30
 */
public interface DxMessageResource {
    /**
     * 推动dx消息
     * 推送dx消息
     *
     * @param receivers 接收人
     * @param message   消息
     * @return {@link Boolean}
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean pushDxMessage(List<String> receivers, String message) throws SdkBusinessErrorException;
}
