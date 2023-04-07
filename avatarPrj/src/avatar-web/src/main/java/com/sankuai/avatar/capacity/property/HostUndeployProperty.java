package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.constant.MiddleWareName;
import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.avatar.common.utils.DateUtils;
import com.sankuai.rocket.host.instance.openapi.model.HostDTO;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2023-03-06 11:58
 */
public class HostUndeployProperty extends AbstractProperty<Boolean> {

    @Override
    public Boolean execute(Node node) {
        if (node.isPlusDeployed() || node.hasMiddleWare(MiddleWareName.MGW)) {
            return false;
        }
        if (node.hasMiddleWare(MiddleWareName.OCTO_HTTP) || node.hasMiddleWare(MiddleWareName.OCTO_TRIFT)) {
            return false;
        }
        AppKeyNode appKeyNode = (AppKeyNode) node;
        PageResultHostDTO resultHostDTO = RocketUtil.httpGetHostDataByAppKey(appKeyNode.getAppkey());
        List<HostDTO> data = resultHostDTO.getData();
        if (CollectionUtils.isEmpty(data)){return false;}
        data = data.stream().filter(hostDTO -> Objects.equals(appKeyNode.getSetName().getSetName(), hostDTO.getCell())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(data) || !isAllHostNewCreate(data)) {
            return false;
        }
        // 服务下所有机器最早的创建时间在30天以内，且没有Plus的发布记录，且未注册 octo、mgw
        setDescription("该链路机器均在30天内创建，且未发布，不计算容灾");
        return true;
    }

    private boolean isAllHostNewCreate(List<HostDTO> hosts){
        for (HostDTO hostDTO : hosts) {
            // 存在机器申请超过 30 天，计算容灾
            if (Objects.isNull(hostDTO) || Objects.isNull(hostDTO.getCtime()) || StringUtils.isEmpty(hostDTO.getCtime())) {
                return false;
            }
            String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            if (DateUtils.isOverTimeGapByDays(hostDTO.getCtime(), format,30)) {
                return false;
            }
        }
        // 服务下所有机器最早的创建时间在30天以内
        return true;
    }

}
