package com.sankuai.avatar.collect.appkey.event.consumer;

import lombok.Data;

/**
 * OSP DTS appkey事件对象
 *
 * @author qinwei05
 * @date 2022/11/30
 */
@Data
public class OpsDbusAppkeyConsumerEventData {

    /**
     * 数据库名.表名
     */
    private String tableName;

    /**
     * 时间戳
     * binlog在数据库中发生时的时间戳。后三位为000.因为仅有秒级别的数据
     */
    private long timestamp;

    /**
     * binlog编号
     */
    private Integer scn;

    /**
     * 类型
     * update、insert或delete
     */
    private String type;

    /**
     * 源ip
     */
    private String sourceIP;

    /**
     * 数据
     */
    private DbRowData data;

    @Data
    public static class DbRowData {

        /**
         * 名字
         */
        private String appkey;
    }
}
