package com.sankuai.avatar.capacity.service.impl;

import com.google.common.collect.ImmutableSet;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.service.ICapacityCollectionItem;
import com.sankuai.avatar.capacity.util.GsonUtils;
import com.sankuai.avatar.capacity.util.OpsUtils;
import com.sankuai.avatar.capacity.util.SquirrelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chenxinli
 */
@Service
public class PaasCollectionItemImpl implements ICapacityCollectionItem {

    private static final String pKey = "pass_owt";
    private static final Set<String> IGNORE_PAAS = new HashSet<>();
    private static final Set<String> SPECIAL_PAAS = ImmutableSet.of(
            "meituan.inf.eagle",
            "meituan.hulk.eventetcd",
            "meituan.hulk.etcd",
            "dianping.infsh.shark",
            "meituan.inf.riverrun",
            "dianping.infsh.crane",
            "meituan.inf.crane",
            "meituan.mss",
            "meituan.ebs",
            "meituan.efs",
            "meituan.mstore",
            "meituan.serverless",
            "meituan.cloudinf.node",
            "meituan.mtsdb",
            "meituan.inf.swarm",
            "meituan.inf.image"
    );

    private Set<String> calculatePaas = ImmutableSet.of("meituan.buffalo");

    private static ArrayList paasList = new ArrayList();

    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    @Override
    public void collect() throws IOException {
        List<String> paasOwtList;
        try {
            paasOwtList = OpsUtils.getPaasOwtList();
            SquirrelUtils.set(pKey, GsonUtils.serialization(paasOwtList));
        } catch (Exception ignored) {
        }
    }

    @Override
    public void refresh() {
        String result = SquirrelUtils.get(pKey);
        if (result != null && !result.isEmpty()) {
            ArrayList objects = GsonUtils.deserialization(result, ArrayList.class);
            for (Object obj : objects) {
                if (IGNORE_PAAS.contains(obj.toString())) {
                    continue;
                }
                paasList.add(obj);
            }

        }
    }

    /**
     * 获取存入数据
     *
     * @return 列表数据
     */
    @Override
    public Boolean getData(AppKeyNode appKeyNode) {
        if (CollectionUtils.isEmpty(paasList)) {
            refresh();
        }
        if (appKeyNode.getSrv() == null) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(paasList)) {
            return containsPaas(paasList, appKeyNode);
        } else {
            List<String> paasOwtList = new ArrayList<>();
            try {
                paasOwtList = OpsUtils.getPaasOwtList();
            } catch (Exception ignored) {
            }
            SquirrelUtils.set(pKey, GsonUtils.serialization(paasOwtList));
            return containsPaas((ArrayList) paasOwtList, appKeyNode);
        }
    }

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     */
    @Override
    public void setAppkeyCapacityProperty(AppKeyNode appKeyNode) {
        appKeyNode.setPaas(this.getData(appKeyNode));
        String[] srvArray = appKeyNode.getSrv().split("\\.");
        String owt = srvArray[0] + "." + srvArray[1];
        appKeyNode.setCalculate(!this.getData(appKeyNode) || calculatePaas.contains(owt));
    }

    private Boolean containsPaas(ArrayList list, AppKeyNode appKeyNode) {
        String owt = appKeyNode.getOwt();
        if (StringUtils.isEmpty(owt)) {
            owt = OpsUtils.getOwtFromSrv(appKeyNode.getSrv());
            appKeyNode.setOwt(owt);
        }
        return list.contains(owt) || specialPaas(appKeyNode);
    }

    private Boolean specialPaas(AppKeyNode appKeyNode){
        for (String owt : SPECIAL_PAAS) {
            if (appKeyNode.getSrv().startsWith(owt)) {
                return true;
            }
        }
        return false;
    }

}
