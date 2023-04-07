package com.sankuai.avatar.collect.appkey.collector.source;

import com.sankuai.avatar.collect.appkey.collector.transfer.CollectorSourceDataTransfer;
import com.sankuai.avatar.collect.appkey.model.Appkey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jie.li.sh
 * @create 2022-11-18
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class DomAppkeySource extends AppkeySource {

    /**
     * appkey
     */
    private String appkey;

    /**
     * 服务周资源利用率
     */
    private String weekResourceUtil;

    @Override
    public SourceName getSourceName() {
        return SourceName.DOM;
    }

    @Override
    public Appkey transToAppkey(Appkey appkey){
        return CollectorSourceDataTransfer.INSTANCE.toAppkey(this, appkey);
    }
}
