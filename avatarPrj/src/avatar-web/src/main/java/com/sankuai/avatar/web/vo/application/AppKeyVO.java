package com.sankuai.avatar.web.vo.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sankuai.avatar.web.vo.DXUserVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Jie.li.sh
 * @create 2020-02-18
 **/
@Data
public class AppKeyVO {
    /**
     * appkey 名称，唯一标识
     */
    private String appKey;
    /**
     * 服务描述
     */
    private String comment;
    /**
     * 容灾等级
     */
    private Byte capacity;
    /**
     * 主机数量
     */
    private Integer host;
    /**
     * 服务等级
     */
    private String rank;
    /**
     * 服务收藏
     */
    private boolean favor;
    /**
     * 服务负责人
     */
    private List<DXUserVO> rdAdmin;
    /**
     * 运维负责人
     */
    private List<DXUserVO> opAdmin;
    /**
     * 测试负责人
     */
    private List<DXUserVO> epAdmin;
    /**
     * 服务创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
