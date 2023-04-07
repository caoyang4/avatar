package com.sankuai.avatar.client.banner;

import com.sankuai.avatar.client.banner.response.ElasticTip;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;

/**
 * <a href="https://km.sankuai.com/page/1316050046">...</a>
 * @author qinwei05
 * @date 2022/12/25 16:30
 */
public interface BannerHttpClient {

    /**
     * 服务接入弹性提示
     *
     * @return {@link ElasticTip}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    ElasticTip getElasticTips() throws SdkCallException, SdkBusinessErrorException;

    /**
     * 是否为弹性灰度的owt
     *
     * @param owt owt
     * @return {@link Boolean}
     * @throws SdkCallException          sdk调用异常
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Boolean getElasticGrayScale(String owt) throws SdkCallException, SdkBusinessErrorException;
}
