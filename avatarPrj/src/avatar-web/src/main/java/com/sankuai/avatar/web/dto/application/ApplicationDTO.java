package com.sankuai.avatar.web.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeNodeDTO;
import com.sankuai.avatar.web.dto.orgtree.OrgTreeUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 应用信息DTO对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO {
    /**
     * id，唯一标识
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 中文名称
     */
    private String chName;

    /**
     * 是否公共服务
     */
    private Boolean isPublic;

    /**
     * 所属组织
     */
    private OrgTreeNodeDTO team;

    /**
     * 负责人
     */
    private OrgTreeUserDTO admin;

    /**
     * 服务数量
     */
    private Integer appKeyCount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
