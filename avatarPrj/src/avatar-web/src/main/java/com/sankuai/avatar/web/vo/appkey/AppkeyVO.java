package com.sankuai.avatar.web.vo.appkey;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 服务树-Appkey对象
 *
 * @author qinwei05
 * @create 2022-12-24 14:15
 * @date 2023/01/06
 */
@Data
public class AppkeyVO {

    /**
     * appkey名称
     */
    private String appkey;

    /**
     * 服务树节点名称
     */
    private String srv;

    /**
     * 描述信息
     */
    private String comment;

    /**
     * 服务名字
     */
    private String name;

    /**
     * 容灾等级
     */
    private Integer capacity;

    /**
     * 主机数
     */
    private Integer hostCount;

    /**
     * 服务等级
     */
    private String rank;

    /**
     * 是否有状态
     */
    private Boolean stateful;

    /**
     * 是否存在Liteset机器
     */
    private Boolean isLiteset;

    /**
     * 是否存在SET机器
     */
    private Boolean isSet;

    /**
     * 上周资源利用率
     */
    private String utilization;

    /**
     * 研发负责人
     */
    private List<AppkeyUserSimpleVO> rdAdmin;

    /**
     * 测试负责人
     */
    private List<AppkeyUserSimpleVO> epAdmin;

    /**
     * 运维负责人
     */
    private List<AppkeyUserSimpleVO> opAdmin;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createAt;

}
