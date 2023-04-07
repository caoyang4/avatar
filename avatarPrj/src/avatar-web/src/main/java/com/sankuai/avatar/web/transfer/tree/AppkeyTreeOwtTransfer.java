package com.sankuai.avatar.web.transfer.tree;

import com.sankuai.avatar.resource.tree.bo.AppkeyTreeOwtBO;
import com.sankuai.avatar.resource.tree.bo.OwtOrgBO;
import com.sankuai.avatar.web.dto.tree.AppkeyTreeOwtDTO;
import com.sankuai.avatar.web.vo.tree.AppkeyTreeOwtVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AppkeyTree的Owt对象转换器
 *
 * @author zhangxiaoning07
 * @create 2022-10-24
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyTreeOwtTransfer {

    AppkeyTreeOwtTransfer INSTANCE = Mappers.getMapper(AppkeyTreeOwtTransfer.class);

    /**
     * BO转换为DTO
     * @param appkeyTreeOwtBO AppkeyTreeOwtBO对象
     * @return AppkeyTreeOwtDTO
     */
    @Mapping(target = "userCanEdit", ignore = true)
    @Mapping(source = "org", target = "org", qualifiedByName = "toOrg")
    @Named("toDTO")
    AppkeyTreeOwtDTO toDTO(AppkeyTreeOwtBO appkeyTreeOwtBO);


    /**
     * BO列表批量转换为DTO列表
     *
     * @param appkeyTreeOwtBO AppkeyTreeOwtBO对象
     * @return List<AppkeyTreeOwtDTO>
     */
    @IterableMapping(qualifiedByName = "toDTO")
    @Named("toDTOList")
    List<AppkeyTreeOwtDTO> toDTOList(List<AppkeyTreeOwtBO> appkeyTreeOwtBO);

    /**
     * DTO转换为VO
     * @param appkeyTreeOwtDTO AppkeyTreeOwtDTO对象
     * @return OwtVO对象
     */
    @Mapping(target = "rdAdmin", ignore = true)
    @Mapping(target = "opAdmin", ignore = true)
    @Mapping(target = "epAdmin", ignore = true)
    @Named("toVO")
    AppkeyTreeOwtVO toVO(AppkeyTreeOwtDTO appkeyTreeOwtDTO);

    /**
     * DTO列表批量转换为VO列表
     * @param appkeyTreeOwtDTOList AppkeyTreeOwtDTO对象列表
     * @return AppkeyTreeOwtVO对象列表
     */
    @IterableMapping(qualifiedByName = "toVO")
    @Named("toVOList")
    List<AppkeyTreeOwtVO> toVOList(List<AppkeyTreeOwtDTO> appkeyTreeOwtDTOList);

    /**
     * org支取中文路径
     *
     * @param orgBOList orgBOList
     * @return {@link List}<{@link String}>
     */
    default List<String> toOrg(List<OwtOrgBO> orgBOList){
        if (CollectionUtils.isEmpty(orgBOList)) {
            return Collections.emptyList();
        }
        return orgBOList.stream().map(OwtOrgBO::getPath).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
    }
}



