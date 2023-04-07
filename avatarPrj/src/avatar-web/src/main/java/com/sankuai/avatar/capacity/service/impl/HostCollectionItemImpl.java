package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.constant.IdcRegion;
import com.sankuai.avatar.capacity.constant.RegionIdcMap;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Host;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.rocket.host.instance.openapi.model.HostDTO;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenxinli
 */
@Service
public class HostCollectionItemImpl implements ICapacityCollectionItem {

    private static final Logger logger = LoggerFactory.getLogger(HostCollectionItemImpl.class);



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
    public List<HostDTO> getData(AppKeyNode appKeyNode) {
        PageResultHostDTO resultHostDTO = RocketUtil.httpGetHostDataByAppKey(appKeyNode.getAppkey());
        List<HostDTO> data = resultHostDTO.getData();
        if (CollectionUtils.isNotEmpty(data)){
            return data;
        }
        return new ArrayList<>();
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        final List<HostDTO> data = this.getData(appKeyNode);
        List<HostDTO> hostDTOList = data.stream().filter(h -> Objects.equals(h.getCell(), appKeyNode.getSetName().getSetName())).collect(Collectors.toList());
        Set<String> idcSet = hostDTOList.stream().map(HostDTO::getIdc).filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
        final Map<String, IdcRegion> idcRegionMap = getIdcRegionMap(idcSet);
        hostDTOList.forEach(
                i -> {
                    IdcRegion region = idcRegionMap.getOrDefault(i.getIdc(), null);
                    if (Objects.nonNull(region)) {
                        Host host = Host.builder().build();
                        host.setIp(i.getIpLan());
                        host.setHostName(i.getName());
                        host.setIdc(i.getIdc());
                        host.setCell(i.getCell());
                        host.setCpuCount(i.getCpuCount());
                        host.setMemSize(i.getMemSize());
                        host.setRegion(region);
                        if (appKeyNode.getHostList() == null) {
                            appKeyNode.setHostList(new ArrayList<>());
                        }
                        appKeyNode.getHostList().add(host);
                    }
                }
        );
        Map<String, List<Host>> listMap = new HashMap<>();
        hostDTOList.stream().map(HostDTO::getIdc).collect(Collectors.toSet()).forEach(
                idc -> listMap.put(idc, new ArrayList<>())
        );
        appKeyNode.getHostList().forEach(
                host -> {
                    if (listMap.containsKey(host.getIdc())) {
                        listMap.get(host.getIdc()).add(host);
                    }
                }
        );
        appKeyNode.setIdcHostMap(listMap);
    }

    private Map<String, IdcRegion> getIdcRegionMap(Set<String> idcSet){
        Map<String, IdcRegion> map = new HashMap<>();
        for (String idc : idcSet) {
            map.put(idc, RegionIdcMap.getRegion(idc));
        }
        return map;
    }
}
