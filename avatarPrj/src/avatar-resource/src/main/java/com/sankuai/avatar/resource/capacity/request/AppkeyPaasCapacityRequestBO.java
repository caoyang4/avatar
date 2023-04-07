package com.sankuai.avatar.resource.capacity.request;

import com.sankuai.avatar.common.vo.PageRequest;
import com.sankuai.avatar.resource.capacity.constant.PaasCapacityType;
import lombok.*;

import java.util.Date;

/**
 * 服务依赖paas容灾条件查询对象
 * @author Jie.li.sh
 * @create 2022-09-26
 **/

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppkeyPaasCapacityRequestBO extends PageRequest {

    private Integer idGtn;

    /**
     * appkey
     */
    private String appkey;
    /**
     * paas组件
     */
    private String paasName;

    /**
     * paas appkey
     */
    private String paasAppkey;

    /**
     * 容灾类型: CLUSTER(集群)，TOPIC(主题)，APPKEY(服务)
     */
    private PaasCapacityType type;

    /**
     * 容灾实体
     */
    private String typeName;

    /**
     * 容灾实体角色
     */
    private String clientRole;

    /**
     * 是否加白
     */
    private Boolean isWhite;

    /**
     * 是否为核心
     */
    private Boolean isCore;

    /**
     * 是否 set 化
     */
    private Boolean isSet;

    /**
     * set名称
     */
    private String setName;

    /**
     * set类型
     */
    private String setType;

    /**
     * 是否达标
     */
    private Boolean isCapacityStandard;

    /**
     * 上报日期
     */
    private Date updateDate;
}
