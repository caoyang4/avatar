package com.sankuai.avatar.collect.appkey.collector.source;

import com.sankuai.avatar.collect.appkey.collector.transfer.CollectorSourceDataTransfer;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author qinwei05
 * @create 2022-12-18
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class OfflineAppkeySource extends AppkeySource {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 服务是否下线
     */
    private Boolean isOffline;

    /**
     * 服务删除时间
     */
    private Date offlineTime;

    @Override
    public SourceName getSourceName() {
        return SourceName.MAFKA_MESSAGE;
    }

    @Override
    public Appkey transToAppkey(Appkey appkey){
        return CollectorSourceDataTransfer.INSTANCE.toAppkey(this, appkey);
    }
}
