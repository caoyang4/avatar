package com.sankuai.avatar.resource.user.transfer;

import com.sankuai.avatar.dao.resource.repository.request.UserRequest;
import com.sankuai.avatar.resource.user.request.UserRequestBO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * user 条件查询对象转换器
 * @author caoyang
 * @create 2022-10-20 14:37
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface UserRequestTransfer {
    UserRequestTransfer INSTANCE = Mappers.getMapper(UserRequestTransfer.class);

    /**
     * 条件查询对象转换 UserRequestBO -> UserRequest
     * @param userRequestBO BO
     * @return UserRequest
     */
    @Named("toUserRequest")
    UserRequest toUserRequest(UserRequestBO userRequestBO);

}
