package com.sankuai.avatar.collect.appkey.process;

import com.sankuai.avatar.collect.appkey.model.Appkey;

import java.util.List;

/**
 * 数据处理
 * @author qinwei05
 * @date 2022/11/3 16:37
 */
public interface Processor {

    /**
     * 批量数据处理
     * 默认写入DB、ES
     *
     * @param appkeys appkeys
     */
    Boolean batchProcess(List<Appkey> appkeys);

    /**
     * 单个数据处理
     * 默认写入DB、ES
     *
     * @param appkey appkey
     */
    Boolean process(Appkey appkey);
}
