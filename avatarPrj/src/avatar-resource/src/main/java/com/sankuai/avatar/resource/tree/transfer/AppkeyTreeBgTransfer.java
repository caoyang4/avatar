package com.sankuai.avatar.resource.tree.transfer;

import com.sankuai.avatar.client.ops.model.OpsBg;
import com.sankuai.avatar.resource.tree.bo.AppkeyTreeBgBO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * AppkeyTreeBG类型转换接口
 * @author zhangxiaoning07
 * @create 2022-10-24
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyTreeBgTransfer {
    /**
     * 转换器
     */
    AppkeyTreeBgTransfer INSTANCE = Mappers.getMapper(AppkeyTreeBgTransfer.class);

    /**
     * 转换为BO
     * @param opsBg opsBg对象
     * @return bo
     */
    AppkeyTreeBgBO toBO(OpsBg opsBg);

    /**
     * 批量转换为BOList
     * @param opsBgList opsAppkeyTreeBG对象列表
     * @return BO对象列表
     */
    List<AppkeyTreeBgBO> toBOList(List<OpsBg> opsBgList);

    /**
     * bg转换为BO
     * @param name bg 名称
     * @return bo
     */
    @Named("toAppkeyTreeBgBO")
    default AppkeyTreeBgBO toAppkeyTreeBgBO(String name){
        return AppkeyTreeBgBO.builder().name(name).build();
    }

    /**
     * bg列表转换
     * @param bgNameList ops bg list
     * @return bo list
     */
    @IterableMapping(qualifiedByName = "toAppkeyTreeBgBO")
    List<AppkeyTreeBgBO> toAppkeyTreeBgBOList(List<String> bgNameList);
}
