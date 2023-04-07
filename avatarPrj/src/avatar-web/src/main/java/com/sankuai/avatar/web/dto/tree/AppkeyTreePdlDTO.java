package com.sankuai.avatar.web.dto.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 服务树Pdl数据对象
 *
 * @author zhangxiaoning07
 * @create 2022/11/1
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyTreePdlDTO {

    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * Pdl的Key,如变更管理工具是dianping.tbd.change
     */
    private String key;

    /**
     * Pdl描述
     */
    private String comment;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 运维负责人
     */
    private List<String> opAdmin;

    /**
     * 测试负责人
     */
    private List<String> epAdmin;

    /**
     * 开发负责人
     */
    private List<String> rdAdmin;

    /**
     * pdl的Owt信息
     */
    private AppkeyTreeOwtDTO owt;

    /**
     * 用户是否可编辑
     */
    private Boolean userCanEdit;

    /**
     * 服务数量
     */
    private Integer srvCount;

}
