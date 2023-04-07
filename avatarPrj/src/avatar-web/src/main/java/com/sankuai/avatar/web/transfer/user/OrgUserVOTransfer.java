package com.sankuai.avatar.web.transfer.user;

import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.vo.user.OrgUserVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * orgUser 转换器
 * @author caoyang
 * @create 2022-11-01 17:41
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface OrgUserVOTransfer {
    OrgUserVOTransfer INSTANCE = Mappers.getMapper(OrgUserVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param userDTO 用户dto
     * @return {@link OrgUserVO}
     */
    @Mapping(source = "organization", target = "orgName", defaultValue = "")
    @Mapping(source = "orgPath", target = "org", defaultValue = "")
    @Mapping(source = "role", target = "role", defaultValue = "")
    @Mapping(source = "leader", target = "leader", defaultValue = "")
    @Mapping(source = "source", target = "source", defaultValue = "")
    @Mapping(source = "orgId", target = "orgId", defaultValue = "")
    @Mapping(source = "avatarUrl", target = "avatarUrl", defaultValue = "")
    @Mapping(source = "jobStatus", target = "jobStatus", defaultValue = "")
    @Named("toVO")
    OrgUserVO toVO(UserDTO userDTO);

    /**
     * 批量转换DTO -> VO
     *
     * @param userDTOList 用户dtolist
     * @return {@link List}<{@link OrgUserVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<OrgUserVO> toVOList(List<UserDTO> userDTOList);
}
