package com.sankuai.avatar.resource.tree.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author zhangxiaoning07
 * @create 2022/10/31
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyTreePdlBO {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    private AppkeyTreeOwtBO owt;

    /**
     * 服务数量
     */
    private Integer srvCount;

}
