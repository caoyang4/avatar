package com.sankuai.avatar.dao.resource.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 大象群数据对象
 * @author caoyang
 * @create 2022-11-01 19:55
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "org_dx_group")
public class DxGroupDO {
    /**
     * 主键 id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 群 id
     */
    private String groupId;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群状态
     */
    private String groupStatus;

    /**
     * 群信息更新人员
     */
    private String updateUser;
    /**
     *  创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
