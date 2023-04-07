package com.sankuai.avatar.dao.resource.repository.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动资源请求参数对象
 * @author caoyang
 * @create 2023-03-06 16:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResourceRequest {

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
     * 窗口期活动 id
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
