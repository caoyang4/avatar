package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.dto.OctoProviderDetailResponse;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.MiddleWare;
import com.sankuai.avatar.capacity.node.OctoProvider;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.OctoUtils;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.rocket.host.instance.openapi.model.HostDTO;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenxinli
 */
@Service
public class OctoHttpCollectionItemImpl implements ICapacityCollectionItem {
    private static OctoUtils octoUtils = new OctoUtils();

    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    @Override
    public void collect() throws IOException {

    }

    @Override
    public void refresh() {

    }

    /**
     * 获取数据
     *
     * @param appKeyNode Appkey节点
     * @return 返回
     */
    @Override
    public List<OctoProvider> getData(AppKeyNode appKeyNode) {
        List<OctoProviderDetailResponse> octoAppkeyHost = new ArrayList<>();
        try {
            octoAppkeyHost = octoUtils.getOctoAppkeyHost(appKeyNode.getAppkey(), "2");
        } catch (IOException ignored) {}
        List<OctoProvider> providers = new ArrayList<>();
        PageResultHostDTO resultHostDTO = RocketUtil.httpGetHostDataByAppKey(appKeyNode.getAppkey());
        List<HostDTO> data = resultHostDTO.getData();
        if (CollectionUtils.isNotEmpty(data)) {
            Map<String, HostDTO> hostDTOMap = data.stream().collect(Collectors.toMap(HostDTO::getIpLan, hostDTO -> hostDTO));
            for (OctoProviderDetailResponse node : octoAppkeyHost) {
                // 查询Rocket接口无法获取主机IDC，不参与容灾计算
                if (hostDTOMap.containsKey(node.getIp())) {
                    providers.add(OctoProvider.builder()
                            .protocol(node.getProtocol())
                            .ip(node.getIp())
                            .cell(node.getCell())
                            .hostName(node.getName())
                            .status(node.getStatus())
                            .idc(hostDTOMap.get(node.getIp()).getIdc())
                            .build()
                    );
                }
            }
        }
        return providers;
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        final List<OctoProvider> data = this.getData(appKeyNode);
        List<OctoProvider> collect = data.stream().filter(h -> Objects.equals(h.getCell(), appKeyNode.getSetName().getSetName())).collect(Collectors.toList());
        MiddleWare middleWare = MiddleWare.builder().middleWareName(MiddleWareName.OCTO_HTTP).build();
        middleWare.setUsed(CollectionUtils.isNotEmpty(collect));
        appKeyNode.getMiddleWareInfoList().add(middleWare);
        appKeyNode.setOctoHttpProviderList(collect);
        Map<String, List<OctoProvider>> listMap = new HashMap<>();
        data.stream().filter(h -> Objects.equals(h.getCell(), appKeyNode.getSetName().getSetName())).map(OctoProvider::getIdc).collect(Collectors.toSet()).forEach(
                idc -> {
                    if (idc != null) {
                        listMap.put(idc, new ArrayList<>());
                    }
                }
        );
        appKeyNode.getOctoHttpProviderList().forEach(
                provider -> {
                    if (listMap.containsKey(provider.getIdc())) {
                        listMap.get(provider.getIdc()).add(provider);
                    }
                }
        );
        appKeyNode.setIdcOctoHttpProviderMap(listMap);
    }

}
