package com.sankuai.avatar.capacity.property;

import com.google.common.collect.ImmutableSet;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.rocket.host.instance.openapi.model.HostDTO;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import org.apache.commons.collections.CollectionUtils;

import java.util.Objects;
import java.util.Set;

/**
 * @author caoyang
 * @create 2023-01-16 10:06
 */
public class IsSpecialOwtProperty extends AbstractProperty<Boolean> {

    private static final Set<String> SPECIAL_OWT = ImmutableSet.of("meituan.data", "meituan.buffer");
    private static final Set<String> GPU_OWT = ImmutableSet.of("meituan.vision", "meituan.ai", "meituan.nlp");

    @Override
    public Boolean execute(Node node) {
        AppKeyNode appKeyNode = (AppKeyNode) node;
        String owt = appKeyNode.getOwt();
        if (GPU_OWT.contains(owt) && isGpuHost(appKeyNode.getAppkey())) {
            setDescription("该服务属于owt：meituan.vision，且服务全部是GPU机器，avatar不予计算容灾等级");
            return true;
        }
        if (SPECIAL_OWT.contains(owt)) {
            setDescription(String.format("该服务属于owt: %s，avatar不予计算容灾等级", appKeyNode.getOwt()));
        }
        return SPECIAL_OWT.contains(owt);
    }

    private boolean isGpuHost(String appkey){
        PageResultHostDTO resultHostDTO = RocketUtil.httpGetHostDataByAppKey(appkey);
        if (Objects.isNull(resultHostDTO) || CollectionUtils.isEmpty(resultHostDTO.getData())) {
            return false;
        }
        for (HostDTO hostDTO : resultHostDTO.getData()) {
            if (!Objects.equals("hulk-yarn", hostDTO.getVendor())) {
                return false;
            }
        }
        return true;
    }
}

