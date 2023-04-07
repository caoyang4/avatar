package com.sankuai.avatar.web.transfer.orgtree;

import com.sankuai.avatar.resource.orgtree.bo.OrgTreeOrgInfoBO;
import com.sankuai.avatar.resource.orgtree.bo.OrgTreeUserBO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeOrgInfoDTO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeUserDTO;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeOrgInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * OrgTreeOrgInfo数据对象类型转换器
 *
 * @author zhangxiaoning07
 * @create 2022-11-15
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgTreeOrgInfoTransfer {
    /**
     * 转换器实例
     */
    OrgTreeOrgInfoTransfer INSTANCE = Mappers.getMapper(OrgTreeOrgInfoTransfer.class);

    /**
     * BO到DTO转换
     *
     * @param orgTreeOrgInfoBO OrgTreeOrgInfoBO对象
     * @param userBO           OrgTreeUserBO对象，是orgTreeOrgInfoBO参数的leader属性
     * @return OrgTreeOrgInfoDTO对象
     */
    @Mapping(source = "userBO.", target = "leader.")
    OrgTreeOrgInfoDTO toDTO(OrgTreeOrgInfoBO orgTreeOrgInfoBO, OrgTreeUserBO userBO);

    /**
     * DTO到VO转换
     *
     * @param orgTreeOrgInfoDTO OrgTreeOrgInfoDTO对象
     * @param userDTO           OrgTreeOrgInfoDTO对象，是orgTreeOrgInfoDTO参数的leader属性
     * @return OrgTreeOrgInfoVO对象
     */
    @Mapping(source = "userDTO.", target = "leader.")
    OrgTreeOrgInfoVO toVO(OrgTreeOrgInfoDTO orgTreeOrgInfoDTO, OrgTreeUserDTO userDTO);

}
