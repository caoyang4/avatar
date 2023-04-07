package com.sankuai.avatar.web.transfer.tree;

import com.sankuai.avatar.resource.tree.bo.AppkeyTreeOwtBO;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreePdlBO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeOwtDTO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreePdlDTO;
import com.sankuai.avatar.web.vo.tree.AppkeyTreePdlVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zhangxiaoning07
 * @create 2022/11/1
 **/
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyTreePdlTransfer {

    AppkeyTreePdlTransfer INSTANCE = Mappers.getMapper(AppkeyTreePdlTransfer.class);

    /**
     * BO转换为DTO
     *
     * @param appkeyTreePdlBO OpsPdlBO对象
     * @return AppkeyTreePdlDTO对象
     */
    @Mapping(source = "owt", target = "owt", qualifiedByName = "toOwtDTO")
    @Named("toDTO")
    AppkeyTreePdlDTO toDTO(AppkeyTreePdlBO appkeyTreePdlBO);



    /**
     * BO批量转换为DTO
     *
     * @param appkeyTreePdlBOList OpsPdlBO列表
     * @return AppkeyTreePdlDTO对象列表
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<AppkeyTreePdlDTO> toDTOList(List<AppkeyTreePdlBO> appkeyTreePdlBOList);

    /**
     * DTO转换为VO
     *
     * @param appkeyTreePdlDTO AppkeyTreePdlDTO对象
     * @return AppkeyTreePdlVO对象
     */
    @Mapping(target = "rdAdmin", ignore = true)
    @Mapping(target = "opAdmin", ignore = true)
    @Mapping(target = "epAdmin", ignore = true)
    @Mapping(target = "owt", ignore = true)
    @Named("toVO")
    AppkeyTreePdlVO toVO(AppkeyTreePdlDTO appkeyTreePdlDTO);

    /**
     * DTO列表批量转换为VO列表
     *
     * @param appkeyTreePdlDTOList AppkeyTreePdlDTO对象列表
     * @return AppkeyTreePdlVO对象列表
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<AppkeyTreePdlVO> toVOList(List<AppkeyTreePdlDTO> appkeyTreePdlDTOList);

    /**
     * BO -> DTO
     *
     * @param owtBO owtBO
     * @return {@link AppkeyTreeOwtDTO}
     */
    default AppkeyTreeOwtDTO toOwtDTO(AppkeyTreeOwtBO owtBO){
        return AppkeyTreeOwtTransfer.INSTANCE.toDTO(owtBO);
    }

}
