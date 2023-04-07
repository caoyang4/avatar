package com.sankuai.avatar.resource.activity.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 * @create 2023-03-08 14:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityResourceRequestBO extends PageRequest {

    private Integer id;

    /**
     * 服务名称
     */
    private String appkey;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 窗口 id
     */
    private Integer windowPeriodId;

    /**
     * 部门 id
     */
    private String orgId;

    /**
     * 部门名称
     */
    private String orgName;

    /**
     * 状态
     */
    private String status;

}
