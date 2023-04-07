package com.sankuai.avatar.collect.appkey.process.transfer;

import com.sankuai.avatar.collect.appkey.model.Appkey;
import com.sankuai.avatar.collect.appkey.process.request.AppkeyDbQueryRequest;
import com.sankuai.avatar.dao.es.request.AppkeyUpdateRequest;
import com.sankuai.avatar.dao.resource.repository.model.AppkeyDO;
import com.sankuai.avatar.dao.resource.repository.request.AppkeyRequest;
import org.elasticsearch.common.inject.name.Named;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Appkey对象转换器
 * 1 属性名称相同，则进行转化
 * 2 属性名不相同， 可通过 @Mapping 注解进行指定转化
 * 3 使用自定义的转换
 *
 * @author qinwei05
 * @date 2022/11/09
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface AppkeyTransfer {

    AppkeyTransfer INSTANCE = Mappers.getMapper(AppkeyTransfer.class);

    /**
     * Appkey对象转数据库存储对象
     *
     * @param appkey appkey
     * @return {@link AppkeyDO}
     */
    @Named("toDO")
    AppkeyDO toDO(Appkey appkey);

    /**
     * 批量数据模型DO转换
     *
     * @param appkeys 服务集合
     * @return {@link List}<{@link AppkeyDO}>
     */
    @IterableMapping(qualifiedByName = "toDO")
    List<AppkeyDO> batchToDO(List<Appkey> appkeys);

    /**
     * Appkey对象转ES存储对象
     *
     * @param appkey appkey
     * @return {@link AppkeyDO}
     */
    @Named("toEsRequest")
    AppkeyUpdateRequest toEsRequest(Appkey appkey);


    /**
     * AppkeyDB对象转ES存储对象
     *
     * @param appkeyDO appkeyDO
     * @return {@link AppkeyDO}
     */
    @Named("dbToEsRequest")
    AppkeyUpdateRequest dbToEsRequest(AppkeyDO appkeyDO);

    /**
     * Appkey对象转DB查询对象
     *
     * @param appkeyDbQueryRequest appkeyDbQueryRequest
     * @return {@link AppkeyDO}
     */
    @Named("toDbRequest")
    AppkeyRequest toDbRequest(AppkeyDbQueryRequest appkeyDbQueryRequest);
}
