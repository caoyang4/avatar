package com.sankuai.avatar.capacity.property;

import com.sankuai.avatar.capacity.node.AppKeyNode;
import com.sankuai.avatar.capacity.node.Host;
import com.sankuai.avatar.capacity.node.Node;
import com.sankuai.avatar.capacity.util.DayuUtils;
import com.sankuai.avatar.capacity.util.RocketUtil;
import com.sankuai.rocket.host.instance.openapi.model.HostDTO;
import com.sankuai.rocket.host.instance.openapi.model.PageResultHostDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author caoyang
 * @create 2022-12-27 14:47
 */
public class IsSetHostBalanceProperty extends AbstractProperty<Boolean>{

    @Override
    public String getName() {
        return " SET均衡部署";
    }

    @Override
    public Boolean execute(Node node) {
        List<Host> hostList = node.getHostList();
        if (CollectionUtils.isEmpty(hostList)) {
            return false;
        }
        Host base = hostList.get(0);
        for (Host host : hostList) {
            // set 自身机器配置不均衡
            if (!isSameHostConfig(base, host)) {
                return false;
            }
        }
        AppKeyNode appKeyNode = (AppKeyNode) node;
        String backupCell = DayuUtils.getBackupCell(appKeyNode.getSetName().getSetName());
        if (StringUtils.isEmpty(backupCell)) {return false;}
        PageResultHostDTO resultHostDTO = RocketUtil.httpGetHostDataByAppKey(appKeyNode.getAppkey());
        List<HostDTO> hostDTOList = resultHostDTO.getData();
        // 数量均衡
        if (CollectionUtils.isEmpty(hostDTOList)) {
            return false;
        }
        hostDTOList = hostDTOList.stream().filter(h -> Objects.equals(h.getCell(), backupCell)).collect(Collectors.toList());
        if (!Objects.equals(hostList.size(), hostDTOList.size())) {
            return false;
        }
        // 配置均衡
        HostDTO hostDTO = hostDTOList.stream().filter(h -> !(Objects.equals(base.getMemSize(), h.getMemSize()) && Objects.equals(base.getCpuCount(), h.getCpuCount())))
                .findFirst().orElse(null);
        return Objects.isNull(hostDTO);
    }

    private boolean isSameHostConfig(Host host1, Host host2){
        if (Objects.isNull(host1) || Objects.isNull(host2) || Objects.isNull(host1.getCpuCount()) || Objects.isNull(host1.getMemSize())) {
            return false;
        }
        return Objects.equals(host1.getCpuCount(), host2.getCpuCount()) && Objects.equals(host1.getMemSize(), host2.getMemSize());
    }
}
