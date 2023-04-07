package com.sankuai.avatar.resource.octo.impl;

import com.sankuai.avatar.client.http.core.EnvEnum;
import com.sankuai.avatar.client.octo.OctoHttpClient;
import com.sankuai.avatar.client.octo.model.OctoProviderGroup;
import com.sankuai.avatar.client.octo.request.OctoNodeStatusQueryRequest;
import com.sankuai.avatar.client.octo.response.OctoNodeStatusResponse;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.resource.octo.OctoResource;
import com.sankuai.avatar.resource.octo.model.OctoNodeStatusResponseBO;
import com.sankuai.avatar.resource.octo.model.OctoProviderGroupBO;
import com.sankuai.avatar.resource.octo.request.OctoNodeStatusQueryRequestBO;
import com.sankuai.avatar.resource.octo.transfer.OctoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-12-13 16:32
 */
@Service
public class OctoResourceImpl implements OctoResource {

    private final OctoHttpClient octoHttpClient;

    @Autowired
    public OctoResourceImpl(OctoHttpClient octoHttpClient) {
        this.octoHttpClient = octoHttpClient;
    }

    @Override
    public List<OctoProviderGroupBO> getOctoProviderGroup(String appkey, EnvEnum env, String type) throws SdkCallException, SdkBusinessErrorException {
        List<OctoProviderGroup> octoProviderGroupList = octoHttpClient.getOctoProviderGroup(appkey, env, type);
        return OctoTransfer.INSTANCE.toOctoProviderGroupBOList(octoProviderGroupList);
    }

    @Override
    public OctoNodeStatusResponseBO getAppkeyOctoNodeStatus(OctoNodeStatusQueryRequestBO octoNodeStatusQueryRequestBO, EnvEnum env)
            throws SdkCallException, SdkBusinessErrorException {
        OctoNodeStatusQueryRequest request = OctoTransfer.INSTANCE.toOctoNodeStatusQueryRequest(octoNodeStatusQueryRequestBO);
        OctoNodeStatusResponse octoNodeStatusResponse = octoHttpClient.getAppkeyOctoNodeStatus(request, env);
        return OctoTransfer.INSTANCE.toPageResponse(octoNodeStatusResponse);
    }
}
