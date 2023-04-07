package com.sankuai.avatar.dao.resource.repository.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author caoyang
 * @create 2023-01-11 15:57
 */
@Builder
@Data
public class ApplicationRoleAdminRequest {

    private Integer id;
    /**
     *  应用 id
     */
    private Integer applicationId;

    /**
     *  应用名称
     */
    private String applicationName;

    /**
     *  org 路径
     */
    private String orgPath;

}
