package com.sankuai.avatar.client.org;

import com.sankuai.avatar.client.org.model.Org;
import com.sankuai.avatar.client.org.model.OrgUser;
import com.sankuai.avatar.common.exception.client.SdkBusinessErrorException;

/**
 * org 客户端
 * @author caoyang
 * @create 2022-10-25 15:23
 */
public interface OrgClient {
    /**
     * 被管理信息系统组织用户
     * 根据用户 mis 获取 org 的用户信息
     *
     * @param mis 人员 mis
     * @return Org
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    OrgUser getOrgUserByMis(String mis) throws SdkBusinessErrorException;

    /**
     * 根据组织 id 获取组织信息
     * @param orgId 组织 id
     * @return Org
     * @throws SdkBusinessErrorException sdk业务错误异常
     */
    Org getOrgByOrgId(String orgId) throws SdkBusinessErrorException;

}
