package com.sankuai.avatar.client.dx;

import com.sankuai.avatar.client.dx.model.DxUser;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;

import java.util.List;

/**
 * dx 客户端
 * @author caoyang
 * @create 2022-10-25 15:25
 */
public interface DxClient {
    /**
     * 得到dx用户管理信息系统
     * 根据 mis 获取 dx 用户新禧
     *
     * @param mis 用户 mis
     * @return DxUser
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    DxUser getDxUserByMis(String mis) throws SdkBusinessErrorException;

    /**
     * 推动dx消息
     * 推送dx消息
     *
     * @param receivers 接收者
     * @param message   消息
     * @return {@link Boolean}
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean pushDxMessage(List<String> receivers, String message) throws SdkBusinessErrorException;

    /**
     * 推送审核消息
     *
     * @param receivers 接收人
     * @param message   消息
     * @return Boolean
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean pushDxAuditMessage(List<String> receivers, String message) throws SdkBusinessErrorException;
}
