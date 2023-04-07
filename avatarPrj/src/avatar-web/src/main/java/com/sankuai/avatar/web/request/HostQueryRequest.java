package com.sankuai.avatar.web.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 主机查询对象
 *
 * @author qinwei05
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class HostQueryRequest extends PageRequest {

    /**
     * 环境
     */
    @NotBlank(message = "env不能为空")
    @Pattern(regexp = "(^prod$|^staging$|^test$|^dev$|^ppe$)", message = "env环境参数错误！！！仅可为prod、staging、test、dev、ppe中的一个")
    private String env;

    /**
     * 服务
     */
    @NotBlank(message = "appkey服务名称不能为空")
    private String appkey;

    /**
     * 服务树节点
     */
    private String srv;

    /**
     * 检索关键字
     * 支持主机名或IP单个、批量检索
     * 支持机器配置检索
     */
    private String query;

    /**
     * SET（set + LiteSet）
     */
    private String set;

    /**
     * 泳道
     */
    private String swimlane;

    /**
     * 业务分组
     */
    private String grouptags;

    /**
     * 网络类型
     */
    private String netType;

    /**
     * 来源（弹性...）
     */
    private String managePlat;

    /**
     * 机房
     */
    private String idcName;

    /**
     * 类型名称（HULK、虚拟机、物理机）
     */
    private String kindName;

    /**
     * 资源池（公共、结算单元、活动、紧急资源池）
     */
    private String originType;

    /**
     * RS版本-关联查询
     */
    private String rsNetType;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 排序（正序、逆序）
     */
    private String sort;

    /**
     * 上线时间: 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 上线时间: 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /* ---------------------------- 自定义高级筛选 ---------------------------- */

    /**
     * 是否为活动资源
     */
    private Boolean activeResourcePool;

    /**
     * 是否可以替换紧急资源
     */
    private Boolean replaceUrgency;

    /**
     * 是否为可赠予的机器
     */
    private Boolean exchange;

    /**
     * 是否为弹性机器
     */
    private Boolean elastic;

    /**
     * 是否为禁用机器
     */
    private Boolean octoDisabled;

}
