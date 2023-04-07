package com.sankuai.avatar.client.org.model;

import lombok.Data;

/**
 * user org 信息
 * @author caoyang
 * @create 2022-10-25 17:03
 */
@Data
public class OrgUser {
    /**
     * mis 号
     */
    private String mis;
    /**
     * 中文姓名
     */
    private String name;

    /**
     * 在职 or 离职
     */
    private String jobStatus;

    /**
     * 直属 leader
     */
    private String leader;
    /**
     * 用户所属 org
     */
    private Org org;

    /**
     * 数据源，即租户，分为：ORG 或者 Maoyan(猫眼)
     */
    private String source;
}
