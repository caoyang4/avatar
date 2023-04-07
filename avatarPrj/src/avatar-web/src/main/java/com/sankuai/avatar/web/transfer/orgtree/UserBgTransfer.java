package com.sankuai.avatar.web.transfer.orgtree;
import com.sankuai.avatar.resource.tree.bo.UserBgBO;
import com.sankuai.avatar.web.dto.tree.UserBgDTO;
import com.sankuai.avatar.web.dto.tree.UserOwtDTO;
import com.sankuai.avatar.web.dto.tree.UserPdlDTO;
import com.sankuai.avatar.web.vo.orgtree.UserBgVO;
import com.sankuai.avatar.web.vo.tree.BgTreeVO;
import com.sankuai.avatar.web.vo.tree.OwtTreeVO;
import com.sankuai.avatar.web.vo.tree.PdlTreeVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import java.util.List;
/**
 * @author caoyang
 * @create 2023-01-06 15:36
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface UserBgTransfer {
    UserBgTransfer INSTANCE = Mappers.getMapper(UserBgTransfer.class);
    /**
     * BO -> DTO
     *
     * @param bgBO bgBO
     * @return {@link UserBgDTO}
     */
    @Named("toBgDTO")
    UserBgDTO toBgDTO(UserBgBO bgBO);
    /**
     * 批量转换BO -> DTO
     *
     * @param bgBOList bgBOList
     * @return {@link List}<{@link UserBgDTO}>
     */
    @IterableMapping(qualifiedByName = "toBgDTO")
    @Named("toPdlDTOList")
    List<UserBgDTO> toBgDTOList(List<UserBgBO> bgBOList);
    /**
     * DTO -> VO
     *
     * @param bgDTO bgDTO
     * @return {@link UserBgDTO}
     */
    @Named("toBgVO")
    UserBgVO toBgVO(UserBgDTO bgDTO);
    /**
     * 批量转换DTO -> VO
     *
     * @param bgDTOList bgDTOList
     * @return {@link List}<{@link UserBgDTO}>
     */
    @IterableMapping(qualifiedByName = "toBgVO")
    @Named("toBgVOList")
    List<UserBgVO> toBgVOList(List<UserBgDTO> bgDTOList);

    @Mapping(source = "pdlName", target = "text")
    @Mapping(source = "pdl", target = "value")
    @Named("toPdlTreeVO")
    PdlTreeVO toPdlTreeVO(UserPdlDTO dto);

    @IterableMapping(qualifiedByName = "toPdlTreeVO")
    @Named("toPdlTreeVOList")
    List<PdlTreeVO> toPdlTreeVOList(List<UserPdlDTO> dtoList);

    @Mapping(source = "owtName", target = "text")
    @Mapping(source = "owt", target = "value")
    @Mapping(source = "pdlList", target = "children", qualifiedByName = "toPdlTreeVOList")
    @Named("toOwtTreeVO")
    OwtTreeVO toOwtTreeVO(UserOwtDTO dto);

    @IterableMapping(qualifiedByName = "toOwtTreeVO")
    @Named("toOwtTreeVOList")
    List<OwtTreeVO> toOwtTreeVOList(List<UserOwtDTO> dtoList);

    @Mapping(source = "bgName", target = "text")
    @Mapping(source = "bgName", target = "value")
    @Mapping(source = "owtList", target = "children", qualifiedByName = "toOwtTreeVOList")
    @Named("toBgTreeVO")
    BgTreeVO toBgTreeVO(UserBgDTO dto);

    @IterableMapping(qualifiedByName = "toBgTreeVO")
    @Named("toBgTreeVOList")
    List<BgTreeVO> toBgTreeVOList(List<UserBgDTO> dtoList);

}