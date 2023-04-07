package com.sankuai.avatar.capacity.service;

import com.sankuai.avatar.capacity.node.AppKeyNode;

import java.io.IOException;

/**
 * @author chenxinli
 */
public interface ICapacityCollectionItem {
    /**
     * 容灾等级相关数据采集
     *
     * @throws IOException some exception
     */
    void collect() throws IOException;

    /**
     * 刷新一下数据
     */
    void refresh();

    /**
     * 获取数据
     *
     * @param <T>        数据类型
     * @param appKeyNode Appkey节点
     * @return 返回
     */
    <T> T getData(AppKeyNode appKeyNode);

    /**
     * 设置Appkey的容灾属性
     *
     * @param appKeyNode Appkey节点
     * @throws Exception some exception
     */
    void setAppkeyCapacityProperty(AppKeyNode appKeyNode) throws Exception;
}
