package com.sankuai.avatar.capacity.service.impl;

import com.sankuai.avatar.capacity.constant.WhiteApp;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.WhiteInfo;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.common.utils.SpringContextUtil;
import com.sankuai.avatar.web.dto.whitelist.OwtSetWhitelistDTO;
import com.sankuai.avatar.web.dto.whitelist.ServiceWhitelistDTO;
import com.sankuai.avatar.web.service.OwtSetWhitelistService;
import com.sankuai.avatar.web.service.WhitelistService;
import com.sankuai.avatar.web.util.ToolUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author chenxinli
 */
public class WhitelistCollectionItemImpl implements ICapacityCollectionItem {
    /**
     * 容灾等级相关数据采集
     *
     */

    private WhitelistService whitelistService = SpringContextUtil.getBean(WhitelistService.class);

    private OwtSetWhitelistService owtSetWhitelistService = SpringContextUtil.getBean(OwtSetWhitelistService.class);

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
    public List<ServiceWhitelistDTO> getData(AppKeyNode appKeyNode) {
        return whitelistService.getServiceWhitelistByAppkey(appKeyNode.getAppkey());
    }

    private boolean isSetCapacityWhite(ServiceWhitelistDTO whitelist, AppKeyNode appKeyNode){
        List<String> whiteSet = Arrays.asList(ToolUtils.nullToEmpty(whitelist.getSetName()).split(","));
        if (StringUtils.isNotEmpty(appKeyNode.getSetName().getSetName())) {
            return  StringUtils.isEmpty(whitelist.getSetName()) || whiteSet.contains(appKeyNode.getSetName().getSetName());
        } else {
            return StringUtils.isEmpty(whitelist.getSetName()) || whiteSet.contains("default");
        }
    }
    private List<OwtSetWhitelistDTO> getOwtSetWhiteList(AppKeyNode appKeyNode){
        if (StringUtils.isEmpty(appKeyNode.getSetName().getSetName())) {
            return Collections.emptyList();
        }
        return owtSetWhitelistService.getCapacityWhitelistByOwtAndSet(appKeyNode.getOwt(), appKeyNode.getSetName().getSetName());
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        boolean owtWhite = false;
        if (CollectionUtils.isNotEmpty(getOwtSetWhiteList(appKeyNode))) {
            WhiteInfo whiteInfo = new WhiteInfo();
            whiteInfo.setWhiteApp(WhiteApp.CAPACITY);
            whiteInfo.setCName(WhiteApp.CAPACITY.getCname());
            whiteInfo.setEndTime(getOwtSetWhiteList(appKeyNode).get(0).getEndTime());
            whiteInfo.setReason(getOwtSetWhiteList(appKeyNode).get(0).getReason());
            appKeyNode.getWhiteInfoList().add(whiteInfo);
            owtWhite = true;
        }
        if (CollectionUtils.isNotEmpty(this.getData(appKeyNode))) {
            for (ServiceWhitelistDTO whitelist : this.getData(appKeyNode)) {
                WhiteInfo whiteInfo = new WhiteInfo();
                switch (whitelist.getApp().getWhiteType()) {
                    case "capacity":
                        if (!owtWhite && isSetCapacityWhite(whitelist, appKeyNode)) {
                            whiteInfo.setWhiteApp(WhiteApp.CAPACITY);
                            whiteInfo.setCName(WhiteApp.CAPACITY.getCname());
                        }
                        break;
                    case "auto-migration":
                        whiteInfo.setWhiteApp(WhiteApp.AUTO_MIGRATE);
                        whiteInfo.setCName(WhiteApp.AUTO_MIGRATE.getCname());
                        break;
                    case "bj-readonly":
                        whiteInfo.setWhiteApp(WhiteApp.BJ_READONLY);
                        whiteInfo.setCName(WhiteApp.BJ_READONLY.getCname());
                        break;
                    case "utilization":
                        whiteInfo.setWhiteApp(WhiteApp.UTILIZATION);
                        whiteInfo.setCName(WhiteApp.UTILIZATION.getCname());
                        break;
                    case "alteration":
                        whiteInfo.setWhiteApp(WhiteApp.ALTERATION);
                        whiteInfo.setCName(WhiteApp.ALTERATION.getCname());
                        break;
                    default:
                        break;
                }
                if (!"capacity".equals(whitelist.getApp().getWhiteType()) || !owtWhite) {
                    whiteInfo.setEndTime(whitelist.getEndTime());
                    whiteInfo.setReason(whitelist.getReason());
                }

                if (whiteInfo.getWhiteApp() != null) {
                    appKeyNode.getWhiteInfoList().add(whiteInfo);
                }
            }
        }

    }
}
