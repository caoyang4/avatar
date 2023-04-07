package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.rocket.host.instance.openapi.model.HostDTO;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2023-03-01 10:17
 */
public class HostUnknownProperty extends AbstractProperty<Boolean> {
    @Override
    public Boolean execute(Node node) {
        AppKeyNode appKeyNode = (AppKeyNode) node;
        PageResultHostDTO resultHostDTO = RocketUtil.httpGetHostDataByAppKey(appKeyNode.getAppkey());
        List<HostDTO> data = resultHostDTO.getData();
        if (CollectionUtils.isEmpty(data)){return false;}
        data = data.stream().filter(hostDTO -> Objects.equals(appKeyNode.getSetName().getSetName(), hostDTO.getCell())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(data) && CollectionUtils.isEmpty(node.getHostList())){
            setDescription("该服务下所有机器所属地域未知，avatar不予计算容灾等级");
            return true;
        }
        return false;
    }
}
