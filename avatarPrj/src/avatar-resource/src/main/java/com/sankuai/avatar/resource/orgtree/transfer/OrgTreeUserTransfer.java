package com.sankuai.avatar.resource.orgtree.transfer;

import com.sankuai.avatar.client.soa.model.ScUser;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * User数据对象类型转换器
 *
 * @author zhangxiaoning07
 * @create 2022/11/15
 **/
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgTreeUserTransfer {
    /**
     * User数据类型转换器
     */
    OrgTreeUserTransfer INSTANCE = Mappers.getMapper(OrgTreeUserTransfer.class);

    /**
     * 转换为BO
     *
     * @param scUser ScUser对象
     * @return OrgTreeUserBO对象
     */
    OrgTreeUserBO toBO(ScUser scUser);

}

