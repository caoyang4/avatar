package com.sankuai.avatar.collect.appkey.collector.transfer;

import com.sankuai.avatar.collect.appkey.consumer.model.OpsDbusAppkeyConsumerData;
import com.sankuai.avatar.collect.appkey.consumer.model.ScAppkeyConsumerData;
import com.sankuai.avatar.collect.appkey.event.consumer.OpsDbusAppkeyConsumerEventData;
import com.sankuai.avatar.collect.appkey.event.consumer.ScAppkeyConsumerEventData;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


/**
 * 收集消费者数据传输
 *
 * @author qinwei05
 * @date 2022/12/13
 */
@Mapper(
        nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface CollectorConsumerDataTransfer {

    /**
     * 转换器
     */
    CollectorConsumerDataTransfer INSTANCE = Mappers.getMapper(CollectorConsumerDataTransfer.class);

    /**
     * SC消息收集数据
     *
     * @param scAppkeyConsumerData 源数据
     * @return {@link ScAppkeyConsumerEventData}
     */
    ScAppkeyConsumerEventData toConsumerData(ScAppkeyConsumerData scAppkeyConsumerData);

    /**
     * OPS消息收集数据
     *
     * @param opsDbusAppkeyConsumerData 源数据
     * @return {@link OpsDbusAppkeyConsumerEventData}
     */
    OpsDbusAppkeyConsumerEventData toConsumerData(OpsDbusAppkeyConsumerData opsDbusAppkeyConsumerData);
}
