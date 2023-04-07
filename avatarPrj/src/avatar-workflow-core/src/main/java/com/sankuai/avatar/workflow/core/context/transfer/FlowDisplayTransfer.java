package com.sankuai.avatar.workflow.core.context.transfer;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.workflow.core.context.FlowDisplay;
import com.sankuai.avatar.workflow.core.display.model.DiffDisplay;
import com.sankuai.avatar.workflow.core.display.model.InputDisplay;
import com.sankuai.avatar.workflow.core.display.model.OutputDisplay;
import com.sankuai.avatar.workflow.core.display.model.TextDisplay;
import com.sankuai.avatar.dao.workflow.repository.entity.FlowDisplayEntity;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayAddRequest;
import com.sankuai.avatar.dao.workflow.repository.request.FlowDisplayUpdateRequest;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

/**
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface FlowDisplayTransfer {
    FlowDisplayTransfer INSTANCE = Mappers.getMapper(FlowDisplayTransfer.class);

    /**
     * FlowDisplayEntity to FlowDisplay
     *
     * @param flowDisplayEntity {@link FlowDisplayEntity}
     * @return {@link FlowDisplay}
     */
    @Mapping(source = "input", target = "input", qualifiedByName = "toInputList")
    @Mapping(source = "output", target = "output", qualifiedByName = "toOutput")
    @Mapping(source = "diff", target = "diff", qualifiedByName = "toDiff")
    @Mapping(source = "text", target = "text", qualifiedByName = "toTextList")
    FlowDisplay toFlowDisplay(FlowDisplayEntity flowDisplayEntity);

    /**
     * FlowDisplay to  FlowDisplayAddRequest
     *
     * @param flowDisplay {@link FlowDisplay}
     * @return {@link FlowDisplayAddRequest}
     */
    @Mapping(source = "input", target = "input", qualifiedByName = "toJsonString")
    @Mapping(source = "output", target = "output", qualifiedByName = "toJsonString")
    @Mapping(source = "diff", target = "diff", qualifiedByName = "toJsonString")
    @Mapping(source = "text", target = "text", qualifiedByName = "toJsonString")
    FlowDisplayAddRequest toAddRequest(FlowDisplay flowDisplay);


    /**
     * FlowDisplay to  FlowDisplayUpdateRequest
     *
     * @param flowDisplay {@link FlowDisplay}
     * @return {@link FlowDisplayUpdateRequest}
     */
    @Mapping(source = "input", target = "input", qualifiedByName = "toJsonString")
    @Mapping(source = "output", target = "output", qualifiedByName = "toJsonString")
    @Mapping(source = "diff", target = "diff", qualifiedByName = "toJsonString")
    @Mapping(source = "text", target = "text", qualifiedByName = "toTextString")
    FlowDisplayUpdateRequest toUpdateRequest(FlowDisplay flowDisplay);

    /**
     * Bean to Json
     *
     * @param object Object
     * @return String
     */
    @Named("toJsonString")
    static String toJsonString(Object object) {
        if (object == null) {
            return "";
        }
        return JsonUtil.bean2Json(object);
    }

    @Named("toInputList")
    static List<InputDisplay> toInputList(String input) {
        if (StringUtils.isEmpty(input)) {
            return Collections.emptyList();
        }
        return JsonUtil.json2List(input, InputDisplay.class);
    }

    @Named("toTextList")
    static List<TextDisplay> toTextList(String text) {
        if (StringUtils.isEmpty(text)) {
            return Collections.emptyList();
        }
        return JsonUtil.json2List(text, TextDisplay.class);
    }

    @Named("toDiff")
    static DiffDisplay toDiff(String diff) {
        if (StringUtils.isEmpty(diff)) {
            return null;
        }
        return JsonUtil.json2Bean(diff, DiffDisplay.class);
    }

    @Named("toOutput")
    static OutputDisplay toOutput(String output) {
        if (StringUtils.isEmpty(output)) {
            return null;
        }
        return JsonUtil.json2Bean(output, OutputDisplay.class);
    }
}
