package com.sankuai.avatar.web.vo.tree;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.web.vo.user.DxUserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 服务树OWT对象VO
 * @author zhangxiaoning07
 * @create 2022-10-19
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppkeyTreeOwtVO {
    /**
     * Owt的key
     */
    private String key;

    /**
     * nginx集群
     */
    private String nginxCluster;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
     * 运维负责人
     */
    private List<DxUserVO> opAdmin;

    /**
     * 测试负责人
     */
    private List<DxUserVO> epAdmin;

    /**
     * RD负责人
     */
    private List<DxUserVO> rdAdmin;

    /**
     * 用户是否可编辑
     */
    private Boolean userCanEdit;

    /**
     * org路径列表
     */
    private List<String> org;

    /**
     * 描述
     */
    private String comment;
}
