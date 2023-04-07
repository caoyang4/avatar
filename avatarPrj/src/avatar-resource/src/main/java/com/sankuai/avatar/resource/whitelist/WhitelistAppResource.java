package com.sankuai.avatar.resource.whitelist;

import com.sankuai.avatar.resource.whitelist.bo.WhitelistAppBO;

import java.util.List;

/**
 * 白名单类型资源管理接口
 * @author caoyang
 * @create 2022-11-15 10:21
 */
public interface WhitelistAppResource {

    /**
     * 查询所有白名单类型
     *
     * @return {@link List}<{@link WhitelistAppBO}>
     */
    List<WhitelistAppBO> getAllWhitelistApp();

}
