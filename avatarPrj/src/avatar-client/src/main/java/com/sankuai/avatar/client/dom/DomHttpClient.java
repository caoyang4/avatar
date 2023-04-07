package com.sankuai.avatar.client.dom;

import com.sankuai.avatar.client.dom.model.AppkeyResourceUtil;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

/**
 * <a href="https://km.sankuai.com/page/162546252">WIKI</a>
 * @author qinwei05
 */
public interface DomHttpClient {

    /**
     * 获取appkey资源利用率
     *
     * @param appkey 名称
     * @return {@link AppkeyResourceUtil}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    AppkeyResourceUtil getAppkeyResourceUtil(String appkey) throws SdkCallException, SdkBusinessErrorException;
}
