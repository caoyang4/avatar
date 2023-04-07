package com.sankuai.avatar.web.transfer.user;

import com.sankuai.avatar.resource.user.bo.UserBO;
import com.sankuai.avatar.web.dto.user.DxUserDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * User DTO对象转换器
 * @author caoyang
 * @create 2022-11-01 16:12
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface UserDTOTransfer {
    UserDTOTransfer INSTANCE = Mappers.getMapper(UserDTOTransfer.class);

    /**
     * BO -> DTO
     *
     * @param userBO BO
     * @return {@link UserDTO}
     */
    @Mapping(source = "userImage", target = "avatarUrl")
    @Named("toDTO")
    UserDTO toDTO(UserBO userBO);

    /**
     * 批量转换 BO -> DTO
     *
     * @param userBOList BOList
     * @return {@link List}<{@link UserDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<UserDTO> toDTOList(List<UserBO> userBOList);

    /**
     * DTO -> BO
     *
     * @param userDTO DTO
     * @return {@link UserBO}
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "avatarUrl", target = "userImage")
    @Named("toBO")
    UserBO toBO(UserDTO userDTO);

    /**
     * 批量转换 DTO -> BO
     *
     * @param userDTOList DTOList
     * @return {@link List}<{@link UserBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<UserBO> toBOList(List<UserDTO> userDTOList);

    /**
     * 转换dto
     *
     * @param userBO userBO
     * @return {@link DxUserDTO}
     */
    @Mapping(source = "organization", target = "org")
    @Mapping(source = "userImage", target = "avatarUrl")
    @Named("toDxUserDTO")
    DxUserDTO toDxUserDTO(UserBO userBO);

    /**
     * 批量转换dto
     *
     * @param userBOList userBOList
     * @return {@link List}<{@link DxUserDTO}>
     */
    @IterableMapping(qualifiedByName = "toDxUserDTO")
    @Named("toDxUserDTOList")
    List<DxUserDTO> toDxUserDTOList(List<UserBO> userBOList);
}
