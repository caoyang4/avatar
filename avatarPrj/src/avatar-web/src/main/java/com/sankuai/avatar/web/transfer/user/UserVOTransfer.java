package com.sankuai.avatar.web.transfer.user;

import com.sankuai.avatar.web.dto.user.DxUserDTO;
import com.sankuai.avatar.web.dto.user.UserDTO;
import com.sankuai.avatar.web.vo.user.DxUserVO;
import com.sankuai.avatar.web.vo.user.UserLoginVO;
import com.sankuai.avatar.web.vo.user.UserVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * User VO 转换器
 * @author caoyang
 * @create 2022-11-01 16:31
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface UserVOTransfer {
    UserVOTransfer INSTANCE = Mappers.getMapper(UserVOTransfer.class);

    /**
     * DTO -> VO
     *
     * @param userDTO DTO
     * @return {@link UserVO}
     */
    @Mapping(source = "mis", target = "loginName")
    @Named("toVO")
    UserVO toVO(UserDTO userDTO);

    /**
     * DTO -> VO
     *
     * @param userDTO 用户dto
     * @return {@link UserLoginVO}
     */
    @Mapping(source = "mis", target = "loginName")
    @Named("toVO")
    UserLoginVO toLoginVO(UserDTO userDTO);

    /**
     * 批量转换 DTO -> VO
     *
     * @param userDTOList DTOList
     * @return {@link List}<{@link UserVO}>
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<UserVO> toVOList(List<UserDTO> userDTOList);

    /**
     * VO -> DTO
     *
     * @param userVO VO
     * @return {@link UserDTO}
     */
    @Mapping(source = "loginName", target = "mis")
    @Mapping(target = "id", ignore = true)
    @Named("toDTO")
    UserDTO toDTO(UserVO userVO);

    /**
     * 批量转换 VO -> DTO
     *
     * @param userVOList VOList
     * @return {@link List}<{@link UserDTO}>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<UserDTO> toDTOList(List<UserVO> userVOList);

    /**
     * toDxUserVO
     *
     * @param userDTO userDTO
     * @return {@link DxUserVO}
     */
    @Mapping(source = "organization", target = "org")
    @Named("toDxUserVO")
    DxUserVO toDxUserVO(UserDTO userDTO);

    /**
     * 批量转换toDxUserVO
     *
     * @param userDTOList userDTOList
     * @return {@link List}<{@link DxUserVO}>
     */
    @IterableMapping(qualifiedByName = "toDxUserVO")
    @Named("toDxUserVOList")
    List<DxUserVO> toDxUserVOList(List<UserDTO> userDTOList);

    /**
     * toDxUserVO
     *
     * @param dxUserDTO dxUserDTO
     * @return {@link DxUserVO}
     */
    @Named("toDxUserVO")
    DxUserVO toDxUserVOByDto(DxUserDTO dxUserDTO);

    /**
     * 批量转换toDxUserVO
     *
     * @param dxUserDTOList dxUserDTOList
     * @return {@link List}<{@link DxUserVO}>
     */
    @IterableMapping(qualifiedByName = "toDxUserVO")
    @Named("toDxUserVOList")
    List<DxUserVO> toDxUserVOListByDto(List<DxUserDTO> dxUserDTOList);
}
