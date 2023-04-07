package com.sankuai.avatar.web.transfer.orgRole;

import com.sankuai.avatar.web.dto.orgRole.OrgSreTreeDTO;
import com.sankuai.avatar.web.dto.user.DxUserDTO;
import com.sankuai.avatar.web.transfer.user.UserVOTransfer;
import com.sankuai.avatar.web.vo.orgRole.OrgSreTreeVO;
import com.sankuai.avatar.web.vo.user.DxUserVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2022-11-14 13:22
 */
/**
 * @author caoyang
 * @create 2022-11-14 11:32
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgSreTreeVOTransfer {
    OrgSreTreeVOTransfer INSTANCE = Mappers.getMapper(OrgSreTreeVOTransfer.class);

    /**
     * toVO
     *
     * @param orgSreTreeDTO orgSreTreeDTO
     * @return {@link OrgSreTreeVO}
     */
    @Mapping(source = "opAdmins", target = "opAdmins", qualifiedByName = "toDxUserVOList")
    @Named("toVO")
    OrgSreTreeVO toVO(OrgSreTreeDTO orgSreTreeDTO);

    /**
     * toVOList
     *
     * @param orgSreTreeDTOList orgSreTreeDTOList
     * @return {@link List}<{@link OrgSreTreeVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<OrgSreTreeVO> toVOList(List<OrgSreTreeDTO> orgSreTreeDTOList);

    /**
     * volist
     *
     * @param dtoList dto列表
     * @return {@link List}<{@link DxUserVO}>
     */
    @Named("toDxUserVOList")
    default List<DxUserVO> toDxUserVOList(List<DxUserDTO> dtoList){
        return UserVOTransfer.INSTANCE.toDxUserVOListByDto(dtoList);
    }
}
