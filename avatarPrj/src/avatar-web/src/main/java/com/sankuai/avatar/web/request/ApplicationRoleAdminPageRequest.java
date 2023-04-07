package com.sankuai.avatar.web.request;

import com.sankuai.avatar.common.vo.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoyang
 * @create 2023-01-16 18:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationRoleAdminPageRequest extends PageRequest {

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
