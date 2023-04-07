package com.sankuai.avatar.resource.plus.impl;

import com.dianping.cat.Cat;
import com.sankuai.avatar.client.plus.PlusHttpClient;
import com.sankuai.avatar.client.plus.model.PlusRelease;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.plus.PlusResource;
import com.sankuai.avatar.resource.plus.bo.PlusReleaseBO;
import com.sankuai.avatar.resource.plus.transfer.PlusResourceTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinwei05
 * @date 2022/10/19 12:02
 */

@Service
public class PlusResourceImpl implements PlusResource {

    private final PlusHttpClient plusHttpClient;

    @Autowired
    public PlusResourceImpl(PlusHttpClient plusHttpClient) {
        this.plusHttpClient = plusHttpClient;
    }

    @Override
    public List<PlusReleaseBO> getBindPlusByAppkey(String appkey) {
        List<PlusRelease> plusReleaseList = new ArrayList<>();
        try {
            plusReleaseList = plusHttpClient.getBindPlusByAppkey(appkey);
        } catch (SdkCallException | SdkBusinessErrorException e) {
            Cat.logError(e);
        }
        return PlusResourceTransfer.INSTANCE.toBOList(plusReleaseList);
    }

    @Override
    public List<PlusReleaseBO> getAppliedPlusByAppkey(String appkey) {
        List<PlusRelease> plusReleaseList = new ArrayList<>();
        try {
            plusReleaseList = plusHttpClient.getAppliedPlusByAppkey(appkey);
        } catch (SdkCallException | SdkBusinessErrorException e) {
            Cat.logError(e);
        }
        return PlusResourceTransfer.INSTANCE.toBOList(plusReleaseList);
    }
}
