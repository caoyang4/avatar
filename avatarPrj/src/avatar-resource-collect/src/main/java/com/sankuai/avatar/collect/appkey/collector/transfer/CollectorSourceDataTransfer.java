package com.sankuai.avatar.collect.appkey.collector.transfer;

import com.sankuai.avatar.collect.appkey.collector.source.*;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface CollectorSourceDataTransfer {

    /**
     * 转换器
     */
    CollectorSourceDataTransfer INSTANCE = Mappers.getMapper(CollectorSourceDataTransfer.class);

    /**
     * offlineAppkeySource转换为Appkey对象
     *
     * @param offlineAppkeySource appkey源
     * @return {@link Appkey}
     */
    Appkey toAppkey(OfflineAppkeySource offlineAppkeySource, @MappingTarget Appkey appkey);

    /**
     * DomAppkeySource转换为Appkey对象
     *
     * @param domAppkeySource dom appkey源
     * @return {@link Appkey}
     */
    Appkey toAppkey(DomAppkeySource domAppkeySource, @MappingTarget Appkey appkey);

    /**
     * RocketAppkeyHostSource转换为Appkey对象
     *
     * @param rocketAppkeyHostSource rocket appkey主机源
     * @return {@link Appkey}
     */
    Appkey toAppkey(RocketAppkeyHostSource rocketAppkeyHostSource, @MappingTarget Appkey appkey);

    /**
     * OpsAppkeySource转换为Appkey对象
     *
     * @param opsAppkeySource ops appkey源
     * @return {@link Appkey}
     */
    Appkey toAppkey(OpsAppkeySource opsAppkeySource, @MappingTarget Appkey appkey);

    /**
     * ScAppkeySource转换为Appkey对象
     *
     * @param scAppkeySource sc appkey源
     * @return {@link Appkey}
     */
    Appkey toAppkey(ScAppkeySource scAppkeySource, @MappingTarget Appkey appkey);

    /**
     * ScNotBackendAppkeySource转换为Appkey对象
     *
     * @param scNotBackendAppkeySource sc非后端appkey源
     * @return {@link Appkey}
     */
    Appkey toAppkey(ScNotBackendAppkeySource scNotBackendAppkeySource, @MappingTarget Appkey appkey);
}
