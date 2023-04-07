package com.sankuai.avatar.client.ecs;

import com.sankuai.avatar.client.ecs.model.BillingUnit;
import com.sankuai.avatar.client.ecs.model.EcsIdc;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

import java.util.List;

/**
 * ECS系统接口
 * @author qinwei05
 */
public interface EcsHttpClient {

    /**
     * 获取机房信息
     *
     * @return {@link List}<{@link EcsIdc}>
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    List<EcsIdc> getIdcList() throws SdkCallException, SdkBusinessErrorException;

    /**
     * 获取服务结算单元信息
     *
     * @param appkey appkey
     * @return {@link BillingUnit}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    BillingUnit getAppkeyUnitList(String appkey) throws SdkCallException, SdkBusinessErrorException;
}
