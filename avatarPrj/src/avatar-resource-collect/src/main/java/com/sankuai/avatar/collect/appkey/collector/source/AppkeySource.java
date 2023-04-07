package com.sankuai.avatar.collect.appkey.collector.source;

import com.sankuai.avatar.collect.appkey.model.Appkey;

/**
 * @author Jie.li.sh
 * @create 2022-11-18
 **/
public abstract class AppkeySource implements Source {

    /**
     * 主键
     *
     * @return appkey
     */
    abstract String getAppkey();

    public abstract Appkey transToAppkey(Appkey appkey);
}
