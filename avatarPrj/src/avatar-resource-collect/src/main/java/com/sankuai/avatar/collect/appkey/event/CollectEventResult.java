package com.sankuai.avatar.collect.appkey.event;

import com.sankuai.avatar.collect.appkey.collector.source.Source;

import java.util.List;

/**
 * 采集结果
 * @author Jie.li.sh
 * @create 2022-11-17
 **/
public interface CollectEventResult {
    /**
     * 源数据
     * @return 更新源结果
     */
    List<? extends Source> getSourceList();
}
