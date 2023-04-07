package com.sankuai.avatar.web.vo.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeNodeVO;
import com.sankuai.avatar.web.vo.orgtree.OrgTreeUserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 应用信息对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/24
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationVO {
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
    private OrgTreeNodeVO team;

    /**
     * 负责人
     */
    private OrgTreeUserVO admin;

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
