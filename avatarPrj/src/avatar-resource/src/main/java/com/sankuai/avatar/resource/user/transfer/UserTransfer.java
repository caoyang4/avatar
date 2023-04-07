package com.sankuai.avatar.resource.user.transfer;

import com.sankuai.avatar.client.dx.model.DxUser;
import com.sankuai.avatar.client.org.model.OrgUser;
import com.sankuai.avatar.dao.resource.repository.model.UserDO;
import com.sankuai.avatar.resource.user.bo.UserBO;
import org.apache.commons.lang.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 人员信息 BO <> DO 转换器
 * @author caoyang
 * @create 2022-10-20 15:05
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface UserTransfer {
    UserTransfer INSTANCE = Mappers.getMapper(UserTransfer.class);

    /**
     * 转换 BO -> DO
     * @param userBO BO
     * @return DO
     */
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(source = "mis", target = "loginName")
    @Named("toDO")
    UserDO toDO(UserBO userBO);

    /**
     * 批量转换 BO -> DO
     * @param userBOList BOList
     * @return DOList
     */
    @IterableMapping(qualifiedByName = "toDO")
    @Named("toDOList")
    List<UserDO> toDOList(List<UserBO> userBOList);

    /**
     * 转换 DO -> BO
     * @param userDO DO
     * @return BO
     */
    @Mapping(source = "loginName", target = "mis")
    @Named("toBO")
    UserBO toBO(UserDO userDO);

    /**
     * 批量转换 BO -> DO
     * @param userDOList DOList
     * @return BOList
     */
    @IterableMapping(qualifiedByName = "toBO")
    @Named("toBOList")
    List<UserBO> toBOList(List<UserDO> userDOList);

    /**
     * 根据org 和 大象 组合UserBO
     * @param orgUser org 信息
     * @param dxUser dx 信息
     * @return UserBO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "registerTime", ignore = true)
    @Mapping(target = "loginTime", ignore = true)
    @Mapping(source = "dxUser.avatarUrl", target = "userImage")
    @Mapping(source = "orgUser.source", target = "source")
    @Mapping(source = "orgUser.jobStatus", target = "jobStatus")
    @Mapping(source = "orgUser.name", target = "name")
    @Mapping(source = "orgUser.mis", target = "mis")
    @Mapping(source = "orgUser.leader", target = "leader")
    @Mapping(source = "orgUser.org.orgId", target = "orgId")
    @Mapping(source = "orgUser.org.orgPath",target = "orgPath")
    @Mapping(source = "orgUser.org.orgNamePath",target = "organization", qualifiedByName = "convertOrgNamePath")
    @Named("orgAndDxToBO")
    UserBO orgAndDxToBO(OrgUser orgUser, DxUser dxUser);

    /**
     * 转换org路径
     *
     * @param orgNamePath 组织名称路径
     * @return {@link String}
     */
    @Named("convertOrgNamePath")
    default String convertOrgNamePath(String orgNamePath){
        return StringUtils.isNotEmpty(orgNamePath) ? orgNamePath.replaceAll("-","/") : "";
    }
}
