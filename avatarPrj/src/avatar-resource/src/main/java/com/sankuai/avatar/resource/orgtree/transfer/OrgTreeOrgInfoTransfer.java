package com.sankuai.avatar.resource.orgtree.transfer;

import com.sankuai.avatar.client.soa.model.ScOrg;
import com.sankuai.avatar.client.soa.model.ScUser;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeOrgInfoBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * OrgTreeOrgInfo数据对象类型转换器
 *
 * @author zhangxiaoning07
 * @create 2022/11/15
 **/
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgTreeOrgInfoTransfer {
    /**
     * OrgTreeOrgInfo数据对象类型转换器
     */
    OrgTreeOrgInfoTransfer INSTANCE = Mappers.getMapper(OrgTreeOrgInfoTransfer.class);

    /**
     * 转换为BO
     *
     * @param scOrg ScOrg对象
     * @return OrgTreeOrgInfoBO对象
     */
    @Mapping(source = "leader", target = "leader", qualifiedByName = "toOrgTreeUserBO")
    OrgTreeOrgInfoBO toBO(ScOrg scOrg);

    @Named("toOrgTreeUserBO")
    default OrgTreeUserBO toOrgTreeUserBO(ScUser scUser) {
        return OrgTreeUserTransfer.INSTANCE.toBO(scUser);
    }
}
