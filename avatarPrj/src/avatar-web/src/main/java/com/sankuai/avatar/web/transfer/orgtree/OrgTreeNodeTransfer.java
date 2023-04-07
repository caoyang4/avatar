package com.sankuai.avatar.web.transfer.orgtree;

import com.sankuai.avatar.resource.orgtree.bo.OrgTreeNodeBO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeNodeDTO;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeNodeVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * OrgTreeNode数据对象类型转换器
 *
 * @author zhangxiaoning07
 * @create 2022-11-15
 */
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
     * BO转换到DTO
     *
     * @param nodeBO OrgTreeNodeBO对象
     * @return OrgTreeNodeDTO对象
     */
    @Named("toDTO")
    OrgTreeNodeDTO toDTO(OrgTreeNodeBO nodeBO);

    /**
     * BO列表批量转换DTO列表
     *
     * @param BOTree OrgTreeNodeBO节点树的列表
     * @return OrgTreeNodeDTO节点树的列表
     */
    @IterableMapping(qualifiedByName = "toDTO")
    List<OrgTreeNodeDTO> batchToDTO(List<OrgTreeNodeBO> BOTree);

    /**
     * DTO转换到VO
     *
     * @param nodeDTO OrgTreeNodeDTO对象
     * @return OrgTreeNodeVO对象
     */
    @Named("toVO")
    OrgTreeNodeVO toVO(OrgTreeNodeDTO nodeDTO);

    /**
     * DTO批量转换VO列表
     *
     * @param DTOTree OrgTreeNodeDTO节点树的列表
     * @return OrgTreeNodeVO节点树的列表
     */
    @IterableMapping(qualifiedByName = "toVO")
    List<OrgTreeNodeVO> batchToVO(List<OrgTreeNodeDTO> DTOTree);
}
