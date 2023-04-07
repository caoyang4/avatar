package com.sankuai.avatar.collect.appkey.collector.source;

import com.sankuai.avatar.collect.appkey.collector.transfer.CollectorSourceDataTransfer;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * appkey机器数目
 *
 * @author qinwei05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RocketAppkeyHostSource extends AppkeySource {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 服务下机器总数目
     * 数据源：rocket
     */
    private Integer hostCount;

    /**
     * 服务下线上环境机器总数目
     * 数据源：rocket
     */
    private Integer prodHostCount;

    public RocketAppkeyHostSource(String appkey) {
        this.appkey = appkey;
    }

    @Override
    public SourceName getSourceName() {
        return SourceName.ROCKET;
    }

    @Override
    public Appkey transToAppkey(Appkey appkey){
        return CollectorSourceDataTransfer.INSTANCE.toAppkey(this, appkey);
    }
}
