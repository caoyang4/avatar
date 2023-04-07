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
 * @author zhangxiaoning07
 * @create 2022/11/1
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyTreePdlVO {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    /**
     * 运维负责人
     */
    private List<DxUserVO> opAdmin;

    /**
     * 测试负责人
     */
    private List<DxUserVO> epAdmin;

    /**
     * 开发负责人
     */
    private List<DxUserVO> rdAdmin;

    /**
     * pdl的Owt信息
     */
    private AppkeyTreeOwtVO owt;

    /**
     * 用户是否可编辑
     */
    private Boolean userCanEdit;

    /**
     * 服务数量
     */
    private Integer srvCount;

}
