package com.sankuai.avatar.workflow.server.transfer;

import com.sankuai.avatar.dao.es.entity.FlowSearchEntity;
import com.sankuai.avatar.workflow.server.dto.flow.SearchFlowDTO;
import com.sankuai.avatar.workflow.server.vo.flow.SearchFlowVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface SearchFlowTransfer {
    SearchFlowTransfer INSTANCE = Mappers.getMapper(SearchFlowTransfer.class);

    /**
     * FlowSearchEntity 转 SearchFlowDTO
     *
     * @param entity FlowSearchEntity
     * @return SearchFlowDTO
     */
    @Mapping(source = "uuid", target = "applyId")
    @Mapping(source = "templateName", target = "applyName")
    SearchFlowDTO entityToDTO(FlowSearchEntity entity);

    /**
     * List<FlowSearchEntity> 转 List<SearchFlowDTO>
     *
     * @param searchFlowEntities List<FlowSearchEntity>
     * @return List<SearchFlowDTO>
     */
    @IterableMapping(qualifiedByName = "entityToDTO")
    List<SearchFlowDTO> entityToDTOs(List<FlowSearchEntity> searchFlowEntities);

    /**
     * SearchFlowDTO 转  SearchFlowVO
     *
     * @param searchFlowDTO SearchFlowDTO
     * @return SearchFlowVO
     */
    SearchFlowVO dtoToVO(SearchFlowDTO searchFlowDTO);

    /**
     * List<SearchFlowDTO> 转 List<SearchFlowVO>
     *
     * @param searchFlowDTOList
     * @return
     */
    @IterableMapping(qualifiedByName = "toVO")
    List<SearchFlowVO> dtoToVOs(List<SearchFlowDTO> searchFlowDTOList);
}
