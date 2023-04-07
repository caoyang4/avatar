package com.sankuai.avatar.resource.application.impl;

import com.sankuai.avatar.client.soa.ScHttpClient;
import com.sankuai.avatar.client.soa.model.*;
import com.sankuai.avatar.client.soa.request.ApplicationPageRequest;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;
import com.sankuai.avatar.common.exception.client.SdkCallException;
import com.sankuai.avatar.common.vo.PageResponse;
import com.sankuai.avatar.resource.application.ApplicationResource;
import com.sankuai.avatar.resource.application.bo.ApplicationBO;
import com.sankuai.avatar.resource.application.bo.ApplicationDetailBO;
import com.sankuai.avatar.resource.application.bo.UserOwnerApplicationBO;
import com.sankuai.avatar.resource.application.request.ApplicationPageRequestBO;
import com.sankuai.avatar.resource.application.request.ScQueryApplicationBO;
import com.sankuai.avatar.resource.application.transfer.ApplicationDetailTransfer;
import com.sankuai.avatar.resource.application.transfer.ApplicationPageRequestTransfer;
import com.sankuai.avatar.resource.application.transfer.PageResponseTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 应用程序资源impl
 *
 * @author zhangxiaoning07
 * @create 2022/11/23
 */
@Service
public class ApplicationResourceImpl implements ApplicationResource {

    private final ScHttpClient scHttpClient;

    @Autowired
    public ApplicationResourceImpl(ScHttpClient scHttpClient) {
        this.scHttpClient = scHttpClient;
    }

    @Override
    public PageResponse<ApplicationBO> getApplications(ApplicationPageRequestBO requestBO) {
        ApplicationPageRequest request = ApplicationPageRequestTransfer.INSTANCE.convert(requestBO);
        ScPageResponse<ScApplication> scPageResponse;
        scPageResponse = scHttpClient.getApplications(request);
        return PageResponseTransfer.INSTANCE.convert(scPageResponse);
    }

    @Override
    public List<String> getPaasApplications() {
        return scHttpClient.getAllPaasApplications();
    }

    @Override
    public PageResponse<UserOwnerApplicationBO> getUserOwnerApplications(ApplicationPageRequestBO requestBO) throws SdkCallException, SdkBusinessErrorException {
        ApplicationPageRequest request = ApplicationPageRequestTransfer.INSTANCE.convert(requestBO);
        ScPageResponse<ScUserOwnerApplication> scPageResponse = scHttpClient.getUserOwnerApplications(request);
        return PageResponseTransfer.INSTANCE.toUserOwnerApplicationBOPageResponse(scPageResponse);
    }

    @Override
    public PageResponse<ScQueryApplicationBO> queryApplications(ApplicationPageRequestBO requestBO) throws SdkCallException, SdkBusinessErrorException {
        ApplicationPageRequest request = ApplicationPageRequestTransfer.INSTANCE.convert(requestBO);
        ScPageResponse<ScQueryApplication> scPageResponse = scHttpClient.queryApplications(request);
        return PageResponseTransfer.INSTANCE.toScQueryApplicationBOPageResponse(scPageResponse);
    }

    @Override
    public ApplicationDetailBO getApplication(String name) {
        ScApplicationDetail scApplicationDetail;
        scApplicationDetail = scHttpClient.getApplication(name);
        return ApplicationDetailTransfer.INSTANCE.toBO(scApplicationDetail);
    }
}
