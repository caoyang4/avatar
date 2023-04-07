package com.sankuai.avatar.resource.dxMessage.impl;

import com.sankuai.avatar.client.dx.DxClient;
import com.sankuai.avatar.client.org.OrgClient;
import com.sankuai.avatar.client.org.model.OrgUser;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.resource.dxMessage.DxMessageResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author caoyang
 * @create 2022-11-24 16:31
 */
@Slf4j
@Component
public class DxMessageResourceImpl implements DxMessageResource {

    private final DxClient dxClient;
    private final OrgClient orgClient;

    @Autowired
    public DxMessageResourceImpl(DxClient dxClient, OrgClient orgClient) {
        this.dxClient = dxClient;
        this.orgClient = orgClient;
    }

    @Override
    public Boolean pushDxMessage(List<String> receivers, String message) throws SdkBusinessErrorException{
        List<String> validUsers = new ArrayList<>();
        for (String receiver : receivers) {
            try {
                OrgUser orgUser = orgClient.getOrgUserByMis(receiver);
                if (Objects.nonNull(orgUser) && Objects.equals("在职", orgUser.getJobStatus())) {
                    validUsers.add(receiver);
                }
            } catch (SdkBusinessErrorException ignored) {}
        }
        if (CollectionUtils.isEmpty(validUsers)) {
            log.warn("无有效人员可发送大象消息");
            return false;
        }
        return dxClient.pushDxMessage(receivers, message);
    }
}
