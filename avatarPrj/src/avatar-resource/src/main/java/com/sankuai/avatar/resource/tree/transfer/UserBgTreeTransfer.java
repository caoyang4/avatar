package com.sankuai.avatar.resource.tree.transfer;

import com.sankuai.avatar.dao.cache.model.UserBg;
import com.sankuai.avatar.resource.tree.bo.UserBgBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author caoyang
 * @create 2023-01-09 15:43
 */
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface UserBgTreeTransfer {

    UserBgTreeTransfer INSTANCE = Mappers.getMapper(UserBgTreeTransfer.class);

    /**
     * DO -> BO
     *
     * @param userBg userBg
     * @return {@link UserBgBO}
     */
    @Named("toBO")
    UserBgBO toBO(UserBg userBg);

    /**
     * 批量转换DO -> BO
     *
     * @param userBgList userBgList
     * @return {@link List}<{@link UserBgBO}>
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<UserBgBO> toBOList(List<UserBg> userBgList);

    /**
     * BO -> DO
     *
     * @param userBgBO userBgBO
     * @return {@link UserBg}
     */
    @Named("toUserBg")
    UserBg toUserBg(UserBgBO userBgBO);

    /**
     * 批量转换BO -> DO
     *
     * @param userBgBOList userBgBOList
     * @return {@link List}<{@link UserBg}>
     */
    @IterableMapping(qualifiedByName = "toUserBg")
    @Named("toUserBgList")
    List<UserBg> toUserBgList(List<UserBgBO> userBgBOList);

}
