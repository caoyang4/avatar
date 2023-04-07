package com.sankuai.avatar.resource.orgtree.transfer;

import com.sankuai.avatar.client.soa.model.ScOrgTreeNode;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * OrgTreeNode数据对象类型转换器
 *
 * @author zhangxiaoning07
 * @create 2022/11/15
 **/
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgTreeNodeTransfer {
    /**
     * 转换器实例
     */
    OrgTreeNodeTransfer INSTANCE = Mappers.getMapper(OrgTreeNodeTransfer.class);

    /**
     * 转换节点对象
     *
     * @param node ScOrgTreeNode对象
     * @return OrgTreeNodeBO对象
     */
    @Named("toBO")
    OrgTreeNodeBO toBO(ScOrgTreeNode node);

    /**
     * 批量转换节点对象
     *
     * @param tree ScOrgTreeNode节点树
     * @return OrgTreeNodeBO节点树
     */
    @IterableMapping(qualifiedByName = "toBO")
    List<OrgTreeNodeBO> batchToBO(List<ScOrgTreeNode> tree);
}
