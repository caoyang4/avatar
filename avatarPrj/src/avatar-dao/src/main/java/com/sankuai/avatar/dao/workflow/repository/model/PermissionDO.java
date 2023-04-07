package com.sankuai.avatar.dao.workflow.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 权限数据对象
 *
 * @author zhaozhifan02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "flow_permisson")
public class PermissionDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 流程模板名称
     */
    private String templateName;

    /**
     * 用户角色
     * rd、sre、rd_admin、group、leader、any
     */
    private String role;

    /**
     * 是否有流程的发起权限
     * Y-是
     * N-否
     */
    private String isApply;

    /**
     * 是否有流程的审核权限
     * Y-是
     * N-否
     */
    private String isChecker;

    /**
     * 成员组
     */
    private String groupList;


}
