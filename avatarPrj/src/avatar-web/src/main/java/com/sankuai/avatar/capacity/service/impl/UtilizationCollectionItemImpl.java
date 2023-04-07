package com.sankuai.avatar.capacity.service.impl;

import com.google.gson.JsonSyntaxException;
import com.meituan.mdp.boot.starter.config.annotation.MdpConfig;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.ResourceUtil;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.DomUtils;
import com.sankuai.avatar.capacity.util.GsonUtils;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxinli
 */
@Service
public class UtilizationCollectionItemImpl implements ICapacityCollectionItem {
    @MdpConfig("DOM_OFFSET:-1")
    private static Integer offset;

    @MdpConfig("BILLING_UNIT")
    private static String billingUnits;

    private static final Logger logger = LoggerFactory.getLogger(UtilizationCollectionItemImpl.class);

    private static final String WEEK_KEY = "week_appkey_utils";
    private static final String KEY_GROUP_BY_CELL = "cell_utils";
    private static final String WEEK_UTIL_WITHOUT_CELL = "appkey_utils";

    private static HashMap srvUtilMap = new HashMap();
    private static HashMap appKeyWeekUtilMap = new HashMap();
    private static HashMap cellUtilMap = new HashMap();
    private static HashMap appKeyWeekUtilWithoutCell = new HashMap();


    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    @Override
    public void collect() throws IOException {
        // cell 资源利用率
        Map<String, Double> utilGroupByCell = new HashMap<>();
        try {
            ArrayList units = GsonUtils.deserialization(billingUnits, ArrayList.class);
            Collections.synchronizedList(units).parallelStream().forEach(
                    unit -> {
                        try {
                            Map<String, Double> utils = DomUtils.getUtilizationByBillingUnitGroupByCell((String) unit, offset);
                            utilGroupByCell.putAll(utils);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } finally {
            String v = GsonUtils.serialization(utilGroupByCell);
            if (v != null && utilGroupByCell.size() > 0) {
                logger.info("本次更新{}条util group by cell资源利用率数据", utilGroupByCell.size());
                SquirrelUtils.set(KEY_GROUP_BY_CELL, v);
            }
        }

        // 周资源利用率区分set
        HashMap<String, Double> weekUtils = new HashMap<>();
        try {
            ArrayList units = GsonUtils.deserialization(billingUnits, ArrayList.class);
            Collections.synchronizedList(units).parallelStream().forEach(
                    unit -> {
                        try {
                            Map<String, Double> utils = DomUtils.getUtilizationByBillingUnit((String) unit);
                            weekUtils.putAll(utils);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } finally {
            String v = GsonUtils.serialization(weekUtils);
            if (v != null && weekUtils.size() > 0) {
                logger.info("本次更新{}条周资源利用率数据", weekUtils.size());
                SquirrelUtils.set(WEEK_KEY, v);
            }
        }

        // 周资源利用率不区分set
        HashMap<String, Double> appKeyUtils = new HashMap<>();
        try {
            ArrayList units = GsonUtils.deserialization(billingUnits, ArrayList.class);
            Collections.synchronizedList(units).parallelStream().forEach(
                    unit -> {
                        try {
                            Map<String, Double> utils = DomUtils.getAppKeyWeekUtilsByUnit((String) unit);
                            appKeyUtils.putAll(utils);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } finally {
            String utils = GsonUtils.serialization(appKeyUtils);
            if (utils != null && appKeyUtils.size() > 0) {
                logger.info("本次更新{}条周资源利用率数据", appKeyUtils.size());
                SquirrelUtils.set(WEEK_UTIL_WITHOUT_CELL, utils);
            }
        }
    }

    private static void setUtilization() {
        try {
            String v2 = SquirrelUtils.get(KEY_GROUP_BY_CELL);
            if (v2 != null && !v2.isEmpty()) {
                cellUtilMap = GsonUtils.deserialization(v2, HashMap.class);
            }
            String v1 = SquirrelUtils.get(WEEK_KEY);
            if (v1 != null && !v1.isEmpty()) {
                appKeyWeekUtilMap = GsonUtils.deserialization(v1, HashMap.class);
            }
            String v3 = SquirrelUtils.get(WEEK_UTIL_WITHOUT_CELL);
            if (v3 != null && !v3.isEmpty()) {
                appKeyWeekUtilWithoutCell = GsonUtils.deserialization(v3, HashMap.class);
            }
        } catch (JsonSyntaxException e) {
            logger.warn("未预期的json反序列化异常，" + e.getMessage());
        }
    }

    @Override
    public void refresh() {
        setUtilization();
    }

    /**
     * 获取数据
     *
     * @param appKeyNode Appkey节点
     * @return 返回
     */
    @Override
    public HashMap getData(AppKeyNode appKeyNode) {
        if (srvUtilMap == null || cellUtilMap.size() < 1 || appKeyWeekUtilMap.size() < 1) {
            refresh();
        }
        return cellUtilMap;
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        // 资源利用率
        ResourceUtil resourceUtil = ResourceUtil.builder()
                .value(0.0)
                .lastWeekValue(0.0)
                .yearPeekValue(0.0)
                .lastWeekValueWithoutSet(0.0)
                .build();
        HashMap data = this.getData(appKeyNode);
        String key = appKeyNode.getSetName().isDefault() ? appKeyNode.getAppkey() : appKeyNode.getAppkey() + "_" + appKeyNode.getSetName().getSetName();
        Object value = data.get(key);
        if (value != null) {
            resourceUtil.setValue((Double) value);
        } else {
            resourceUtil.setValue(DomUtils.getAppKeyUtilization(appKeyNode.getAppkey(), appKeyNode.getSetName().getSetName(), false));
        }
        //自然周资源利用率
        Object weekValue = appKeyWeekUtilMap.get(key);
        if (weekValue != null) {
            resourceUtil.setLastWeekValue((Double) weekValue);
        } else {
            resourceUtil.setLastWeekValue(DomUtils.getWeekAppKeyUtilizationWithCell(appKeyNode.getAppkey(), appKeyNode.getSetName().getSetName()));
        }
        Object weekValueWithoutCell = appKeyWeekUtilWithoutCell.get(key);
        if (weekValueWithoutCell != null) {
            resourceUtil.setLastWeekValueWithoutSet((Double) weekValueWithoutCell);
        } else {
            Double weekAppKeyUtilizationWithoutCell = DomUtils.getWeekAppKeyUtilizationWithoutCell(appKeyNode.getAppkey());
            if (weekAppKeyUtilizationWithoutCell <= 0) {
                logger.warn(String.format("appKey: %s, 周资源利用率获取失败, 影响资源利用率数据推送!", appKeyNode.getAppkey()));
            }
            resourceUtil.setLastWeekValueWithoutSet(weekAppKeyUtilizationWithoutCell);
        }
        // 年最大资源利用率
        try {
            Double yearPeakValue = DomUtils.getYearPeakValue(appKeyNode.getAppkey());
            resourceUtil.setYearPeekValue(yearPeakValue);
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("appKey: {}获取年最大资源利用率失败", appKeyNode.getAppkey());
            resourceUtil.setYearPeekValue(0.0);
        }
        appKeyNode.setResourceUtil(resourceUtil);
    }
}
