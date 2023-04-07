package com.sankuai.avatar.web.dto.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 服务树owt对象DTO
 * @author zhangxiaoning07
 * @create 2022/10/26
 **/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyTreeOwtDTO {

    /**
     * ID
     */
    private Integer id;

    /**
     * Owt的key
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     * nginx集群
     */
    private String nginxCluster;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * bg
     */
    private String businessGroup;

    /**
     * sre大象群
     */
    private String sreXmGroup;
    /**
     * RD负责人
     */
    private List<String> rdAdmin;

    /**
     * 运维负责人
     */
    private List<String> opAdmin;

    /**
     * 测试负责人
     */
    private List<String> epAdmin;

    /**
     * orgPath
     */
    private List<String> org;

    /**
     * 用户是否可编辑
     */
    private Boolean userCanEdit;

    /**
     * 描述
     */
    private String comment;

}
