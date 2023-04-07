package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.common.utils.SpringContextUtil;
import com.sankuai.avatar.sdk.entity.servicecatalog.AppKey;
import com.sankuai.avatar.sdk.entity.servicecatalog.Org;
import com.sankuai.avatar.sdk.manager.ServiceCatalogAppKey;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chenxinli
 */
public class ServiceCatalogCollectionItemImpl implements ICapacityCollectionItem {

    private ServiceCatalogAppKey serviceCatalogAppKey = SpringContextUtil.getBean(ServiceCatalogAppKey.class);
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
    public AppKey getData(AppKeyNode appKeyNode) {
        if (StringUtils.isBlank(appKeyNode.getAppkey())){
            return new AppKey();
        }
        try {
            return serviceCatalogAppKey.getAppKey(appKeyNode.getAppkey());
        } catch (Exception e){
            return new AppKey();
        }
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        AppKey data = getData(appKeyNode);
        if (data != null){
            appKeyNode.setStateful(data.getStateful());
            Org team = data.getTeam();
            try {
                List<String> stringList = team.getAncestors().stream().map(Org::getId).map(Objects::toString).collect(Collectors.toList());
                stringList.add(team.getId().toString());
                appKeyNode.setOrgPath(String.join(",", stringList));
                appKeyNode.setOrgDisplayName(team.getDisplayName());
            } catch (Exception e){

            }
        }
    }
}
