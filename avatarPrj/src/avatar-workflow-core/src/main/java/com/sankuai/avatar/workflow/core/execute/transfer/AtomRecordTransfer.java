package com.sankuai.avatar.workflow.core.execute.transfer;

import com.sankuai.avatar.common.utils.JsonUtil;
import com.sankuai.avatar.workflow.core.execute.atom.AtomResult;
import com.sankuai.avatar.dao.workflow.repository.model.ExecuteResult;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * AtomRecord 对象转换
 *
 * @author zhaozhifan02
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AtomRecordTransfer {
    AtomRecordTransfer INSTANCE = Mappers.getMapper(AtomRecordTransfer.class);

    /**
     * AtomResult to  ExecuteResult
     *
     * @param atomResult {@link AtomResult}
     * @return {@link ExecuteResult}
     */
    @Mapping(source = "input", target = "input", qualifiedByName = "toInputString")
    @Mapping(source = "output", target = "output", qualifiedByName = "toOutputString")
    @Mapping(source = "exception", target = "exception", qualifiedByName = "toExceptionString")
    ExecuteResult toExecuteResult(AtomResult atomResult);

    /**
     * input Object to String
     *
     * @param input Object
     * @return String
     */
    @Named("toInputString")
    static String toInputString(Object input) {
        return JsonUtil.bean2Json(input);
    }

    /**
     * output Object to String
     *
     * @param output Object
     * @return String
     */
    @Named("toOutputString")
    static String toOutputString(Object output) {
        return JsonUtil.bean2Json(output);
    }

    /**
     * Exception to String
     *
     * @param exception Exception
     * @return String
     */
    @Named("toExceptionString")
    static String toException(Exception exception) {
        return exception == null ? null : exception.toString();
    }
}
