package com.sankuai.avatar.resource.mcm.impl;

import com.sankuai.avatar.client.mcm.ComponentHttpClient;
import com.sankuai.avatar.client.mcm.request.CreateBusyPeriodRequest;
import com.sankuai.avatar.resource.mcm.ComponentResource;
import com.sankuai.avatar.resource.mcm.request.CreateBusyPeriodRequestBO;
import com.sankuai.avatar.resource.mcm.transfer.BusyPeriodTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 高峰期解禁功能实现
 *
 * @author zhaozhifan02
 */
@Slf4j
@Service
public class ComponentResourceImpl implements ComponentResource {

    private final ComponentHttpClient componentHttpClient;

    @Autowired
    public ComponentResourceImpl(ComponentHttpClient componentHttpClient) {
        this.componentHttpClient = componentHttpClient;
    }

    @Override
    public boolean createBusyPeriod(CreateBusyPeriodRequestBO request) {
        if (request == null) {
            return false;
        }
        CreateBusyPeriodRequest createBusyPeriodRequest = BusyPeriodTransfer.INSTANCE.boToRequest(request);
        return componentHttpClient.createBusyPeriod(createBusyPeriodRequest);
    }
}
